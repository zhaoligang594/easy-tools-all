package vip.breakpoint.handler;

/**
 * 表明这个对象是代理对象，此时需要获取原来的对象
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/29
 * 欢迎关注公众号:代码废柴
 */
public interface EasyLogInterceptor {
    /**
     * 获取真正的bean
     *
     * @return bean
     */
    Object getRealBean();
}
