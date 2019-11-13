package com.kite.modules.sys.enums;

/**
 * @author lyb
 * @Description: 系统枚举类
 * @date 2019-9-3
 */
public class SystemEnum {

	public enum OrganizationName {

		行政线("1"),
		提成线("3"),
		审批线("4");

        private String name;

        private OrganizationName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
