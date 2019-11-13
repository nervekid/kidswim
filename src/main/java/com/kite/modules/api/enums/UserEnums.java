package com.kite.modules.api.enums;

/**
 * Created by chx on 2019/1/25.
 */

public enum  UserEnums {
    NAME_EMPTY(-1,"名称为空"),LOGIN_NAME_EMPTY(-1,"登录名称为空"),PASSWORD_EMPTY(-1,"密码为空"),EMAIL_EMPTY(-1,"邮箱为空"),MOBILE_EMPTY(-1,"手机号码为空")
    ,DEPARTMENT_EMPTY(-1,"部门为空"),DEPARTMENT_PARAM_ERROR(-1,"部门参数出错"),ID_ERROR(-1,"用户id出错");
    private final Integer key;
    private final String value;

    private UserEnums(Integer key, String value){
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}