package vip.breakpoint.demo.bean;

/**
 * @author : breakpoint/赵先生 <zlgtop@163.com>
 * create on 2022/10/24
 * 欢迎关注公众号:代码废柴
 */
public class TestUser {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
