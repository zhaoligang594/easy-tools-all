package vip.breakpoint.convertor;

import vip.breakpoint.convertor.base.TypeConvertor;

/**
 * Long 转换器
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public class LongTypeConvertor implements TypeConvertor<String, Long> {

    @Override
    public Long doConvert(String s) throws Exception {
        return Long.valueOf(s);
    }
}
