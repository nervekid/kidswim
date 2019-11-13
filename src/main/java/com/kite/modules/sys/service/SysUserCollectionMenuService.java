/**
 * MouTai
 */
package com.kite.modules.sys.service;

import java.util.List;

import com.kite.modules.sys.entity.Menu;
import com.kite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.kite.modules.sys.entity.SysUserCollectionMenu;
import com.kite.modules.sys.dao.SysUserCollectionMenuDao;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户收藏菜单Service
 * @author cxh
 * @version 2017-12-15
 */
@Service
@Transactional(readOnly = true)
public class SysUserCollectionMenuService extends CrudService<SysUserCollectionMenuDao, SysUserCollectionMenu> {

    @Autowired
	SysUserCollectionMenuDao sysUserCollectionMenuDao;
	@Override
	public SysUserCollectionMenu get(String id) {
		return super.get(id);
	}
	@Override
	public List<SysUserCollectionMenu> findList(SysUserCollectionMenu sysUserCollectionMenu) {
		return super.findList(sysUserCollectionMenu);
	}
	@Override
	public Page<SysUserCollectionMenu> findPage(Page<SysUserCollectionMenu> page, SysUserCollectionMenu sysUserCollectionMenu) {
		return super.findPage(page, sysUserCollectionMenu);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SysUserCollectionMenu sysUserCollectionMenu) {
		super.save(sysUserCollectionMenu);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SysUserCollectionMenu sysUserCollectionMenu) {
		super.delete(sysUserCollectionMenu);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysUserCollectionMenuDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}

	/**
	 * 通过用户名以及菜单链接获取数据
	 * @param menu
	 * @return
	 */
	public SysUserCollectionMenu getDataByUserAndMenu(SysUserCollectionMenu menu){
		return dao.getDataByUserAndMenu(menu);
	}


	/**
	 * 通过用户以及菜单链接删除数据
	 * @param menu
	 * @return
	 */
	@Transactional(readOnly = false)
	public void deleteDataByUserAndMenu(SysUserCollectionMenu menu){
		 dao.deleteDataByUserAndMenu(menu);
	}


	public void initCollectionMenu(HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		if(StringUtils.isNotEmpty(menuId)){
			Menu menu = UserUtils.getMenuById(menuId);
			if(menu!=null){
				model.addAttribute("menu",menu);
				SysUserCollectionMenu  sysUserCollectionMenu = new SysUserCollectionMenu();
				sysUserCollectionMenu.setUser(UserUtils.getUser());
				String href = menu.getHref();
				if(StringUtils.isNotEmpty(href)){
					if(href.contains("?")){
						href+="&menuId="+menuId;
					}else{
						href+="?menuId="+menuId;
					}
				}
				sysUserCollectionMenu.setMenuUrl(href);
				SysUserCollectionMenu sysUserCollection = this.getDataByUserAndMenu(sysUserCollectionMenu);
				if(sysUserCollection!=null){
					model.addAttribute("checkBoxChecked",true);
				}
			}
		}
	}
	
}