package vip.breakpoint.exception;

/**
 * @author : breakpoint
 * create on 2022/08/30
 * 欢迎关注公众号 《代码废柴》
 */
public class EasyToolException extends Exception {

    public EasyToolException(String message) {
        super(message);
    }

    public EasyToolException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyToolException(Throwable cause) {
        super(cause);
    }
}
