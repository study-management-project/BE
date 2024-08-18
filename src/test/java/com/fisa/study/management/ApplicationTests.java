package com.fisa.study.management;

import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.service.RoomService;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.service.SnapshotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ApplicationTests {

	@Autowired
	private SnapshotService snapshotService;
	@Autowired
	private RoomService roomService;

	@Test
	void contextLoads() {
		RoomRequestDTO roomRequestDTO= RoomRequestDTO.builder()
				.name("test용")
				.description("테스트테스트")
				.build();
		roomService.createRoom(roomRequestDTO);
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
