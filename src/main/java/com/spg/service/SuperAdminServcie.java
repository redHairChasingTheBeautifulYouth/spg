package com.spg.service;

import com.spg.commom.JsonEntity;
import com.spg.commom.SuperAdminLogin;
import com.spg.domin.SuperAdmin;

/**
 * @author trevor
 * @date 03/27/19 12:55
 */
public interface SuperAdminServcie {

    /**
     * 登陆
     * @param superAdminLogin
     * @return
     */
    JsonEntity<SuperAdmin> login(SuperAdminLogin superAdminLogin);

    /**
     * 创建房间
     * @return
     */
    JsonEntity<String> createRoom();
}
