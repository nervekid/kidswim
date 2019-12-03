package com.kite.modules.att.enums;

/**
 * @author lyb
 * @Description: 泳课排班枚举类
 * @date 2019-12-03
 */
public class KidSwimDictEnum {

	/**
	 * 星期几字典标记
	 * @author lyb
	 *
	 */
	public enum weekenFlag {

		星期日("0"),
		星期一("1"),
		星期二("2"),
		星期三("3"),
		星期四("4"),
		星期五("5"),
		星期六("6");

        private String name;

        private weekenFlag(String name) {
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
