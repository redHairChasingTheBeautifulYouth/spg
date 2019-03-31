package com.spg.service.impl;

import com.spg.dao.RoomMapper;
import com.spg.domin.Room;
import com.spg.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoomServciceImpl implements RoomService {

    @Resource
    private RoomMapper roomMapper;

    @Override
    public void insertOne(Room room) {
        roomMapper.insertOne(room);
    }

    @Override
    public Long isExist(Long roomId){
        return roomMapper.isExist(roomId);
    }
}
