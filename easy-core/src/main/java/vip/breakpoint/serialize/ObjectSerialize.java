package vip.breakpoint.serialize;

import java.io.Serializable;

/**
 * 对象序列化接口
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/08
 * 欢迎关注公众号:代码废柴
 */
public interface ObjectSerialize {

    // 序列化
    <T extends Serializable>byte[] serialize(T object);

    // 反序列化
    <T extends Serializable> T deSerialize(byte[] bytes, Class<T> clazz);
}
