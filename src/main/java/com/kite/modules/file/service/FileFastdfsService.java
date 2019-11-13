/**
 * KITE
 */
package com.kite.modules.file.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.DateUtlis;
import com.kite.common.utils.ListUtils;
import com.kite.common.utils.StringUtils;
import com.kite.modules.file.dao.FileAuthorityDao;
import com.kite.modules.file.dao.FileFastdfsDao;
import com.kite.modules.file.entity.FileAuthority;
import com.kite.modules.file.entity.FileFastdfs;
import com.kite.modules.file.entity.FileInfo;
import com.kite.modules.file.enums.FileInfoEnum;
import com.kite.modules.sys.entity.Role;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.utils.UserUtils;

/**
 * fastdfs文件管理Service
 * @author yyw
 * @version 2018-08-17
 */
@Service
@Transactional(readOnly = true)
public class FileFastdfsService extends CrudService<FileFastdfsDao, FileFastdfs> {

    @Autowired
	private FileFastdfsDao fileFastdfsDao;
    @Autowired
	private FileAuthorityDao fileAuthorityDao;

    public String findUrlById(String id) {
    	return this.fileFastdfsDao.findUrlById(id);
    }

	@Override
	public FileFastdfs get(String id) {
		return super.get(id);
	}

	@Override
	public List<FileFastdfs> findList(FileFastdfs fileFastdfs) {
		return super.findList(fileFastdfs);
	}

