package vip.breakpoint.serialize.impl;

import vip.breakpoint.serialize.ObjectSerialize;
import vip.breakpoint.utils.serialize.JavaSerializeUtils;

import java.io.Serializable;

/**
 * java 类型的转换
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/08
 * 欢迎关注公众号:代码废柴
 */
public class JavaObjectSerialize implements ObjectSerialize {

    @Override
    public <T extends Serializable> byte[] serialize(T object) {
        return JavaSerializeUtils.serialize(object);
    }

    @Override
    public <T extends Serializable> T deSerialize(byte[] bytes, Class<T> clazz) {
        return JavaSerializeUtils.deserialize(bytes, clazz);
    }
}
