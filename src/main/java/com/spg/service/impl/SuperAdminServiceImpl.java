package com.spg.service.impl;

import com.spg.commom.JsonEntity;
import com.spg.commom.MessageCodeEnum;
import com.spg.commom.ResponseHelper;
import com.spg.commom.SuperAdminLogin;
import com.spg.dao.SuperAdminDao;
import com.spg.domin.Config;
import com.spg.domin.Room;
import com.spg.domin.SuperAdmin;
import com.spg.service.ConfigService;
import com.spg.service.RoomService;
import com.spg.service.SuperAdminServcie;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author
 * @date 03/27/19 12:56
 */
@Service
public class SuperAdminServiceImpl implements SuperAdminServcie {

    @Resource
    private SuperAdminDao superAdminDao;

    @Resource
    private RoomService roomService;

    @Resource
    private ConfigService configService;

    @Override
    public JsonEntity<SuperAdmin> login(SuperAdminLogin superAdminLogin) {
        SuperAdmin admin = superAdminDao.findByAccount(superAdminLogin.getAccount());
        if (admin == null) {
            return ResponseHelper.withErrorInstance(MessageCodeEnum.SUPER_ADMIN_LOGIN_ERROR);
        }else {
            synchronized (this) {
                if (admin.getErrorNum() > 100L) {
                    return ResponseHelper.withErrorInstance(MessageCodeEnum.ERROR_NUM_MAX);
                }
                if (!Objects.equals(admin.getPassword() ,superAdminLogin.getPassward())) {
                    superAdminDao.updateLoginErrorTime(admin.getId() ,admin.getErrorNum() + 1L);
                    return ResponseHelper.withErrorInstance(MessageCodeEnum.SUPER_ADMIN_LOGIN_ERROR);
                }else {
                    return ResponseHelper.createInstance(admin ,MessageCodeEnum.LOGIN_SUCCESS);
                }
            }
        }
    }

    @Override
    public JsonEntity<String> createRoom(){
        Room room = new Room();
        room.setRoomName("小私群");
        roomService.insertOne(room);
        List<Config> domainNames = configService.findDomainNamesCanUse();
        if (domainNames.isEmpty()) {
            return ResponseHelper.withErrorInstance(MessageCodeEnum.NO_DOMAIN_NAMES);
        }
        return ResponseHelper.createInstance(domainNames.get(0).getDomainName() ,MessageCodeEnum.CREATE_SUCCESS);
    }
}
