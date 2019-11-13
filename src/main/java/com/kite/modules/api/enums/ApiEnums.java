package com.kite.modules.api.enums;

/**
 * Created by chx on 2019/1/24.
 */

public enum ApiEnums {
    PARAM_EMPTY(10001,"参数不能为空"),PARAM_ERROR(10002,"配置信息出错"),SUCCESS(0,"成功"),VALID(0,"有效"), INVALID(-1,"token无效"),
    CREATE(0,"create") ,ID_NOT_EXITES(-2,"Id不存在"),UPDATE(0,"update"),PARENT_ID_NOT_EXITES(-3,"ParentID不存在"),DELETE(0,"delete"),OK(0,"ok"),ERROR_PERMISSION(-1,"无权进行访问"),ERROR_NUM_TOO_MUCH(-1,"访问次数过多")
    , OBJECT_NOT_EXITES(1003, "对象不存在");

    private final Integer key;
    private final String value;

    private ApiEnums(Integer key, String value){
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
