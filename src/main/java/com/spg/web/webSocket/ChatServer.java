package com.spg.web.webSocket;

import com.spg.commom.*;
import com.spg.domin.Message;
import com.spg.domin.User;
import com.spg.service.MessageService;
import com.spg.service.UserService;
import com.spg.util.TokenUtil;
import com.spg.web.webSocket.bo.MessageModelEnum;
import com.spg.web.webSocket.config.ChatServerConfigurator;
import com.spg.web.webSocket.decoder.ChatDecoder;
import com.spg.web.webSocket.encoder.ChatEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Auther: trevor
 * @Date: 2019\3\29 0029 22:24
 * @Description:
 */
@ServerEndpoint(
        value = "/chat/{chatRoomId}}",
        configurator = ChatServerConfigurator.class,
        encoders = {ChatEncoder.class},
        decoders = {ChatDecoder.class}
)
@Component
@Slf4j
public class ChatServer {

    @Resource
    private UserService userService;

    @Resource
    private MessageService messageService;

    private Session mySession;

    private ServerEndpointConfig endpointConfig;

    private ConcurrentHashMap<String , CopyOnWriteArrayList<ChatMessage>> chatMessageMap;

    private ConcurrentHashMap<String ,CopyOnWriteArrayList<Session>> sessionUsers;

    @OnOpen
    public void startChatChannel(@PathParam("chatRoomId") String roomId  ,EndpointConfig config ,Session session) throws IOException, EncodeException {
        this.mySession = session;
        String token = session.getRequestParameterMap().get(WebKeys.TOKEN).get(0);
        if (token == null) {
            this.mySession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE ,MessageModelEnum.TOKEN_ERROR.getCode()));
            return;
        }
        //解析token
        Map<String, Object> claims = TokenUtil.getClaimsFromToken(token);
        String openid = (String) claims.get(WebKeys.OPEN_ID);
        String hash = (String) claims.get("hash");
        String timestamp = String.valueOf(claims.get("timestamp"));
        //三者必须存在,少一样说明token被篡改
        if (openid == null || hash == null || timestamp == null) {
            this.mySession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE ,MessageModelEnum.TOKEN_ERROR.getCode()));
            return;
        }
        //token是否合法
        if(!(checkOpenidAndHash(openid,hash))){
            this.mySession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE ,MessageModelEnum.TOKEN_ERROR.getCode()));
            return;
        }
        //token过期
        if(checkTimeStamp(timestamp)){
            this.mySession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE ,MessageModelEnum.TOKEN_TIME_ERROR.getCode()));
            return;
        }
        User user = userService.findByOpenid(openid);
        this.mySession.getUserProperties().put("user" ,user);
        this.endpointConfig = (ServerEndpointConfig) config;
        ChatServerConfigurator csc = (ChatServerConfigurator) endpointConfig.getConfigurator();
        this.chatMessageMap = csc.getMessages();
        this.sessionUsers = csc.getSessions();

    }

    @OnMessage
    public void handleChatMessage(@PathParam("chatRoomId") String roomId, ReceiveChatMessage receiveChatMessage) throws IOException, EncodeException {
        User user = (User) this.mySession.getUserProperties().get("user");
        ChatMessage chatMessage = new ChatMessage(user ,receiveChatMessage);
        if (Objects.equals(receiveChatMessage.getMessageType() ,1)) {
            this.addNewUser(roomId);
        }
        this.addMessage(roomId ,chatMessage);
        this.broadcastMessage(roomId ,new ReturnChatMessage(0 ,chatMessage));
        this.sendMessageToMyself(new ReturnChatMessage(1 ,chatMessage));
        this.saveChatMessage(chatMessage ,roomId);
    }

    @OnError
    public void myError(Throwable t){

    }

    @OnClose
    public void endChatChannel(){

    }


    /**
     * 聊天加入新的用户
     */
    private void addNewUser(String roomId){
        CopyOnWriteArrayList<Session> sessions = this.sessionUsers.get(roomId);
        sessions.add(mySession);
    }

    /**
     * 加入聊天信息
     * @param roomId
     * @param chatMessage
     */
    private void addMessage(String roomId ,ChatMessage chatMessage){
        CopyOnWriteArrayList<ChatMessage> chatMessages = this.chatMessageMap.get(roomId);
        chatMessages.add(chatMessage);
    }

    /**
     * 给这个聊天室的人广播消息(除了自己)
     */
    private void broadcastMessage(String roomId ,ReturnChatMessage returnmyseChatMessage) throws IOException, EncodeException {
        CopyOnWriteArrayList<Session> sessions = this.sessionUsers.get(roomId);
        for (Session session : sessions) {
            if (session.equals(this.mySession)) {
                continue;
            }
            session.getBasicRemote().sendObject(returnmyseChatMessage);
        }
    }

    /**
     * 给自己发消息
     * @param returnmyseChatMessage
     * @throws IOException
     * @throws EncodeException
     */
    private void sendMessageToMyself(ReturnChatMessage returnmyseChatMessage) throws IOException, EncodeException {
        this.mySession.getBasicRemote().sendObject(returnmyseChatMessage);
    }

    /**
     * 保存聊天记录
     * @param chatMessage
     */
    private void saveChatMessage(ChatMessage chatMessage ,String roomId){
        Message message = new Message(chatMessage ,roomId);
        messageService.generateMessage(message);
    }

    private boolean checkTimeStamp(String timestamp) {
        // 有效期: 30分钟,单位: ms
        long expires_in = 30 * 1000 * 20;
        long timestamp_long = Long.parseLong(timestamp);
        //两者相差的时间,单位(ms)
        long time = System.currentTimeMillis() - timestamp_long;
        if(time > expires_in){
            //过期
            return false;
        }else {
            return true;
        }
    }

    private Boolean checkOpenidAndHash(String openid,String hash){
        User user = userService.findByOpenid(openid);
        if(user.getOpenid() != null){
            //对比
            if(openid.equals(user.getOpenid()) && hash.equals(user.getHash())){
                return true;
            }
        }
        return false;
    }

}
