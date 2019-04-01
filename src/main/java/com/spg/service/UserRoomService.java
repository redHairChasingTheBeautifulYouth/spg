package com.spg.service;

import com.spg.domin.User;
import com.spg.domin.UserRoom;

import java.util.List;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 20:12
 * @Description:
 */
public interface UserRoomService {

    Long isExist(Long roomId);

    void becomeRoomMaster(Long roomId ,Long userId);

    UserRoom findByRoomIdAndUserId(Long roomId ,Long userId);

    void applyEnterRoom(Long roomId ,Long userId);

    List<User> queryMember(Long roomId);
}
