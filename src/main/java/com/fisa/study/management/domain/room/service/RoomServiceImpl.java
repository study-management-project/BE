package com.fisa.study.management.domain.room.service;

import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.repository.MemberRepository;
import com.fisa.study.management.domain.room.dto.RoomModifyRequestDTO;
import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByUserDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.global.error.CustomException;
import com.fisa.study.management.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public List<RoomResponseByAdminDTO> getAllRoomsByUserId(Long userId) {
        return roomRepository.findByAdminId(userId);
    }

    public void createRoom(Long userId, RoomRequestDTO roomRequestDTO) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Room room = roomRequestDTO.toEntity(member);
        roomRepository.save(room);
    }

    public void updateRoom(UUID uuid, String content) {
        Room room = roomRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        room.setContent(content);
        roomRepository.save(room);
    }

    public void modifyRoom(RoomModifyRequestDTO roomModifyRequestDTO) {
        Room room = roomRepository.findByUuid(roomModifyRequestDTO.getUuid())
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        room.modify(roomModifyRequestDTO.getDescription(), roomModifyRequestDTO.getName());
        roomRepository.save(room);

    }

    public RoomResponseByUserDTO getRoomDetails(UUID uuid) {
        Room room = roomRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        return RoomResponseByUserDTO.from(room);
    }

    public void deleteRoom(UUID uuid) {
        Room room = roomRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        roomRepository.delete(room);
    }
}
