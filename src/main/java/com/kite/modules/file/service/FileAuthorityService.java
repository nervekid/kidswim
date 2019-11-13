/**
 * KITE
 */
package com.kite.modules.file.service;

import java.util.*;

import com.kite.common.utils.ListUtils;
import com.kite.common.utils.MapUtil;
import com.kite.modules.file.dao.FileFastdfsDao;
import com.kite.modules.file.entity.FileFastdfs;
import com.kite.modules.sys.dao.OfficeDao;
import com.kite.modules.sys.dao.UserDao;
import com.kite.modules.sys.entity.Office;
import com.kite.modules.sys.entity.Role;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.service.SystemService;
import com.kite.modules.sys.utils.UserUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.kite.modules.file.entity.FileAuthority;
import com.kite.modules.file.dao.FileAuthorityDao;

/**
 * 文件权限Service
 * @author yyw
 * @version 2018-08-29
 */
@Service
@Transactional(readOnly = true)
public class FileAuthorityService extends CrudService<FileAuthorityDao, FileAuthority> {

    @Autowired
	FileAuthorityDao fileAuthorityDao;
    @Autowired
	private FileFastdfsDao fileFastdfsDao;
    @Autowired
	private UserDao userDao;
    @Autowired
	private OfficeDao officeDao;
	@Override
	public FileAuthority get(String id) {

		FileAuthority fileAuthority = super.get(id);
		//补充部门、人员信息
        List<FileAuthority> list = new ArrayList<FileAuthority>();
        list.add(fileAuthority);
		makeDeptAndUser(list);

		return fileAuthority;
	}
	@Override
	public List<FileAuthority> findList(FileAuthority fileAuthority) {

	    List<FileAuthority> list = super.findList(fileAuthority);
        makeDeptAndUser(list);
		return list;
	}
	@Override
	public Page<FileAuthority> findPage(Page<FileAuthority> page, FileAuthority fileAuthority) {

		fileAuthority.setPage(page);



		User user = UserUtils.getUser();
		if(user == null) {
			return page;
		}

		String id = user.getId();
		fileAuthority.setCurrUserId(id);
		String deptId = user.getOffice() == null?"":user.getOffice().getId();
		fileAuthority.setCurrDeptId(deptId);
		String parentDeptId = user.getCompany() == null?"":user.getCompany().getId();
		fileAuthority.setCurrParentDeptId(parentDeptId);

		List<Role> listRole = user.getRoleList();
		if(ListUtils.isNotNull(listRole)) {
			for (Role role : listRole) {
				if(role == null) {
					continue;
				}
				if(org.apache.commons.lang3.StringUtils.equals("file_admin",role.getEnname())) {
					fileAuthority.setIfAdmin("Y");
				}
			}
		}

		List<FileAuthority> list = dao.findList(fileAuthority);
		makeDeptAndUser(list);

		page.setList(list);
		return page;

	}

	public Page<FileAuthority> findFileList(Page<FileAuthority> page, FileAuthority fileAuthority) {
		fileAuthority.setPage(page);
		List<FileAuthority> list = dao.findFileList(fileAuthority);

		//补充部门、人员信息
		makeDeptAndUser(list);

		page.setList(list);
		return page;
	}


