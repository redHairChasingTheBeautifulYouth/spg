package com.spg.web.webSocket;

import com.spg.commom.ChatUser;
import com.spg.commom.WebKeys;
import com.spg.domin.Message;
import com.spg.domin.User;
import com.spg.service.UserService;
import com.spg.util.SessionUtil;
import com.spg.util.TokenUtil;
import com.spg.web.webSocket.bo.MessageModelEnum;
import com.spg.web.webSocket.config.ChatServerConfigurator;
import com.spg.web.webSocket.bo.MessageModel;
import com.spg.web.webSocket.bo.Transcript;
import com.spg.web.webSocket.config.HttpSessionInvalidationListener;
import com.spg.web.webSocket.decoder.ChatDecoder;
import com.spg.web.webSocket.encoder.ChatEncoder;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

    private ConcurrentHashMap<String , Set<Transcript>> userMap;

    @OnOpen
    public void startChatChannel(EndpointConfig config ,Session session) throws IOException, EncodeException {
        this.session = session;
        String token = session.getRequestParameterMap().get(WebKeys.TOKEN).get(0);
        if (token == null) {
            this.session.getBasicRemote().sendObject(new MessageModel(MessageModelEnum.TOKEN_ERROR));
            this.session.close();
            return;
        }
        //解析token
        Map<String, Object> claims = TokenUtil.getClaimsFromToken(token);
        String openid = (String) claims.get(WebKeys.OPEN_ID);
        String hash = (String) claims.get("hash");
        String timestamp = String.valueOf(claims.get("timestamp"));
        //三者必须存在,少一样说明token被篡改
        if (openid == null || hash == null || timestamp == null) {
            this.session.getBasicRemote().sendObject(new MessageModel(MessageModelEnum.TOKEN_ERROR));
            this.session.close();
            return;
        }
        //token过期
        if(checkTimeStamp(timestamp)){
            this.session.getBasicRemote().sendObject(new MessageModel(MessageModelEnum.TOKEN_TIME_ERROR));
            this.session.close();
            return;
        }

        this.endpointConfig = (ServerEndpointConfig) config;
        ChatServerConfigurator csc = (ChatServerConfigurator) endpointConfig.getConfigurator();
        this.userMap = csc.getUserMap();


//        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties().get("httpSession");
//        HttpSessionInvalidationListener listener = new HttpSessionInvalidationListener(this);
//        httpSession.setAttribute("httpSession-listenter" ,listener);
//        httpSession.setMaxInactiveInterval(60);
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

    private Boolean checkTokenTimeStamp(){}

    private Boolean checkToken(String token) throws IOException {

        //解析token
        Map<String, Object> claims = TokenUtil.getClaimsFromToken(token);
        String openid = (String) claims.get(WebKeys.OPEN_ID);
        String hash = (String) claims.get("hash");
        String timestamp = String.valueOf(claims.get("timestamp"));
        //三者必须存在,少一样说明token被篡改
        if (openid == null || hash == null || timestamp == null) {
            this.session.close();
        }
        //三者合法才通过
        if(!(checkOpenidAndHash(openid,hash) && checkTimeStamp(timestamp))){
            this.session.close();
        }
        return Boolean.TRUE;
    }

    /**
     * 检查token是否过期
     * 开发时:指定1分钟,可以更好的看到效果
     * @param timestamp
     *
     *
     * @return
     */
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

    /**
     * 判断opendid,hash是否合法
     * true合法
     * false不合法
     * @param openid
     * @return
     */
    private Boolean checkOpenidAndHash(String openid,String hash){
        User user = userService.findUserByOpenidContainOpenidAndHash(openid);
        if(user.getOpenid() != null){
            //对比
            if(openid.equals(user.getOpenid()) && hash.equals(user.getHash())){
                return true;
            }
        }
        return false;
    }

    void processNewUser(ChatUser chatUser){


    }

    public void notifySessionUnboundMe(HttpSession s) {
        try {
            this.session.getBasicRemote().sendText("Session" + s.getId() + "was invalidated");
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE ,"HttpSession ended"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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





}
