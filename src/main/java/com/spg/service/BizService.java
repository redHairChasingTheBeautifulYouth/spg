package com.spg.service;

import com.spg.commom.JsonEntity;
import com.spg.commom.LoginUser;
import com.spg.commom.ReturnChatMessage;
import com.spg.commom.RoomMember;
import com.spg.domin.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 20:17
 * @Description:
 */
public interface BizService {

    JsonEntity<String> enterRoom(Long roomId ,String openid);

    JsonEntity<String> applyRnterRoom(Long roomId ,String openid);

    JsonEntity<List<ReturnChatMessage>> chatRecord(String openid , Long roomId , Integer pageSize , Integer pageNo);

    JsonEntity<List<RoomMember>> queryMember(String openid ,Long roomId);

    JsonEntity<String> operationChatRoom(String openid ,Long byOperationUserId ,Long roomId ,Integer operation);


    JsonEntity<LoginUser> getLoginUser(String openid ,Long roomId);
}
