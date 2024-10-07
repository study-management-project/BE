package com.fisa.study.management.domain.websocket;

import com.fisa.study.management.domain.checkup.dto.CheckUpDTO;
import com.fisa.study.management.domain.checkup.dto.SendCheckUpDTO;
import com.fisa.study.management.domain.checkup.service.CheckUpServiceImpl;
import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.service.CommentServiceImpl;
import com.fisa.study.management.domain.room.dto.CodeDTO;
import com.fisa.study.management.domain.room.service.RoomServiceImpl;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.dto.ResSnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import com.fisa.study.management.domain.snapshot.service.SnapshotServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompController {

    private final SimpMessageSendingOperations sendingOperations;
    private final SimpMessagingTemplate messagingTemplate;
    private final RoomServiceImpl roomService;
    private final CommentServiceImpl commentService;
    private final SnapshotServiceImpl snapshotService;
    private final CheckUpServiceImpl checkUpService;
    private final ConcurrentHashMap<UUID, String> cacheMap = new ConcurrentHashMap<>();

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
        roomService.updateRoom(roomId, newContent);

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
//    @MessageMapping("/delete-comment")
//    public void deleteComment(@Payload DeleteCommentDTO dto) {
//        commentService.remove(dto.getId());
//        log.info(dto.toString());
//        WebSocketMessage message = new WebSocketMessage() {
//            @Override
//            public Object getPayload() {
//                return null;
//            }
//
//            @Override
//            public int getPayloadLength() {
//                return 0;
//            }
//
//            @Override
//            public boolean isLast() {
//                return false;
//            }
//        };
//        message.setType("DELETE"); // DELETE 타입 명시
//        message.setCommentId(dto.getId());
//        message.setUuid(dto.getUuid());
//        sendingOperations.convertAndSend("/topic/" + dto.getUuid() + "/comment", message);
//    }

    @MessageMapping("/share-snapshot")
    public void shareSnapshot(@Payload RegSnapshotDTO dto, SimpMessageHeaderAccessor headerAccessor) {
        log.info("세션 확인" + headerAccessor.getSessionAttributes().get("sessionId"));
//        if (headerAccessor.getSessionAttributes().get("sessionId") != "none") {
        if (true) {
            Snapshot snapshot = snapshotService.regSnapshot(dto);
            ResSnapshotDTO resSnapshotDTO = ResSnapshotDTO.from(snapshot);
            sendingOperations.convertAndSend("/topic/" + dto.getUuid() + "/snapshot", resSnapshotDTO);
        }
    }

    @MessageMapping("/share-checkup")
    public void shareCheckUp(@Payload CheckUpDTO dto) {
        checkUpService.registerCheckUpForRoom(dto);
        sendingOperations.convertAndSend("/topic/" + dto.getUuid() + "/checkup", dto);
    }

    @MessageMapping("/end-checkup")
    public void endCheckUp(@Payload UUID uuid, @Header("simpSessionId") String sessionId) {
        SendCheckUpDTO sendCheckUpDTO = checkUpService.getCheckUpResult(uuid);
        CheckUpDTO dto =checkUpService.getCheckUp(uuid);
        messagingTemplate.convertAndSendToUser(sessionId, "/queue/" + uuid + "/result/checkup",
                sendCheckUpDTO, createHeaders(sessionId));
        sendingOperations.convertAndSend("/topic/" + uuid + "/checkup", dto);
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        if (sessionId != null) headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}