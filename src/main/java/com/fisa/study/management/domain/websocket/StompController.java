package com.fisa.study.management.domain.websocket;

import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.service.CommentService;
import com.fisa.study.management.domain.room.dto.CodeDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.service.RoomService;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.dto.ResSnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import com.fisa.study.management.domain.snapshot.service.SnapshotService;
import com.fisa.study.management.global.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompController {

    private final SimpMessageSendingOperations sendingOperations;
    private final RoomService roomService;
    private final CommentService commentService;
    private final SnapshotService snapshotService;
    private ConcurrentHashMap<UUID, String> cacheMap = new ConcurrentHashMap<>();

    @MessageMapping("/share-code")
    public void shareCode(@Payload CodeDTO dto) {
        UUID roomId = dto.getUuid();
        String newContent = dto.getContent();

        // 캐시에서 현재 콘텐츠를 가져옴
        String cachedContent = cacheMap.get(roomId);

        if (cachedContent != null && cachedContent.equals(newContent)) {
            // 캐시 히트: 캐시된 내용과 동일하면 바로 반환
            log.info("Cache Hit");
            sendingOperations.convertAndSend("/topic/" + roomId + "/code/", cachedContent);
            return;
        }

        // 캐시 미스: 새로운 콘텐츠로 업데이트
        log.info("Cache Miss");

        // 메시지를 받은 후 해당 Room의 content를 업데이트
        Room room = roomService.updateRoom(roomId, newContent);

        // 캐시 업데이트
        cacheMap.put(roomId, newContent);

        // 현재 방의 구독자 수 확인

        // 업데이트된 content를 topic 구독자들에게 뿌림
        sendingOperations.convertAndSend("/topic/" + roomId + "/code", newContent);
    }

    @MessageMapping("/share-comment")
    public void shareComment(@Payload CommentDTO dto) {
        commentService.regCommentByRoomId(dto);
        log.info(dto.toString());
        sendingOperations.convertAndSend("/topic/" + dto.getUuid() + "/comment", dto.getContent());
    }

    @MessageMapping("/share-snapshot")
    public void shareSnapshot(@Payload RegSnapshotDTO dto) {
        Snapshot snapshot = snapshotService.regSnapshot(dto);
        ResSnapshotDTO resSnapshotDTO = snapshotService.entityToSendSnapshotDTO(snapshot);
        sendingOperations.convertAndSend("/topic/" + dto.getUuid() + "/snapshot", resSnapshotDTO);
    }

    @MessageMapping("/share-checkup")
    public void shareSnapshot(@Payload String title) {
//        Snapshot snapshot = snapshotService.regSnapshot(dto);
//        ResSnapshotDTO resSnapshotDTO = snapshotService.entityToSendSnapshotDTO(snapshot);
//        sendingOperations.convertAndSend("/topic/" + dto.getUuid() + "/snapshot", title);
    }
}