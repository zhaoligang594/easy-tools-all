package vip.breakpoint.exception;

import org.springframework.beans.BeansException;

/**
 * 日志中间件的异常处理类
 *
 * @author :breakpoint/赵立刚
 */
public class MultiInterfaceBeansException extends BeansException {
    public MultiInterfaceBeansException(String msg) {
        super(msg);
    }

    public MultiInterfaceBeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
