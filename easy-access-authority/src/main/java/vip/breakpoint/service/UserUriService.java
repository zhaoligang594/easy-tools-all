package vip.breakpoint.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import vip.breakpoint.enums.UserRoleEnum;

/**
 * 用户的URI权限服务
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/02
 * 欢迎关注公众号:代码废柴
 */
public interface UserUriService {

    /**
     * 根据角色判断字段是否可以访问
     *
     * @param uri      资源路径
     * @param roleEnum 用户角色
     * @return 是否拥有访问的权限
     */
    boolean checkAuthorityByRole(@NonNull String uri, @NonNull UserRoleEnum roleEnum);

    /**
     * 根据角色判断字段是否可以访问
     *
     * @param uri    资源路径
     * @param userId 用户标记ID
     * @return 是否拥有访问的权限
     */
    boolean checkAuthorityByUserId(@NonNull String uri, @Nullable Long userId);
}
