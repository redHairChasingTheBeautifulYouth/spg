package com.spg.dao;

import com.spg.domin.User;
import com.spg.domin.UserRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 20:15
 * @Description:
 */
@Repository
public interface UserRoomMapper {

    Long isExist(@Param("roomId") Long roomId);

    void insertOne(@Param("userRoom")UserRoom userRoom);

    UserRoom findByRoomIdAndUserId(@Param("roomId") Long roomId ,@Param("userId") Long userId);

    List<User> queryMember(@Param("roomId") Long roomId);
}
