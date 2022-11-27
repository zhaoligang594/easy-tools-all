package vip.breakpoint.utils.serialize;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import vip.breakpoint.annotation.MParam;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列化的工具
 *
 * @author : breakpoint
 * create on 2022/08/30
 * 欢迎关注公众号 《代码废柴》
 */
public abstract class ProtostuffSerializeUtils {

    // 缓存Schema
    private static final Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();

    // 序列化数据
    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(@MParam("待序列化对象") T obj) {
        // 避免每次序列化都重新申请Buffer空间
        // 1M 的空间进行对象进行序列化
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        // 获取类信息
        Class<T> clazz = (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(clazz);
        byte[] data;
        try {
            data = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } finally {
            // 释放资源
            buffer.clear();
            buffer = null;
        }
        return data;
    }

    // 反序列化
    public static <T> T deserialize(@MParam("数据") byte[] data,
                                    @MParam("对象类别") Class<T> clazz) {
        Schema<T> schema = getSchema(clazz);
        // 新创建返回的对象
        T obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, obj, schema);
        return obj;
    }

    @SuppressWarnings("unchecked")
    private static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = (Schema<T>) schemaCache.get(clazz);
        if (null == schema) {
            synchronized (ProtostuffSerializeUtils.class) {
                schema = (Schema<T>) schemaCache.get(clazz);
                if (null == schema) {
                    //这个schema通过RuntimeSchema进行懒创建并缓存
                    //所以可以一直调用RuntimeSchema.getSchema(),这个方法是线程安全的
                    schema = RuntimeSchema.getSchema(clazz);
                    if (Objects.nonNull(schema)) {
                        schemaCache.put(clazz, schema);
                    }
                }
            }
        }
        return schema;
    }

}
