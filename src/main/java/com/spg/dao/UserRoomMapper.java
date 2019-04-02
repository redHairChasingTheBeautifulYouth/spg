package com.spg.dao;

import com.spg.commom.LoginUser;
import com.spg.commom.RoomMember;
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

    List<RoomMember> queryMember(@Param("roomId") Long roomId);

    void updateStatus(@Param("roomId") Long roomId ,@Param("userId") Long userId ,@Param("status") Integer status);

    LoginUser findLoginUser(@Param("roomId") Long roomId ,@Param("userId") Long userId);
}
