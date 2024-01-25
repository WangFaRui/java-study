package com.itwray.study.advance.emuns;

/**
 * 类型枚举
 * <p>枚举的变量值也是可以更改的！！！如果想用枚举作为字典常量，尽量将变量设置为private final，同时不提供set方法</p>
 *
 * @author wangfarui
 * @since 2024/1/25
 */
public enum TypeEnum {

    A(1, "a"),
    B(2, "b");

    private Integer code;

    private String name;

    TypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
