package com.spg.service.impl;

import com.spg.commom.JsonEntity;
import com.spg.commom.MessageCodeEnum;
import com.spg.commom.ResponseHelper;
import com.spg.service.BizService;
import com.spg.service.RoomService;
import com.spg.service.UserRoomService;
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

    @Override
    public JsonEntity<String> checkUserRoom(Long roomId ,String token) {
        Long roomCount = roomService.isExist(roomId);
        if (Objects.equals(roomCount ,0L)) {
            return ResponseHelper.withErrorInstance(MessageCodeEnum.ROOM_NOT_EXIST);
        }
        Long countUserRoom = userRoomService.isExist(roomId);
        //成为房主
        if (Objects.equals(countUserRoom ,0L)) {

        }
    }
}
