package vip.breakpoint.convertor;

import vip.breakpoint.convertor.base.TypeConvertor;

/**
 * Double 转换器
 *
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/26
 * 欢迎关注公众号:代码废柴
 */
public class CharacterTypeConvertor implements TypeConvertor<String, Character> {

    @Override
    public Character doConvert(String s) throws Exception {
        return (Character) s.charAt(0);
    }
}
