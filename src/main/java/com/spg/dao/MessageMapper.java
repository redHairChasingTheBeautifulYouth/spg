package com.spg.dao;

import com.spg.domin.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author trevor
 * @date 04/01/19 13:32
 */
@Repository
public interface MessageMapper {

    void insertOne(@Param("message") Message message);

    List<Message> findMeeagePage(@Param("roomId") Long roomId ,@Param("start") Integer start ,@Param("pageSize") Integer pageSize);
}
