package vip.breakpoint.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 *
 * @author :breakpoint/赵立刚
 * create on 2017/11/28
 */
public final class EasyMD5Utils {

    // refuse new object
    private EasyMD5Utils() {
    }

    private static final String ENCRYPTING_CODE = "MD5";

    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final String DEFAULT_CHARSET = "UTF-8";

    // 获取 MessageDigest
    private static MessageDigest getMessageDigest(String encryptingCode) {
        try {
            return MessageDigest.getInstance(encryptingCode);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("没有找到加密的方式 \"" + encryptingCode + "\"", e);
        }
    }

    private static byte[] digest(String encryptingCode, byte[] bytes) {
        return getMessageDigest(encryptingCode).digest(bytes);
    }


    public static byte[] md5DigestBytes(byte[] bytes) {
        return digest(ENCRYPTING_CODE, bytes);
    }

    public static byte[] md5DigestBytes(String encryptingMessage, String charset) {
        try {
            byte[] bytes = encryptingMessage.getBytes(charset);
            return md5DigestBytes(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("编码不正确 charset=" + charset, e);
        }
    }

    public static byte[] md5DigestBytes(String encryptingMessage) {
        return md5DigestBytes(encryptingMessage, DEFAULT_CHARSET);
    }

    public static String md5DigestHexString(String encryptingMessage) {
        return new String(encodeHex(md5DigestBytes(encryptingMessage)));
    }

    private static char[] encodeHex(byte[] bytes) {
        char[] chars = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return chars;
    }
}