	@Override
	public Page<FileFastdfs> findPage(Page<FileFastdfs> page, FileFastdfs fileFastdfs) {

		try {
			fileFastdfs.setPage(page);

			//设置时间
			String selectDateStr = fileFastdfs.getSelectDateStr();
			if(StringUtils.isNotEmpty(selectDateStr)){
				String[] arr = selectDateStr.split(" - ");
				fileFastdfs.setBeginDate(DateUtlis.formatDate(arr[0],"yyyy-MM-dd"));
				fileFastdfs.setEndDate(DateUtlis.formatDate(arr[1],"yyyy-MM-dd"));
			}


			User user = UserUtils.getUser();
			if(user == null) {
				return page;
			}

			String id = user.getId();
			fileFastdfs.setCurrUserId(id);
			String deptId = user.getOffice() == null?"":user.getOffice().getId();
			fileFastdfs.setCurrDeptId(deptId);
			String parentDeptId = user.getCompany() == null?"":user.getCompany().getId();
			fileFastdfs.setCurrParentDeptId(parentDeptId);

			List<Role> listRole = user.getRoleList();
			if(ListUtils.isNotNull(listRole)) {
				for (Role role : listRole) {
					if(role == null) {
						continue;
					}
					if(org.apache.commons.lang3.StringUtils.equals("file_admin",role.getEnname())) {
						fileFastdfs.setIfAdmin("Y");
					}
				}
			}

			page.setList(dao.findList(fileFastdfs));
		} catch (Exception e) {
			e.printStackTrace();
		}


		return page;
	}
	@Override
	@Transactional(readOnly = false)
	public void save(FileFastdfs fileFastdfs) {
		super.save(fileFastdfs);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(FileFastdfs fileFastdfs) {
		super.delete(fileFastdfs);
	}

	@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();

		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(fileFastdfsDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}

	/**
	　　* @Description: 保存
	　　* @author yyw
	　　* @date 2018/8/21 11:48
	*/
	@Transactional(readOnly = false)
	public List<FileFastdfs> insert(FileFastdfs fileFastdfs) throws RuntimeException{

		List<FileInfo> listFileInfo = fileFastdfs.getListFileInfo();
		String group = fileFastdfs.getGroup();
		String catalogId = fileFastdfs.getCatalogId();

		List<FileFastdfs> insertList = new ArrayList<FileFastdfs>();
		List<FileAuthority> insertFaList = new ArrayList<FileAuthority>();
		for (FileInfo fileInfo : listFileInfo) {

			//插入文件信息
			FileFastdfs ff = new FileFastdfs();
			String id = ff.preInsert4return();
			ff.setGroup(group);
			ff.setLevel(FileInfoEnum.FileLevel.OPEN.getValue());	//默认私密
			ff.setName(fileInfo.getName());
			ff.setSize(fileInfo.getLength());
			ff.setType(fileInfo.getExt());
			ff.setUrl(fileInfo.getFile_id());
			ff.setCatalogId(catalogId);
			ff.setUserId(fileFastdfs.getUserId());
			insertList.add(ff);

			//插入如文件权限
			FileAuthority fa = new FileAuthority();
			fa.setFileId(id);
			fa.preInsert();
			insertFaList.add(fa);
		}

		fileFastdfsDao.insertList(insertList);
		fileAuthorityDao.insertList(insertFaList);
		return insertList;
	}

	/**
	　　* @Description: 批量删除
	　　* @author yyw
	　　* @date 2018/8/21 15:33
	*/
    @Transactional(readOnly = false)
    public void deleteList(List<String> list,Date date,String userId) {

		fileFastdfsDao.deleteList(list,date, userId);
		fileAuthorityDao.deleteLogicList(list);
    }

    /**
    　　* @Description: 批量逻辑删除
    　　* @author yyw
    　　* @date 2018/9/12 14:38
    */
	@Transactional(readOnly = false)
	public void deleteRecyclyList(List<String> list,Date date,String userId) {
		fileFastdfsDao.deleteRecyclyList(list);
		fileAuthorityDao.deleteList(list);
	}

	@Transactional(readOnly = false)
    public void renew(List<String> listId) {
		fileFastdfsDao.renew(listId);
		fileAuthorityDao.renewList(listId);
    }

	@Transactional(readOnly = false)
    public void updataCatalog(List<String> listId ,String catalogId) {

    	if(ListUtils.isNull(listId) || org.apache.commons.lang3.StringUtils.isEmpty(catalogId)) {
    		return;
		}

		fileFastdfsDao.updataCatalog(listId,catalogId);
    }

	public boolean checkAuthority(List<String> listId) {

    	boolean flag = false;

		User user = UserUtils.getUser();

		if(user == null) {
			return flag;
		}

		FileFastdfs select = new FileFastdfs();
		select.setListId(listId);
		select.setCurrUserId(user.getId());


		List<Role> listRole = user.getRoleList();
		if(ListUtils.isNotNull(listRole)) {
			for (Role role : listRole) {
				if(role == null) {
					continue;
				}
				if(org.apache.commons.lang3.StringUtils.equals("file_admin",role.getEnname())) {
					select.setIfAdmin("Y");
				}
			}
		}

		List<FileFastdfs> list = fileFastdfsDao.checkAuthority(select);
		if(ListUtils.isNotNull(list) && Integer.compare(list.size(),listId.size())== 0) {
			flag = true;
		}else {
			flag = false;
		}

		return flag;
	}

	/**
	　　* @Description: 回收站列表
	　　* @author yyw
	　　* @date 2018/9/4 15:45
	*/
	public Page<FileFastdfs> findRecyclePage(Page<FileFastdfs> page, FileFastdfs fileFastdfs) {

		try {


			fileFastdfs.setPage(page);

			//设置时间
			String selectDateStr = fileFastdfs.getSelectDateStr();
			if(StringUtils.isNotEmpty(selectDateStr)){
				String[] arr = selectDateStr.split(" - ");
				fileFastdfs.setBeginDate(DateUtlis.formatDate(arr[0],"yyyy-MM-dd"));
				fileFastdfs.setEndDate(DateUtlis.formatDate(arr[1],"yyyy-MM-dd"));
			}


			User user = UserUtils.getUser();
			if(user == null) {
				return page;
			}

			String id = user.getId();
			fileFastdfs.setCurrUserId(id);

			List<Role> listRole = user.getRoleList();
			if(ListUtils.isNotNull(listRole)) {
				for (Role role : listRole) {
					if(role == null) {
						continue;
					}
					if(org.apache.commons.lang3.StringUtils.equals("file_admin",role.getEnname())) {
						fileFastdfs.setIfAdmin("Y");
					}
				}
			}

			fileFastdfs.setDelFlag("1");
			page.setList(dao.findRecyclePage(fileFastdfs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
}