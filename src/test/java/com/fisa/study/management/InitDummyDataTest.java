package com.fisa.study.management;

import com.fisa.study.management.domain.checkup.dto.CheckUpDTO;
import com.fisa.study.management.domain.checkup.service.CheckUpServiceImpl;
import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.service.CommentServiceImpl;
import com.fisa.study.management.domain.member.dto.MemberRegisterDTO;
import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.repository.MemberRepository;
import com.fisa.study.management.domain.member.service.MemberServiceImpl;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.domain.room.service.RoomServiceImpl;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.service.SnapshotServiceImpl;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @BeforeAll을 인스턴스 메서드로 사용하기 위함
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //junit5에서는 순서지정하려면 이거 써야함
public class InitDummyDataTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    RoomServiceImpl roomService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SnapshotServiceImpl snapshotService;

    @Autowired
    private CheckUpServiceImpl checkUpService;

    @Autowired
    private CommentServiceImpl commentService;

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

        memberService.register(dto);
        log.info("Order 1 {}", "성공");
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
    public void insertSnapshot() {
        for (int i = 1; i <= 3; i++) {
            RegSnapshotDTO dto = RegSnapshotDTO.builder()
                    .uuid(uuid)
                    .title("SnapShot " + i)
                    .content("SnapShot Content " + i)
                    .build();
            snapshotService.regSnapshot(dto);
        }
        log.info("Order 3");
    }

    @Test
    @Order(4)
    public void insertCheckup() {
        CheckUpDTO dto = CheckUpDTO.builder()
                .uuid(uuid)
                .title("test용 checkup")
                .build();

        checkUpService.registerCheckUpForRoom(dto);
        log.info("Order 4");
    }

    @Test
    @Order(5)
    public void insertComment() {
        for (int i = 1; i <= 3; i++) {
            CommentDTO dto = CommentDTO.builder()
                    .uuid(uuid)
                    .content("테스트용 코멘트")
                    .build();
            commentService.regCommentByRoomId(dto);
        }
        log.info("Order 5");
    }
}
