package com.fisa.study.management.global.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations sendingOperations;
    // room : session set
    private final Map<String, Set<String>> roomSubscribers = new ConcurrentHashMap<>();
    // unsubscribe event에서 roomId를 추출할 수 없기 때문에 roomId와 session을 묶어서 저장함
    private final Map<String, String> subscriptionToRoom = new ConcurrentHashMap<>();

//    @EventListener
//    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
//        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//        String destination = headers.getDestination();
//        String roomId = extractRoomId(destination);
//        String sessionId = headers.getSessionId();
//        String subscriptionId = headers.getSubscriptionId();
//
//        if (roomId != null && sessionId != null && subscriptionId != null) {
//            roomSubscribers.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(sessionId);
//            subscriptionToRoom.put(subscriptionId, roomId);
//            int subscriberCount = getSubscriberCount(roomId);
//            sendingOperations.convertAndSend("/topic/room/" + roomId + "/subscribers", subscriberCount);
//        }
//    }
//
//    @EventListener
//    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
//        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//        String sessionId = headers.getSessionId();
//        String subscriptionId = headers.getSubscriptionId();
//
//        String roomId = subscriptionToRoom.remove(subscriptionId);
//        if (roomId != null) {
//            Set<String> subscribers = roomSubscribers.get(roomId);
//            if (subscribers != null) {
//                subscribers.remove(sessionId);
//                if (subscribers.isEmpty()) {
//                    roomSubscribers.remove(roomId);
//                }
//            }
//            int subscriberCount = getSubscriberCount(roomId);
//            sendingOperations.convertAndSend("/topic/room/" + roomId + "/subscribers", subscriberCount);
//        }
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        String sessionId = event.getSessionId();
//        roomSubscribers.forEach((roomId, subscribers) -> {
//            if (subscribers.remove(sessionId)) {
//                log.info("disconnect -> Room {} has {} subscribers", roomId, subscribers.size());
//                int subscriberCount = subscribers.size();
//                sendingOperations.convertAndSend("/topic/room/" + roomId + "/subscribers", subscriberCount);
//            }
//            if (subscribers.isEmpty()) {
//                roomSubscribers.remove(roomId);
//            }
//        });
//    }
//
//    private String extractRoomId(String destination) {
//        if (destination != null && destination.startsWith("/topic/")) {
//            return destination.substring("/topic/".length());
//        }
//        return null;
//    }
//
//    public int getSubscriberCount(String roomId) {
//        Set<String> subscribers = roomSubscribers.get(roomId);
//        return subscribers != null ? subscribers.size() : 0;
//    }
}
