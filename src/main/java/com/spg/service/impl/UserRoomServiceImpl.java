package com.spg.service.impl;

import com.spg.dao.UserRoomMapper;
import com.spg.domin.UserRoom;
import com.spg.service.UserRoomService;
import org.springframework.stereotype.Service;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 20:12
 * @Description:
 */
@Service
public class UserRoomServiceImpl implements UserRoomService {

    private UserRoomMapper userRoomMapper;

    @Override
    public Long isExist(Long roomId) {
        return userRoomMapper.isExist(roomId);
    }

    @Override
    public void becomeRoomMaster(Long roomId ,Long userId){
        UserRoom userRoom = new UserRoom();
        userRoom.setUserId(userId);
        userRoom.setRoomId(roomId);
        userRoom.setRoleType(1);
        userRoomMapper.insertOne(userRoom);
    }

    @Override
    public UserRoom findByRoomIdAndUserId(Long roomId ,Long userId) {
        return userRoomMapper.findByRoomIdAndUserId(roomId ,userId);
    }

    @Override
    public void applyEnterRoom (Long roomId ,Long userId) {
        UserRoom userRoom = new UserRoom();
        userRoom.setUserId(userId);
        userRoom.setRoomId(roomId);
        userRoom.setRoleType(4);
        userRoomMapper.insertOne(userRoom);
    }


}
