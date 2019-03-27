package com.spg.service.impl;

import com.spg.dao.RoomDao;
import com.spg.domin.Room;
import com.spg.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoomServciceImpl implements RoomService {

    @Resource
    private RoomDao roomDao;

    @Override
    public void insertOne(Room room) {
        roomDao.insertOne(room);
    }
}
