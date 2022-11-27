package vip.breakpoint.config;

import vip.breakpoint.kaptcha.EasyKaptchaService;
import vip.breakpoint.supplier.value.StringValueSupplier;

/**
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public enum StringConfigEnum implements StringValueSupplier {
    /**
     * error page
     */
    SERVER_ERROR_PATH("server.error.path", "", "error page") {
        @Override
        public boolean isStatic() {
            return true;
        }
    },
    ERROR_PATH("error.path", "/error", "error page") {
        @Override
        public boolean isStatic() {
            return true;
        }
    },
    VERIFY_CODE_CONTENT("verify.code.content",
            EasyKaptchaService.VERIFY_CODES, "验证码内容") {
        @Override
        public boolean isStatic() {
            return true;
        }
    },
    ;

    private final String valueKey;
    private final String defaultValue;
    private final String desc;

    StringConfigEnum(String valueKey, String defaultValue, String desc) {
        this.valueKey = valueKey;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    @Override
    public String valueKey() {
        return this.valueKey;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }
}
