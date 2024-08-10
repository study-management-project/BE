package com.fisa.study.management.domain.room.controller;

import com.fisa.study.management.domain.room.dto.TextMessage;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TextController {

    private final SimpMessageSendingOperations sendingOperations;
    private final RoomService roomService;
    private ConcurrentHashMap<Long, String> cacheMap = new ConcurrentHashMap<>();

    @MessageMapping("/send-text")
    public void handleTextMessage(@Payload TextMessage message) {
        Long roomId = message.getRoomId();
        String newContent = message.getContent();

        // 캐시에서 현재 콘텐츠를 가져옴
        String cachedContent = cacheMap.get(roomId);

        if (cachedContent != null && cachedContent.equals(newContent)) {
            // 캐시 히트: 캐시된 내용과 동일하면 바로 반환
            log.info("Cache Hit");
            sendingOperations.convertAndSend("/topic/" + roomId, cachedContent);
            return;
        }

        // 캐시 미스: 새로운 콘텐츠로 업데이트
        log.info("Cache Miss");

        // 메시지를 받은 후 해당 Room의 content를 업데이트
        Room room = roomService.updateRoom(roomId, newContent);

        // 캐시 업데이트
        cacheMap.put(roomId, newContent);

        // 업데이트된 content를 topic 구독자들에게 뿌림
        sendingOperations.convertAndSend("/topic/" + roomId, newContent);
    }
}
