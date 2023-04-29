package vip.breakpoint.service;

import vip.breakpoint.bean.LoginUserMsg;
import vip.breakpoint.exception.EasyToolException;

import java.io.Serializable;

/**
 * @author : breakpoint
 * create on 2022/10/17
 * 欢迎关注公众号 《代码废柴》
 */
public interface UserStoreService {


    /**
     * 存储登录的用户
     *
     * @param userToken   token
     * @param userMessage 用户信息
     * @param <T>         包装类
     * @return true or false
     */
    @Deprecated
    <T extends Serializable> boolean storeUserMessage(String userToken, T userMessage);


    /**
     * 得到登录的用户信息
     *
     * @param userToken token
     * @param clazz     用户信息类型
     * @param <T>       包装类
     * @return T
     */
    @Deprecated
    <T extends Serializable> T getUserMessageByUserToken(String userToken, Class<T> clazz);

    /**
     * 存储登录的用户
     *
     * @param userToken   token
     * @param userMessage 用户信息
     * @param <T>         包装类
     * @return true or false
     */
    <T extends Serializable> boolean storeUserMessageV2(String userToken, LoginUserMsg<T> userMessage);


    /**
     * 得到登录的用户信息
     *
     * @param userToken token
     * @param <T>       包装类
     * @param clazz     类型
     * @return LoginUserMsg 返回用户的登录信息
     */
    <T extends Serializable> LoginUserMsg<T> getUserMessageByUserTokenV2(String userToken, Class<T> clazz);

    /**
     * 得到登录的用户信息
     *
     * @param userToken token
     * @return 返回用户信息
     */
    @Deprecated
    Object getUserMessageByUserToken(String userToken);

}
