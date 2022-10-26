package vip.breakpoint.convertor.base;

/**
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public interface TypeConvertor<T, R> {

    /**
     * 转换对象
     */
    R doConvert(T t) throws Exception;
}
