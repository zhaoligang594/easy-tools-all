package vip.breakpoint.convertor;

import vip.breakpoint.convertor.base.TypeConvertor;

/**
 * String 转换器
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public class StringTypeConvertor implements TypeConvertor<String, String> {

    @Override
    public String doConvert(String s) {
        return s;
    }
}
