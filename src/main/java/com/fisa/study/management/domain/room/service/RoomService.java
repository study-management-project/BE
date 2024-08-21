package com.fisa.study.management.domain.room.service;

import com.fisa.study.management.domain.checkup.dto.ResponseFirstCheckUpDTO;
import com.fisa.study.management.domain.checkup.dto.SendCheckUpDTO;
import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.checkup.repository.CheckUpRepository;
import com.fisa.study.management.domain.checkup.service.CheckUpService;
import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.comment.repository.CommentRepository;
import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.repository.MemberRepository;
import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByUserDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.domain.snapshot.dto.SendSnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import com.fisa.study.management.domain.snapshot.service.SnapshotService;
import com.fisa.study.management.global.argumentresolver.Login;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final CheckUpRepository checkUpRepository;

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
        Room room = roomRepository.findByUuidWithSnapshots(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        Room room2 = roomRepository.findByUuidWithComments(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        List<SendSnapshotDTO> sendSnapshotDTOS = room.getSnapshotList().stream()
                .map(this::EntityToSendSnapshotDTO).toList();
        List<String> commentDTOS = room2.getCommentList()
                .stream()
                .map(Comment::getContent).
                toList();

        Optional<CheckUp> _checkUp = checkUpRepository.findTopByRoomIdOrderByIdDesc(room.getId());
        ResponseFirstCheckUpDTO checkUpDTO = null;
        if (_checkUp.isPresent()){
            checkUpDTO= CheckUpEntityToDTO(_checkUp.get());
        }else {
            checkUpDTO= ResponseFirstCheckUpDTO.builder()
                    .title("현재 질문이 없습니다")
                    .build();
        }
        return RoomResponseByUserDTO.builder()
                .name(room.getName())
                .description(room.getDescription())
                .content(room.getContent())
                .snapshotList(sendSnapshotDTOS)
                .commentList(commentDTOS)
                .checkUp(checkUpDTO)
                .build();
    }

    SendSnapshotDTO EntityToSendSnapshotDTO(Snapshot snapshot){
        return SendSnapshotDTO.builder()
                .title(snapshot.getTitle())
                .content(snapshot.getContent())
                .createDate(snapshot.getCreatedDate())
                .build();
    }
    CommentDTO CommentEntityToDTO(Comment comment){
        return CommentDTO.builder()
                .content(comment.getContent())
                .build();
    }
    ResponseFirstCheckUpDTO CheckUpEntityToDTO(CheckUp checkUp){
        return  ResponseFirstCheckUpDTO.builder()
                .title(checkUp.getTitle())
                .build();
    }

}
