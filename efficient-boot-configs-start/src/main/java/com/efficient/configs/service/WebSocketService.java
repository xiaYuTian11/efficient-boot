package com.efficient.configs.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.efficient.common.util.JackSonUtil;
import com.efficient.configs.api.WebSocketHandleApi;
import com.efficient.configs.model.dto.WebSocketReceiveDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TMW
 * @since 2024/7/17 14:53
 */
@Component
@ConditionalOnProperty(name = "com.efficient.configs.webSocket.enable", havingValue = "true")
// 类似@RequestMapping注解，只不过这个只支持ws协议，不用http/https
// 这里的id是发送请求时url最后标记客户端的标识，说明这是一个ws的通用入口，内部不会细分
@ServerEndpoint("/ws/{id}")
@Slf4j
public class WebSocketService {
    // 连接数量（感觉可有可无，目前并无用到数量限制之类的）
    private static int onlineCount = 0;
    // 线程安全的方式存放客户端对象
    private static ConcurrentHashMap<String, WebSocketService> webSocketMap = new ConcurrentHashMap<>();
    private static WebSocketHandleApi webSocketHandleApi;
    // 存储客户端会话
    private Session session;
    // 记录会话ID
    private String id = "";

    @Autowired
    public void setUserService(WebSocketHandleApi webSocketHandleApi) {
        WebSocketService.webSocketHandleApi = webSocketHandleApi;
    }

    /**
     * 向指定客户端发送消息
     *
     * @param message
     * @param id
     * @throws IOException
     */
    public void sendInfo(String message, @PathParam("id") String id) throws IOException {
        if (StringUtils.isNotBlank(id) && webSocketMap.containsKey(id)) {
            webSocketMap.get(id).sendMessage(message);
        } else {
            log.error("WebSocket:目标ID[" + id + "]不存在，发送失败！");
        }
    }

    /**
     * 服务端主动推送信息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 在线数量
     *
     * @return
     */
    public synchronized int getOnLineCount() {
        return onlineCount;
    }

    // 创建连接
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        // 记录当前请求连接的客户端
        this.session = session;
        this.id = id;
        // 如果在已登陆列表中找到，需要删除老session信息，重新记录
        if (webSocketMap.containsKey(id)) {
            webSocketMap.remove(id);
            webSocketMap.put(id, this);
        } else {
            // 全新用户直接填充
            webSocketMap.put(id, this);
            addOnlineCount();
        }
        log.info("WebSocket:用户[" + id + "]已连接到WS服务器！");
        // 此时可以给客户端发送连接成功的信息
        try {
            Map<String, String> map = new HashMap<>();
            map.put("type", "register");
            // 发送消息
            sendMessage(JackSonUtil.toJson(map));
        } catch (IOException e) {
            log.error("WebSocket:用户网络异常，发送失败！", e);
        }
    }

    /**
     * 增加在线数量
     */
    public synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    /**
     * 接收客户端消息
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("WebSocket:接收到用户[" + id + "]发送消息，报文：" + message);
        if (StringUtils.isNotBlank(message)) {
            // 解析报文内容，是发给服务端的还是发给其他客户端的
            try {
                WebSocketReceiveDTO receiveDTO = JackSonUtil.toObject(message, WebSocketReceiveDTO.class);
                String receiveDTOMessage = receiveDTO.getMessage();
                List<String> toIdList = receiveDTO.getToIdList();
                if (CollUtil.isNotEmpty(toIdList)) {
                    // 发给其他客户端的
                    toIdList.stream().filter(et -> webSocketMap.containsKey(et)).forEach(et -> {
                        try {
                            webSocketMap.get(et).sendMessage(receiveDTOMessage);
                        } catch (IOException e) {
                            log.error("WebSocket:目标ID[" + et + "]不存在，发送失败！", e);
                        }
                    });
                } else {
                    // 发给服务端的
                   WebSocketService.webSocketHandleApi.receive(receiveDTOMessage);
                }
            } catch (Exception e) {
                log.error("WebSocket 接收消息处理异常！", e);
            }
        }
    }

    /**
     * 异常处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket:用户错误", error);
    }

    // 客户端主动断开连接
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(id)) {
            webSocketMap.remove(id);
            subOnlineCount();
        }
        log.info("WebSocket:用户[" + id + "]已断开WS服务器连接！");
    }

    /**
     * 减少在线数量
     */
    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }
}