	private void makeDeptAndUser(List<FileAuthority> list) {
		if(ListUtils.isNull(list)) {
			return;
		}

		Set<String> setDept = new HashSet<String>();
		Set<String> setUser = new HashSet<String>();

		//获取部门、人员的集合
		for (FileAuthority fileAuthority : list) {

			if(fileAuthority == null) {
				continue;
			}

			String deptStr = fileAuthority.getDeptId();
			if(StringUtils.isNotEmpty(deptStr)) {
				String[] deptArr = deptStr.split(",");
				setDept.addAll(Arrays.asList(deptArr));
			}

			String userStr = fileAuthority.getUserId();
			if(StringUtils.isNotEmpty(userStr)) {
				String[] userArr = userStr.split(",");
				setUser.addAll(Arrays.asList(userArr));
			}
		}

		//获取部门、人员的实体数据
		User user = new User();
		user.setListId(new ArrayList<>(setUser));
		List<User> listUserEntity = userDao.findList(user);
		Map<String,User> mapUser = new HashMap<String,User>();
		if(ListUtils.isNotNull(listUserEntity)) {
			for (User u : listUserEntity) {
				mapUser.put(u.getId(),u);
			}
		}

		Office office = new Office();
		office.setListId(new ArrayList<>(setDept));
		List<Office> listOfficeEntity = officeDao.findList(office);
		Map<String,Office> mapOffice = new HashMap<String,Office>();
		if(ListUtils.isNotNull(listOfficeEntity)) {
			for (Office o : listOfficeEntity) {
				mapOffice.put(o.getId(),o);
			}
		}

		//设置
		for (FileAuthority authority : list) {

			if(authority == null) {
				continue;
			}

			StringBuffer listDeptName = new StringBuffer();
			if(mapOffice != null && !mapOffice.isEmpty()) {
				String dept = StringUtils.isEmpty(authority.getDeptId())?"":authority.getDeptId();
				String[] deptArr = dept.split(",");
				if(deptArr != null &&deptArr.length > 0) {
					for (String s : deptArr) {
						Office o = mapOffice.get(s);
						if(o != null) {
							String name = o.getName();
							listDeptName.append(name).append(",");
						}
					}
				}


			}

			String deptSb = listDeptName.toString();
			if(StringUtils.isNotEmpty(deptSb)) {
				authority.setListDept(deptSb.substring(0,deptSb.length() - 1));
			}


			StringBuffer listUserName = new StringBuffer();
			if(mapUser != null && !mapUser.isEmpty()) {
				String use= StringUtils.isEmpty(authority.getUserId())?"":authority.getUserId();
				String[] userArr = use.split(",");
				if(userArr != null && userArr.length > 0) {
					for (String s : userArr) {
						User u = mapUser.get(s);
						if(u != null) {
							String name = u.getName();
							listUserName.append(name).append(",");
						}
					}
				}

			}

			String userSb = listUserName.toString();
			if(StringUtils.isNotEmpty(userSb)) {
				authority.setListUser(userSb.substring(0,userSb.length() - 1));
			}


		}
		
	}
	@Override
	@Transactional(readOnly = false)
	public void save(FileAuthority fileAuthority) {
		super.save(fileAuthority);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(FileAuthority fileAuthority) {
		super.delete(fileAuthority);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(fileAuthorityDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}

	@Transactional(readOnly = false)
    public void updataCatalog(FileAuthority fileAuthority) {
		List<String> listFileId = fileAuthority.getListFileId();
		String level = fileAuthority.getLevel();

		fileFastdfsDao.updateLevel(listFileId,level);

    }

    public FileAuthority getByFile(String fileId) {
		FileAuthority f = fileAuthorityDao.getByFile(fileId);
		List<FileAuthority> list = new ArrayList<FileAuthority>();
		list.add(f);
		makeDeptAndUser(list);
		return f;
    }

	public FileAuthority getByFileId(String fileId) {
		return fileAuthorityDao.getByFileId(fileId);
	}

	@Transactional(readOnly = false)
	public void updataUser(String id, String insertId) {
		fileAuthorityDao.updataUser(id,insertId);
	}


	public String getUserValue(FileAuthority fileAuthority) {

		StringBuffer result = new StringBuffer();

		String id = fileAuthority.getId();

		FileAuthority authority = super.get(id);

		String userStr = authority == null?"":authority.getUserId();

		if(StringUtils.isEmpty(userStr)) {
			return result.toString();
		}

		Set<String> setUser = new HashSet<String>();
		String[] userArr = userStr.split(",");
		setUser.addAll(Arrays.asList(userArr));

		//获取部门、人员的实体数据
		User user = new User();
		user.setListId(new ArrayList<>(setUser));
		List<User> listUserEntity = userDao.findList(user);

		if(ListUtils.isNull(listUserEntity)) {
			return result.toString();
		}


		for (User u : listUserEntity) {
			if(u != null) {
				String name = u.getName();
				result.append(name).append(",");
			}
		}

		String userSb = result.toString();
		return userSb.substring(0,userSb.length() - 1);

	}

	/**
	　　* @Description: 修改用户信息
	　　* @author yyw
	　　* @date 2018/9/3 14:56
	*/
    @Transactional(readOnly = false)
    public void updataUserInfo(FileAuthority fileAuthority) {

        String userArr = fileAuthority.getIdsArr();
        String[] arr = userArr.split(",");

        //原先数据
        FileAuthority f = get(fileAuthority.getId());
        String existId = f.getUserId();

        StringBuffer userId = new StringBuffer("");
        if(arr != null && arr.length > 0) {

            Set<String> set = new HashSet<>();

            //与源数据去重
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(existId)) {
                for (String s : arr) {
                    if(!existId.contains(s)) {
                        set.add(s);
                    }
                }
            }else {
                for (String s : arr) {
                    set.add(s);
                }
            }

            List<String> list = new ArrayList<>(set);
            if(ListUtils.isNull(list)) {
                return;
            }

            User user = new User();
            user.setListId(list);
            List<User> listUser = userDao.findList(user);

            //提取数据
            if(ListUtils.isNotNull(listUser)) {
                for (User u : listUser) {
                    userId.append(u.getId()).append(",");
                }
            }
        }

        System.out.println(userId.toString());

        //整合原先数据，并且修改到对应的权限实体
        String insertId = "";
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(existId)) {
            userId.append(existId);
            insertId = userId.toString();
        }else {
            //否则去除末位的逗号
            insertId = userId.substring(0,userId.length()-1);
        }

        updataUser(fileAuthority.getId(),insertId);
    }

	@Transactional(readOnly = false)
    public void addRule(FileAuthority fileAuthority) {
    	String list = fileAuthority.getListAuthorityId();
    	fileAuthority.setListId(Arrays.asList(list.split(",")));
		fileAuthorityDao.addRule(fileAuthority);
    }
}