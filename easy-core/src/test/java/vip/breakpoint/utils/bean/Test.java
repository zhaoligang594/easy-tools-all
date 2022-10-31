package vip.breakpoint.utils.bean;

import vip.breakpoint.annotation.MaskField;
import vip.breakpoint.enums.MaskTypeEnum;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/31
 * 欢迎关注公众号:代码废柴
 */
public class Test {
    @MaskField(MaskTypeEnum.PHONE_NUMBER)
    private String phone;
    @MaskField(MaskTypeEnum.EMAIL)
    private String email;
    @MaskField(MaskTypeEnum.ID_CARD)
    private String id;

    public Test(String phone, String email, String id) {
        this.phone = phone;
        this.email = email;
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Test{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
