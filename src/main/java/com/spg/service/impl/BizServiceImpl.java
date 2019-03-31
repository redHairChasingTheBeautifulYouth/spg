package com.spg.service.impl;

import com.spg.commom.JsonEntity;
import com.spg.commom.MessageCodeEnum;
import com.spg.commom.ResponseHelper;
import com.spg.domin.User;
import com.spg.domin.UserRoom;
import com.spg.service.BizService;
import com.spg.service.RoomService;
import com.spg.service.UserRoomService;
import com.spg.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
