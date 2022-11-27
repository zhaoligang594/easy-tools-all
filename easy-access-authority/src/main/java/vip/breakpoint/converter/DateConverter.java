package vip.breakpoint.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.utils.EasyDateUtils;

import java.util.Date;

/**
 * 日期的转换 将前端转过来的日期字符串转换成日期的操作
 * 不然就得用 @DateTimeFormat(pattern = "yyyy-MM-dd") 在参数上
 *
 * @author breakpoint/zlgtop@163.com
 * create on 2022/11/07
 * @see DateTimeFormat
 */
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(@NonNull String source) {
        if ("".equals(source)) {
            return null;
        }
        try {
            return EasyDateUtils.parseDateStr(source);
        } catch (EasyToolException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
