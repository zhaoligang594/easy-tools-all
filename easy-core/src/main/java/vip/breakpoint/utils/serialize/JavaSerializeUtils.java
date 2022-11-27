package vip.breakpoint.utils.serialize;


import vip.breakpoint.annotation.MParam;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.io.*;

/**
 * Java版本的序列化以及反序列化
 *
 * @author : breakpoint
 * create on 2022/08/30
 * 欢迎关注公众号 《代码废柴》
 */
public class JavaSerializeUtils {

    private static final Logger log = WebLogFactory.getLogger(JavaSerializeUtils.class);

    /**
     * 序列化
     *
     * @param obj 序列化对象
     * @param <T> 类型
     * @return 返回序列化的数据
     */
    public static <T extends Serializable> byte[] serialize(@MParam("待序列化对象") T obj) {
        ObjectOutputStream outputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("序列化的数据发生错误", e);
            return null;
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    /**
     * 反序列化
     *
     * @param <T>   类型
     * @param clazz 类
     * @param data  序列化数据
     * @return 返回对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserialize(@MParam("数据") byte[] data,
                                                         @MParam("对象类别") Class<T> clazz) {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            return (T) objectInputStream.readObject();
        } catch (Exception e) {
            log.error("反序列化的数据发生错误", e);
            return null;
        } finally {
            if (null != objectInputStream) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
        }
    }
}
