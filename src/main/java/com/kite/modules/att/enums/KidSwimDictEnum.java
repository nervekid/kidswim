package com.kite.modules.att.enums;

/**
 * @author lyb
 * @Description: 泳課排班枚舉類
 * @date 2019-12-03
 */
public class KidSwimDictEnum {

	/**
	 * 星期幾字典標記
	 * @author lyb
	 *
	 */
	public enum weekenFlag {

		星期日("0"),
		星期壹("1"),
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

	/**
	 * 是否標記
	 * @author lyb
	 *
	 */
	public enum yesNo {

		否("0"),
		是("1");

        private String name;

        private yesNo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

	/**
	 * 收費標準標記
	 * @author lyb
	 *
	 */
	public enum costStandardFlag {

		按堂收費("1"),
		按月收費("2"),
		雙月收費("3");

        private String name;

        private costStandardFlag(String name) {
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
