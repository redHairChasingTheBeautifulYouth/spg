package com.spg.service.impl;

import com.spg.commom.*;
import com.spg.domin.Message;
import com.spg.domin.User;
import com.spg.domin.UserRoom;
import com.spg.service.*;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        UserRoom byRoomIdAndUserId = userRoomService.findByRoomIdAndUserId(roomId, user.getId());
        if (byRoomIdAndUserId == null) {
            userRoomService.applyEnterRoom(roomId ,user.getId());
        }else {
            userRoomService.updateStatus(roomId ,user.getId() ,4);
        }
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
    public JsonEntity<List<RoomMember>> queryMember(String openid ,Long roomId) {
        List<RoomMember> roomMembers = userRoomService.queryMember(roomId);
        User byOpenid = userService.findByOpenid(openid);
        RoomMember roomMember = null;
        for (RoomMember r : roomMembers) {
            if (Objects.equals(r.getId() ,byOpenid.getId())) {
                roomMember = r;
                break;
            }
        }
        if (roomMember == null) {
            return ResponseHelper.withErrorInstance(MessageCodeEnum.NOT_FRIENDS);
        }
        //房主或管理员看全部，其他看1，2，3
        if (Objects.equals(roomMember.getStatus() ,1) || Objects.equals(roomMember.getStatus() ,2)) {
            return ResponseHelper.createInstance(roomMembers ,MessageCodeEnum.QUERY_SUCCESS);
        }else {
            List<RoomMember> rm = new ArrayList<>(2<<9);
            for (RoomMember r : roomMembers) {
                if (Objects.equals(r.getStatus() ,1) || Objects.equals(r.getStatus() ,2) || Objects.equals(r.getStatus() ,3)) {
                    rm.add(r);
                }
            }
            return ResponseHelper.createInstance(rm ,MessageCodeEnum.QUERY_SUCCESS);
        }
    }

    /**
     * 通过好友申请、拒绝好友申请、将普通好友变管理员、取消管理员身份、将好友踢出，operation码分别是1，2，3，4，5
     * @param openid
     * @param byOperationUserId
     * @param roomId
     * @param operation
     * @return
     */
    @Override
    public JsonEntity<String> operationChatRoom(String openid, Long byOperationUserId ,Long roomId, Integer operation) {
        User user = userService.findByOpenid(openid);
        if (Objects.equals(operation ,1)) {
            return this.passApply(user.getId() ,byOperationUserId ,roomId);
        }else if (Objects.equals(operation ,2)) {
            return this.refuseApply(user.getId() ,byOperationUserId ,roomId);
        }else if (Objects.equals(operation ,3)) {
            return this.becomeAdmin(user.getId(), byOperationUserId, roomId);
        }else if (Objects.equals(operation ,4)) {
            return this.cancelAdmin(user.getId(), byOperationUserId, roomId);
        }else if (Objects.equals(operation ,5)) {
            return this.kickOut(user.getId(), byOperationUserId, roomId);
        }
        return null;
    }

    @Override
    public JsonEntity<LoginUser> getLoginUser(String openid ,Long roomId) {
        User user = userService.findByOpenid(openid);
        LoginUser loginUser = userRoomService.findLoginUser(roomId, user.getId());
        return ResponseHelper.createInstance(loginUser ,MessageCodeEnum.QUERY_SUCCESS);
    }

    /**
     * 通过好友申请
     * @param userId
     * @param byOperationUserId
     * @param roomId
     */
    private JsonEntity<String> passApply(Long userId ,Long byOperationUserId ,Long roomId){
        UserRoom userRoom = userRoomService.findByRoomIdAndUserId(roomId, userId);
        if (Objects.equals(userRoom.getRoleType() ,1) || Objects.equals(userRoom.getRoleType() ,2)) {
            if (!checkByOperationUserStatus(byOperationUserId ,roomId ,4)) {
                log.error("通过好友申请时，被操作人的status不是4,操作人id：" + userId +",被操作人id：" + byOperationUserId);
                return ResponseHelper.withErrorInstance(MessageCodeEnum.OPERATION_FAILED);
            }
            userRoomService.updateStatus(roomId ,byOperationUserId ,3);
            return ResponseHelper.withErrorInstance(MessageCodeEnum.OPERATION_SUCCESS);
        }else {
            log.error("通过好友申请时，操作人的status不是1或2");
            return ResponseHelper.withErrorInstance(MessageCodeEnum.NO_AUTH_OPERATION);
        }
    }

    /**
     * 拒绝好友申请
     * @param userId
     * @param byOperationUserId
     * @param roomId
     */
    private JsonEntity<String> refuseApply(Long userId ,Long byOperationUserId ,Long roomId){
        UserRoom userRoom = userRoomService.findByRoomIdAndUserId(roomId, userId);
        if (Objects.equals(userRoom.getRoleType() ,1) || Objects.equals(userRoom.getRoleType() ,2)) {
            if (!checkByOperationUserStatus(byOperationUserId ,roomId ,4)) {
                log.error("拒绝好友申请时，被操作人的status不是4,操作人id：" + userId +",被操作人id：" + byOperationUserId);
                return ResponseHelper.withErrorInstance(MessageCodeEnum.OPERATION_FAILED);
            }
            userRoomService.updateStatus(roomId ,byOperationUserId ,5);
            return ResponseHelper.withErrorInstance(MessageCodeEnum.OPERATION_SUCCESS);
        }else {
            log.error("拒绝好友申请时，操作人的status不是1或2，操作人id：" + userId);
            return ResponseHelper.withErrorInstance(MessageCodeEnum.NO_AUTH_OPERATION);
        }
    }

    /**
     * 将好友变成管理员
     * @param userId
     * @param byOperationUserId
     * @param roomId
     */
    private JsonEntity<String> becomeAdmin(Long userId ,Long byOperationUserId ,Long roomId){
        UserRoom userRoom = userRoomService.findByRoomIdAndUserId(roomId, userId);
        if (Objects.equals(userRoom.getRoleType() ,1)) {
            if (!checkByOperationUserStatus(byOperationUserId ,roomId ,3)) {
                log.error("将好友变成管理员，被操作人的status不是3,操作人id：" + userId +",被操作人id：" + byOperationUserId);
                return ResponseHelper.withErrorInstance(MessageCodeEnum.OPERATION_FAILED);
            }
            userRoomService.updateStatus(roomId ,byOperationUserId ,2);
            return ResponseHelper.withErrorInstance(MessageCodeEnum.OPERATION_SUCCESS);
        }else {
            log.error("将好友变成管理员，操作人的status不是1，操作人id：" + userId);
            return ResponseHelper.withErrorInstance(MessageCodeEnum.NO_AUTH_OPERATION);
        }
    }

    /**
     * 将好友取消管理员
     * @param userId
     * @param byOperationUserId
     * @param roomId
     */
    private JsonEntity<String> cancelAdmin(Long userId ,Long byOperationUserId ,Long roomId){
        UserRoom userRoom = userRoomService.findByRoomIdAndUserId(roomId, userId);
        if (Objects.equals(userRoom.getRoleType() ,1)) {
            if (!checkByOperationUserStatus(byOperationUserId ,roomId ,2)) {
                log.error("将好友取消管理员，被操作人的status不是2,操作人id：" + userId +",被操作人id：" + byOperationUserId);
                return ResponseHelper.withErrorInstance(MessageCodeEnum.OPERATION_FAILED);
            }
            userRoomService.updateStatus(roomId ,byOperationUserId ,3);
            return ResponseHelper.withErrorInstance(MessageCodeEnum.OPERATION_SUCCESS);
        }else {
            log.error("将好友取消管理员，操作人的status不是1，操作人id：" + userId);
            return ResponseHelper.withErrorInstance(MessageCodeEnum.NO_AUTH_OPERATION);
        }
    }

    /**
     * 将好友踢出
     * @param userId
     * @param byOperationUserId
     * @param roomId
     */
    private JsonEntity<String> kickOut(Long userId ,Long byOperationUserId ,Long roomId){
        UserRoom userRoom = userRoomService.findByRoomIdAndUserId(roomId, userId);
        if (Objects.equals(userRoom.getRoleType() ,1) || Objects.equals(userRoom.getRoleType() ,2)) {
            if (!checkByOperationUserStatus(byOperationUserId ,roomId ,3)) {
                log.error("将好友踢出，被操作人的status不是3,操作人id：" + userId +",被操作人id：" + byOperationUserId);
                return ResponseHelper.withErrorInstance(MessageCodeEnum.OPERATION_FAILED);
            }
            userRoomService.updateStatus(roomId ,byOperationUserId ,5);
            return ResponseHelper.withErrorInstance(MessageCodeEnum.OPERATION_SUCCESS);
        }else {
            log.error("将好友取消管理员，操作人的status不是1或2，操作人id：" + userId);
            return ResponseHelper.withErrorInstance(MessageCodeEnum.NO_AUTH_OPERATION);
        }
    }

    private Boolean checkByOperationUserStatus(Long byOperationUserId ,Long roomId ,Integer status){
        UserRoom byOperationUserRoom = userRoomService.findByRoomIdAndUserId(roomId, byOperationUserId);
        if (!Objects.equals(byOperationUserRoom.getRoleType() ,status)) {
            return false;
        }
        return true;
    }
}
