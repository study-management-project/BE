package com.fisa.study.management;

import com.fisa.study.management.domain.room.service.RoomServiceImpl;
import com.fisa.study.management.domain.snapshot.service.SnapshotServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ApplicationTests {

    @Autowired
    private SnapshotServiceImpl snapshotService;
    @Autowired
    private RoomServiceImpl roomService;

    @Test
    void contextLoads() {
//		RoomRequestDTO roomRequestDTO= RoomRequestDTO.builder()
//				.name("test용")
//				.description("테스트테스트")
//				.build();
//		roomService.createRoom(roomRequestDTO);
//		snapshotService.regSnapshot(1L,
//				RegSnapshotDTO.builder()
//						.content("test2")
//						.build());
//		snapshotService.regSnapshot(1L,
//				RegSnapshotDTO.builder()
//						.content("test3")
//						.build());

    }

}
