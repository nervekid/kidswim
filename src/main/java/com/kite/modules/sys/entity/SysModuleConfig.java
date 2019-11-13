/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 系统模块配置Entity
 * @author cxh
 * @version 2019-04-29
 */
public class SysModuleConfig extends DataEntity<SysModuleConfig> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 系统模块名称
	private String image;		// 系统图片
	private String url;		// 系统连接
	
	public SysModuleConfig() {
		super();
	}

	public SysModuleConfig(String id){
		super(id);
	}

	@ExcelField(title="系统模块名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="系统图片", align=2, sort=2)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@ExcelField(title="系统连接", align=2, sort=3)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}