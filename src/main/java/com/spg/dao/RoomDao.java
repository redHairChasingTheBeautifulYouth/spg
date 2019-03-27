package com.spg.dao;

import com.spg.domin.Room;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDao {

    void insertOne(@Param("room") Room room);
}
