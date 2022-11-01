package vip.breakpoint.cache;

/**
 * CacheException
 *
 * @author breakpoint/赵先生
 * create on 2021/02/03
 */
class CacheException extends RuntimeException {

    private static final long serialVersionUID = 31710638342818351L;

    CacheException(String message) {
        super(message);
    }
}
