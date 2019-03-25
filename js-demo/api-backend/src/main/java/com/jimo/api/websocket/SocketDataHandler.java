package com.jimo.api.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author jimo
 * @date 19-3-25 上午10:37
 */
public class SocketDataHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("received----->" + payload);
        session.sendMessage(new TextMessage("BACK>>" + payload));
    }
}
