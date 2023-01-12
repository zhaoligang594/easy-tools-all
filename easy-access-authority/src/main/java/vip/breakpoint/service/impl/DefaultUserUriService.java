package vip.breakpoint.service.impl;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import vip.breakpoint.enums.UserRoleEnum;
import vip.breakpoint.service.UserUriService;

/**
 * 实例模板 供开发人员使用
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/02
 * 欢迎关注公众号:代码废柴
 */
public class DefaultUserUriService implements UserUriService {

    /*
            1. 加上缓存
            2. 红黑树 hashMap
     */

    @Override
    public boolean checkAuthorityByRole(@NonNull String uri, @NonNull UserRoleEnum roleEnum) {
        // 自己实现具体的细节
        return true;
    }

    @Override
    public boolean checkAuthorityByUserId(@NonNull String uri, @Nullable Long userId) {
        if (null == userId) {
            return false;
        }
        // 自己实现具体的细节
        return true;
    }

}
