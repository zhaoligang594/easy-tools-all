package vip.breakpoint.convertor;

import vip.breakpoint.convertor.base.TypeConvertor;

/**
 * Short 转换器
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public class ShortTypeConvertor implements TypeConvertor<String, Short> {

    @Override
    public Short doConvert(String s) throws Exception {
        return Short.valueOf(s);
    }
}
