package vip.breakpoint.convertor;

import vip.breakpoint.convertor.base.TypeConvertor;

/**
 * Integer 转换器
 *
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public class IntTypeConvertor implements TypeConvertor<String, Integer> {

    @Override
    public Integer doConvert(String s) throws Exception {
        return Integer.valueOf(s);
    }
}
