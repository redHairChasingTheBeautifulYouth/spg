package com.spg.service.impl;

import com.spg.commom.JsonEntity;
import com.spg.commom.MessageCodeEnum;
import com.spg.commom.ResponseHelper;
import com.spg.commom.ReturnChatMessage;
import com.spg.domin.Message;
import com.spg.domin.User;
import com.spg.domin.UserRoom;
import com.spg.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 20:17
 * @Description:
 */
@Service
public class BizServiceImpl implements BizService {

    @Resource
    private RoomService roomService;

    @Resource
    private UserRoomService userRoomService;

    @Resource
    private UserService userService;

    @Resource
    private MessageService messageService;


    @Override
    public JsonEntity<String> enterRoom(Long roomId ,String openid) {
        Long roomCount = roomService.isExist(roomId);
        if (Objects.equals(roomCount ,0L)) {
            return ResponseHelper.withErrorInstance(MessageCodeEnum.ROOM_NOT_EXIST);
        }
        String roomIdStr = String.valueOf(roomId).intern();
        User user = userService.findByOpenid(openid);
        synchronized (roomIdStr) {
            Long countUserRoom = userRoomService.isExist(roomId);
            //成为房主
            if (Objects.equals(countUserRoom ,0L)) {
                //插入一条数据
                userRoomService.becomeRoomMaster(roomId ,user.getId());
                return ResponseHelper.createInstanceWithOutData(MessageCodeEnum.RNTER_ROOM_SUCCESS);
            }
        }
        UserRoom userRoom = userRoomService.findByRoomIdAndUserId(roomId ,user.getId());
        if (userRoom == null || Objects.equals(userRoom.getRoleType() ,4)) {
            return ResponseHelper.createInstanceWithOutData(MessageCodeEnum.NOT_FRIENDS);
        }else {
            return ResponseHelper.createInstanceWithOutData(MessageCodeEnum.RNTER_ROOM_SUCCESS);
        }
    }

    @Override
    public JsonEntity<String> applyRnterRoom(Long roomId ,String openid) {
        User user = userService.findByOpenid(openid);
        userRoomService.applyEnterRoom(roomId ,user.getId());
        return ResponseHelper.createInstanceWithOutData(MessageCodeEnum.APPLY_SUCCESS);
    }

    @Override
    public JsonEntity<List<ReturnChatMessage>> chatRecord(String openid ,Long roomId, Integer pageSize, Integer pageNo) {
        User user = userService.findByOpenid(openid);
        List<Message> meeagePage = messageService.findMeeagePage(roomId, pageSize, pageNo);
        List<ReturnChatMessage> returnChatMessageList = new ArrayList<>(2<<7);
        meeagePage.forEach(message -> {
            ReturnChatMessage returnChatMessage = new ReturnChatMessage();
            returnChatMessage.setAppName(message.getAppName());
            if (Objects.equals(user.getId() ,message.getUserId())) {
                returnChatMessage.setIsMyself(1);
            }else {
                returnChatMessage.setIsMyself(0);
            }
            returnChatMessage.setMessage(message.getMessage());
            returnChatMessage.setMessageType(message.getMessageType());
            returnChatMessage.setPictureUrl(message.getPictureUrl());
            returnChatMessageList.add(returnChatMessage);
        });
        return ResponseHelper.createInstance(returnChatMessageList ,MessageCodeEnum.CREATE_SUCCESS);
    }

    @Override
    public JsonEntity<List<User>> queryMember(Long roomId) {
        List<User> users = userRoomService.queryMember(roomId);
        return ResponseHelper.createInstance(users ,MessageCodeEnum.QUERY_SUCCESS);
    }
}
