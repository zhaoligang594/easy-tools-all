package vip.breakpoint.convertor.base;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public interface TypeConvertor<T, R> {

    /**
     * 转换对象
     *
     * @param t 对象
     * @return 转换对象
     * @throws Exception 抛出异常
     */
    R doConvert(T t) throws Exception;
}
