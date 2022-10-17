package vip.breakpoint.service.impl;

import vip.breakpoint.cache.LocalUserCache;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.service.UserStoreService;

import java.io.Serializable;

/**
 * @author : breakpoint
 * create on 2022/10/17
 * 欢迎关注公众号 《代码废柴》
 */
public class DefaultUserStoreServiceImpl implements UserStoreService {

    @Override
    public <T extends Serializable> boolean storeUserMessage(String userToken, T userMessage) throws EasyToolException {
        LocalUserCache.addUser(userToken, userMessage);
        return true;
    }

    @Override
    public <T extends Serializable> T getUserMessageByUserToken(String userToken, Class<T> clazz) throws EasyToolException {
        return LocalUserCache.getLoginUser(userToken, clazz);
    }

    @Override
    public Object getUserMessageByUserToken(String userToken) throws EasyToolException {
        return LocalUserCache.getLoginUser(userToken);
    }
}
