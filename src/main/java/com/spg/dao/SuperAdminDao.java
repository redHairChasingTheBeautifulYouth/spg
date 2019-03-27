package com.spg.dao;

import com.spg.domin.SuperAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminDao {

    /**
     * 根据账号密码发现用户
     * @param account
     * @param password
     * @return
     */
    SuperAdmin findByAccountAndPassword(@Param("account") String account ,@Param("password") String password);


    /**
     * 根据账号发现用户
     * @param account
     * @return
     */
    SuperAdmin findByAccount(@Param("account") String account );

    /**
     * 更新开始错误登陆的时间
     * @param time
     */
    void updateLoginErrorTime(@Param("userId") Long userId ,@Param("time") Long time);
}
