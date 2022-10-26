package vip.breakpoint.convertor;

import vip.breakpoint.convertor.base.TypeConvertor;

/**
 * Boolean 转换器
 *
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public class BooleanTypeConvertor implements TypeConvertor<String, Boolean> {

    @Override
    public Boolean doConvert(String s) throws Exception {
        return Boolean.valueOf(s);
    }
}
