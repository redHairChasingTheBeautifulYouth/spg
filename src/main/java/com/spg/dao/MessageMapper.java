package com.spg.dao;

import com.spg.domin.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author trevor
 * @date 04/01/19 13:32
 */
@Repository
public interface MessageMapper {

    void insertOne(@Param("message") Message message);
}
