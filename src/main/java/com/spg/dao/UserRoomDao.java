package com.spg.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 20:15
 * @Description:
 */
@Repository
public interface UserRoomDao {

    Long isExist(@Param("roomId") Long roomId);
}
