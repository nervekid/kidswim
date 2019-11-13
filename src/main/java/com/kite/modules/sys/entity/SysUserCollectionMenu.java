/**
 * MouTai
 */
package com.kite.modules.sys.entity;

import com.kite.modules.sys.entity.User;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 用户收藏菜单Entity
 * @author cxh
 * @version 2017-12-15
 */
public class SysUserCollectionMenu extends DataEntity<SysUserCollectionMenu> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// user_id
	private String menuUrl;		// menu_url
	private String flag;//区别收藏还是取消收藏标识
	private String title;//菜单名称

	public SysUserCollectionMenu() {
		super();
	}

	public SysUserCollectionMenu(String id){
		super(id);
	}
	public SysUserCollectionMenu(User user){
		this.user=user;
	}

	@ExcelField(title="user_id", fieldType=User.class, value="user.name", align=2, sort=1)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ExcelField(title="menu_url", align=2, sort=2)
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}