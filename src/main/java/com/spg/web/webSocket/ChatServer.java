package com.spg.web.webSocket;

import com.spg.commom.ChatUser;
import com.spg.commom.WebKeys;
import com.spg.domin.User;
import com.spg.service.UserService;
import com.spg.util.TokenUtil;
import com.spg.web.webSocket.bo.MessageModel;
import com.spg.web.webSocket.bo.MessageModelEnum;
import com.spg.web.webSocket.bo.Transcript;
import com.spg.web.webSocket.config.ChatServerConfigurator;
import com.spg.web.webSocket.decoder.ChatDecoder;
import com.spg.web.webSocket.encoder.ChatEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
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

    private Session session;

    private ServerEndpointConfig endpointConfig;

    private ConcurrentHashMap<String , Transcript> userMap;

    @OnOpen
    public void startChatChannel(EndpointConfig config ,Session session) throws IOException, EncodeException {
        this.session = session;
        String token = session.getRequestParameterMap().get(WebKeys.TOKEN).get(0);
        if (token == null) {
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE ,MessageModelEnum.TOKEN_ERROR.getCode()));
            return;
        }
        //解析token
        Map<String, Object> claims = TokenUtil.getClaimsFromToken(token);
        String openid = (String) claims.get(WebKeys.OPEN_ID);
        String hash = (String) claims.get("hash");
        String timestamp = String.valueOf(claims.get("timestamp"));
        //三者必须存在,少一样说明token被篡改
        if (openid == null || hash == null || timestamp == null) {
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE ,MessageModelEnum.TOKEN_ERROR.getCode()));
            return;
        }
        //token是否合法
        if(!(checkOpenidAndHash(openid,hash))){
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE ,MessageModelEnum.TOKEN_ERROR.getCode()));
            return;
        }
        //token过期
        if(checkTimeStamp(timestamp)){
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE ,MessageModelEnum.TOKEN_TIME_ERROR.getCode()));
            return;
        }
        this.endpointConfig = (ServerEndpointConfig) config;
        ChatServerConfigurator csc = (ChatServerConfigurator) endpointConfig.getConfigurator();
        this.userMap = csc.getUserMap();


    }

    @OnMessage
    public void handleChatMessage(MessageModel messageModel) {
        switch (messageModel.getCode()) {
            case "CHAT_DATE_MESSAGE"
        }
    }

    @OnError
    public void myError(Throwable t){

    }

    @OnClose
    public void endChatChannel(){

    }


    /**
     * 聊天加入新的用户
     * @param openid
     */
    private void addNewUser(String roomId ,String openid){
        User user = userService.findByOpenid(openid);
        Transcript  transcript = userMap.get(roomId);
        CopyOnWriteArrayList<ChatUser> chatUsers = transcript.getUsers();
        ChatUser chatUser = new ChatUser();
        chatUser.setId(user.getId());
        chatUser.setAppName(user.getAppName());
        chatUser.setPictureUrl(user.getAppPictureUrl());
        chatUsers.add(chatUser);

    }


    private String getCurrentUserName(){
        return (String) session.getUserProperties().get(USERNAME_KEY);
    }

    private void broadcastTranscriptUpdate(){
        for (Session nextSession : session.getOpenSessions()) {
            ChatUpda
        }
    }

    private  void addMessage(String message){
        this.transcript.addEntry(this.getCurrentUserName() ,message);
        this
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
