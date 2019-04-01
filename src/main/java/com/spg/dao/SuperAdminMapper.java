package com.spg.dao;

import com.spg.domin.SuperAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminMapper {

    /**
     * 根据账号发现用户
     * @param account
     * @return
     */
    SuperAdmin findByAccount(@Param("account") String account );

    /**
     * 更新开始错误登陆的次数
     * @param id
     * @param errorNum
     */
    void updateLoginErrorNum(@Param("id") Long id ,@Param("errorNum") Long errorNum);
}
