package com.kite.modules.file.enums;

/**
 * @author yyw
 * @Description: 文件信息枚举
 * @date 2018/8/2111:52
 */
public class FileInfoEnum {

    /**
    　　* @Description:  公开/一级部门/二级部门/个别人可见/私密
    　　* @author yyw
    　　* @date 2018/8/21 11:54
    */
    public enum FileLevel {

        OPEN("公开","0"),
        L1_DEPT("一级部门","1"),
        L2_DEPT("二级部门","2"),
        SOME("个别人可见","3"),
        PRIVATE("私密","4");

        private String name ;
        private String  value ;

        FileLevel(String name, String value){
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

}
