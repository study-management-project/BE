package com.fisa.study.management.domain.room.service;

import com.fisa.study.management.domain.checkup.dto.CheckUpDTO;
import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.checkup.repository.CheckUpRepository;
import com.fisa.study.management.domain.comment.dto.CommentDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


    public String createRoom(Long userId, RoomRequestDTO roomRequestDTO) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (optionalMember.isEmpty()) {
            return "존재하지 않는 유저";
        }
        Member member = optionalMember.get();
        Room room = roomRequestDTO.toEntity(member);
        Room savedRoom = roomRepository.save(room);
        return savedRoom.getUuid().toString();
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

        Optional<CheckUp> _checkUp = checkUpRepository.findTopByRoomIdOrderByIdDesc(room.getId());
        CheckUpDTO checkUpDTO = null;
        if (_checkUp.isPresent()){
            checkUpDTO= CheckUpEntityToDTO(_checkUp.get());
        }else {
            checkUpDTO= CheckUpDTO.builder()
                    .title("현재 질문이 없습니다")
                    .build();
        }
        return RoomResponseByUserDTO.builder()
                .name(room.getName())
                .description(room.getDescription())
                .content(room.getContent())
                .snapshotList(resSnapshotDTOS)
                .commentList(commentDTOS)
                .haveSnapshotDate(dayList)
                .checkUp(checkUpDTO)
                .build();
    }

    ResSnapshotDTO EntityToSendSnapshotDTO(Snapshot snapshot){
        return ResSnapshotDTO.builder()
                .title(snapshot.getTitle())
                .content(snapshot.getContent())
                .createdDate(snapshot.getCreatedDate())
                .build();
    }

    CheckUpDTO CheckUpEntityToDTO(CheckUp checkUp){
        return  CheckUpDTO.builder()
                .title(checkUp.getTitle())
                .build();
    }
}
