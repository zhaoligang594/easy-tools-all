package vip.breakpoint.service;

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
     * @throws EasyToolException 抛出的异常信息
     */
    <T extends Serializable> boolean storeUserMessage(String userToken, T userMessage) throws EasyToolException;


    /**
     * 得到登录的用户信息
     *
     * @param userToken token
     * @param clazz     用户信息类型
     * @param <T>       包装类
     * @return T
     * @throws EasyToolException 异常信息
     */
    <T extends Serializable> T getUserMessageByUserToken(String userToken, Class<T> clazz) throws EasyToolException;

    /**
     * 得到登录的用户信息
     *
     * @param userToken token
     * @return
     * @throws EasyToolException
     */
    Object getUserMessageByUserToken(String userToken) throws EasyToolException;

}
