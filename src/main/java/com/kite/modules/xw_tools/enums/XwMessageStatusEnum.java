package com.kite.modules.xw_tools.enums;

/**
 * Created by Administrator on 2017/12/14.
 */
public enum XwMessageStatusEnum {

    READ("已读","1"),NORREAD("未读","0");

    private String name ;
    private String  value ;
    private XwMessageStatusEnum(String name , String  value ){
        this.name = name ;
        this.value = value ;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String   getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
