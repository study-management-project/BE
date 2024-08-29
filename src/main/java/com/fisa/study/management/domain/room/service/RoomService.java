package com.fisa.study.management.domain.room.service;

import com.fisa.study.management.domain.checkup.dto.CheckUpDTO;
import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.checkup.repository.CheckUpRepository;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.repository.MemberRepository;
import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByUserDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.domain.snapshot.dto.ResSnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import com.fisa.study.management.domain.snapshot.repository.SnapshotRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final CheckUpRepository checkUpRepository;
    private final SnapshotRepository snapshotRepository;

    public Room getRoomByUUID(UUID uuid){
        return roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
    }

    public List<RoomResponseByAdminDTO> getAllRoomsByUserId(Long userId) {
        return roomRepository.findByAdminId(userId);
    }


    public ResponseEntity<?> createRoom(Long userId, RoomRequestDTO roomRequestDTO) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (optionalMember.isEmpty()) {
            return ResponseEntity.badRequest().body("존재하지 않는 유저");
        }
        Member member = optionalMember.get();
        Room room = roomRequestDTO.toEntity(member);
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.ok(savedRoom.getUuid().toString());
    }

    public Room updateRoom(UUID uuid, String content) {
        Room room = roomRepository.findByUuid(uuid).orElse(null);
        if (room != null) {
            room.setContent(content);
            return roomRepository.save(room);
        }
        return null;
    }

    public RoomResponseByUserDTO getRoomDetails(UUID uuid){

        Room room = roomRepository.findByUuidWithComments(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        LocalDate now =LocalDate.now();
        int year= now.getYear();
        int month= now.getMonthValue();
        int day= now.getDayOfMonth();
        List<String> commentDTOS = room.getCommentList()
                .stream()
                .map(Comment::getContent).
                toList();

        List<ResSnapshotDTO> resSnapshotDTOS =
                snapshotRepository.findCreatedDateByRoomIdAndDay(room.getId(), year,month,day)
                .stream().map(this::EntityToSendSnapshotDTO).toList();

        Integer[] dayList=
                snapshotRepository.findDistinctCreatedDatesByRoomIdAndMonth(room.getId(),year,month)
                .stream().map(LocalDateTime::getDayOfMonth).distinct().toArray(Integer[]::new);

        Optional<CheckUp> checkUp= checkUpRepository.findTopByRoomIdOrderByIdDesc(room.getId());
//        log.info("로그입니다"+checkUp.get().getIsOpen()+checkUp.get().getTitle());
        CheckUpDTO checkUpDTO = EntityToTestCheckUpDTO(checkUp,room.getUuid());
        return RoomResponseByUserDTO.builder()
                .name(room.getName())
                .description(room.getDescription())
                .content(room.getContent())
                .snapshotList(resSnapshotDTOS)
                .commentList(commentDTOS)
                .haveSnapshotDate(dayList)
                .checkUpDTO(checkUpDTO)
                .build();
    }

    ResSnapshotDTO EntityToSendSnapshotDTO(Snapshot snapshot){
        return ResSnapshotDTO.builder()
                .title(snapshot.getTitle())
                .content(snapshot.getContent())
                .createdDate(snapshot.getCreatedDate())
                .build();
    }
    CheckUpDTO EntityToTestCheckUpDTO(Optional<CheckUp> checkUp,UUID uuid) {
        if (checkUp.isPresent()) {
            return CheckUpDTO.builder()
                    .uuid(uuid)
                    .title(checkUp.get().getTitle())
                    .isOpen(checkUp.get().getIsOpen())
                    .build();
        }
        else {
            return CheckUpDTO.builder()
                    .title("현재 이해도 조사가 없습니다")
                    .uuid(uuid)
                    .isOpen(false)
                    .build();
        }
    }
}
