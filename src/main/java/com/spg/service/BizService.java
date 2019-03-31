package com.spg.service;

import com.spg.commom.JsonEntity;

/**
 * @Auther: trevor
 * @Date: 2019\3\31 0031 20:17
 * @Description:
 */
public interface BizService {

    JsonEntity<String> enterRoom(Long roomId ,String openid);

    JsonEntity<String> applyRnterRoom(Long roomId ,String openid);
}
