package vip.breakpoint.exception;

/**
 * 异常的处理
 *
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/24
 * 欢迎关注公众号:代码废柴
 */
public class OptNotSupportException extends RuntimeException {

    public OptNotSupportException(String message) {
        super(message);
    }

    public OptNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public OptNotSupportException(Throwable cause) {
        super(cause);
    }
}
