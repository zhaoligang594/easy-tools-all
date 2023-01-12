package vip.breakpoint.service.impl;

import vip.breakpoint.bean.LoginUserMsg;
import vip.breakpoint.cache.LocalUserCache;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.service.UserStoreService;

import java.io.Serializable;

/**
 * 默认的用户操作
 * 用户可以实现自定义的功能 操作用户信息的存储
 *
 * @author : breakpoint
 * create on 2022/10/17
 * 欢迎关注公众号 《代码废柴》
 */
public class DefaultUserStoreServiceImpl implements UserStoreService {

    @Override
    public <T extends Serializable> boolean storeUserMessage(String userToken, T userMessage) {
        LocalUserCache.addUser(userToken, userMessage);
        return true;
    }

    @Override
    public <T extends Serializable> T getUserMessageByUserToken(String userToken, Class<T> clazz) {
        return LocalUserCache.getLoginUser(userToken, clazz);
    }

    @Override
    public <T extends Serializable> boolean storeUserMessageV2(String userToken, LoginUserMsg<T> userMessage) {
        LocalUserCache.addUser(userToken, userMessage);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> LoginUserMsg<T> getUserMessageByUserTokenV2(String userToken, Class<T> clazz) {
        return (LoginUserMsg<T>) LocalUserCache.getLoginUser(userToken, LoginUserMsg.class);
    }

    @Override
    public Object getUserMessageByUserToken(String userToken) {
        return LocalUserCache.getLoginUser(userToken);
    }
}
