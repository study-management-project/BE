package com.fisa.study.management;

import com.fisa.study.management.domain.checkup.dto.ReceiveCheckUpDTO;
import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.checkup.service.CheckUpService;
import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.service.CommentService;
import com.fisa.study.management.domain.member.dto.MemberRegisterDTO;
import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.entity.Role;
import com.fisa.study.management.domain.member.repository.MemberRepository;
import com.fisa.study.management.domain.member.service.MemberService;
import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.domain.room.service.RoomService;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.service.SnapshotService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback(value = false) // 각각의 테스트가 연관되있기 때문에 rollback 하면 안됨
@Slf4j
public class InsertDummyData {

    @Autowired
    MemberService memberService;

    @Autowired
    RoomService roomService;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private RoomRepository roomRepository;

    private UUID roomId = null;
    @Autowired
    private SnapshotService snapshotService;

    @Autowired
    private CheckUpService checkUpService;

    @Autowired
    private CommentService commentService;

    @Test
    @Order(1)
    public void insertMember() {
        MemberRegisterDTO dto = MemberRegisterDTO.builder()
                        .email("test")
                        .username("test")
                        .password("1")
                        .build();

        String register = memberService.register(dto);
        log.info("Order 1 {}", register);
    }

    @Test
    @Order(2)
    public void insertRoom() {
        RoomRequestDTO dto = RoomRequestDTO.builder()
                        .name("my Room")
                        .description("my description")
                        .build();

        Long userId = memberRepository.findByEmail("test").get().getId();
        String result = roomService.createRoom(userId, dto);
        log.info("Order 2 {}", result);
    }

    @Test
    @Order(3)
    public void insertSnapshot() throws IllegalAccessException {
        Long userId = memberRepository.findByEmail("test").get().getId();
        UUID uuid = roomRepository.findByAdminId(userId).get(0).getUuid();
        RegSnapshotDTO dto = RegSnapshotDTO.builder()
                .uuid(uuid)
                .content("Select * from todo;")
                .createDate(LocalDateTime.now())
                .build();

        snapshotService.regSnapshot(userId, dto);
        log.info("Order 3");
    }
    @Test
    @Order(4)
    public void insertCheckup() throws Exception {
        Long userId = memberRepository.findByEmail("test").get().getId();

        UUID uuid = roomRepository.findByAdminId(userId).get(0).getUuid();

        ReceiveCheckUpDTO dto = ReceiveCheckUpDTO.builder()
                .title("test용 checkup")
                .build();
        checkUpService.registerCheckUpForRoom(userId, uuid, dto);
        log.info("Order 4");
    }
    @Test
    @Order(5)
    public void insertComment(){
        Long userId = memberRepository.findByEmail("test").get().getId();
        UUID uuid = roomRepository.findByAdminId(userId).get(0).getUuid();
        CommentDTO dto= CommentDTO.builder()
                .uuid(uuid)
                .content("테스트용 코멘트")
                .build();
        commentService.regCommentByRoomId(dto);
    }

}
