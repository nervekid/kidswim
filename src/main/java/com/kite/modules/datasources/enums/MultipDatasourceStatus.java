package com.kite.modules.datasources.enums;

/**
 * 多数据配置状态
 * @author cxh
 * @version 2017-08-21
 */
public enum  MultipDatasourceStatus {

    STOP("停止","0"),OPEN("启用","1");

    private String name ;
    private String value ;


    private MultipDatasourceStatus( String name , String value ){
        this.name = name ;
        this.value = value ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
