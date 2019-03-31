package com.spg.service.impl;

import com.spg.dao.UserRoomDao;
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

    private UserRoomDao userRoomDao;

    @Override
    public Long isExist(Long roomId) {
        return userRoomDao.isExist(roomId);
    }

    @Override
    public void becomeRoomMaster(Long roomId ,Long userId){
        UserRoom userRoom = new UserRoom();
        userRoom.setUserId(userId);
        userRoom.setRoomId(roomId);
        userRoom.setRoleType(1);
        userRoomDao.insertOne(userRoom);
    }

    @Override
    public UserRoom findByRoomIdAndUserId(Long roomId ,Long userId) {
        return userRoomDao.findByRoomIdAndUserId(roomId ,userId);
    }

    @Override
    public void applyEnterRoom (Long roomId ,Long userId) {
        UserRoom userRoom = new UserRoom();
        userRoom.setUserId(userId);
        userRoom.setRoomId(roomId);
        userRoom.setRoleType(4);
        userRoomDao.insertOne(userRoom);
    }


}
