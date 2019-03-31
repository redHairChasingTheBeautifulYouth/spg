package com.spg.service.impl;

import com.spg.dao.UserRoomDao;
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
}
