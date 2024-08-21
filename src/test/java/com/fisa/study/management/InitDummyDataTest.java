package com.fisa.study.management;

import com.fisa.study.management.domain.checkup.dto.ReceiveCheckUpDTO;
import com.fisa.study.management.domain.checkup.service.CheckUpService;
import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.service.CommentService;
import com.fisa.study.management.domain.member.dto.MemberRegisterDTO;
import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.repository.MemberRepository;
import com.fisa.study.management.domain.member.service.MemberService;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.domain.room.service.RoomService;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.service.SnapshotService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @BeforeAll을 인스턴스 메서드로 사용하기 위함
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InitDummyDataTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberService memberService;

    @Autowired
    RoomService roomService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SnapshotService snapshotService;

    @Autowired
    private CheckUpService checkUpService;

    @Autowired
    private CommentService commentService;

    private UUID uuid = UUID.fromString("bc31c700-8318-46a9-b6aa-bed717ba1663");

    private Long userId = 1L;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public void clearDatabase() {
        // 외래 키 제약 조건 비활성화
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");

        // 모든 테이블 이름 가져오기
        List<String> tableNames = jdbcTemplate.queryForList("SHOW TABLES;", String.class);

        // 각 테이블에 대해 TRUNCATE TABLE 실행
        for (String tableName : tableNames) {
            jdbcTemplate.execute("TRUNCATE TABLE " + tableName + ";");
        }

        // 외래 키 제약 조건 활성화
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");
    }

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
        Member member = memberRepository.findByEmail("test").get();

        Room room = Room.builder()
                .uuid(uuid)
                .name("room name")
                .description("desc")
                .content("")
                .snapshotList(null)
                .commentList(null)
                .checkUpList(null)
                .member(member)
                .build();

        roomRepository.save(room);
        em.persist(room);
        log.info("Order 2 {}", uuid);
    }

    @Test
    @Order(3)
    public void insertSnapshot() throws IllegalAccessException {
        for (int i = 1; i <= 3; i++) {
            RegSnapshotDTO dto = RegSnapshotDTO.builder()
                    .uuid(uuid)
                    .title("SnapShot " + i)
                    .content("SnapShot Content " + i)
                    .createDate(LocalDateTime.now())
                    .build();
            snapshotService.regSnapshot(userId, dto);
        }
        log.info("Order 3");
    }

    @Test
    @Order(4)
    public void insertCheckup() throws Exception {
        ReceiveCheckUpDTO dto = ReceiveCheckUpDTO.builder()
                .title("test용 checkup")
                .build();

        checkUpService.registerCheckUpForRoom(userId, uuid, dto);
        log.info("Order 4");
    }

    @Test
    @Order(5)
    public void insertComment(){
        for (int i = 1; i <= 3; i++) {
            CommentDTO dto= CommentDTO.builder()
                    .uuid(uuid)
                    .content("테스트용 코멘트")
                    .build();
            commentService.regCommentByRoomId(dto);
        }
        log.info("Order 5");
    }
}
