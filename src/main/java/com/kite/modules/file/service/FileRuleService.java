/**
 * KITE
 */
package com.kite.modules.file.service;

import java.util.*;

import com.kite.common.utils.ListUtils;
import com.kite.modules.file.entity.FileAuthority;
import com.kite.modules.sys.dao.OfficeDao;
import com.kite.modules.sys.dao.UserDao;
import com.kite.modules.sys.entity.Office;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.kite.modules.file.entity.FileRule;
import com.kite.modules.file.dao.FileRuleDao;

/**
 * 文件权限规则Service
 * @author yyw
 * @version 2018-09-20
 */
@Service
@Transactional(readOnly = true)
public class FileRuleService extends CrudService<FileRuleDao, FileRule> {

    @Autowired
	FileRuleDao fileRuleDao;
    @Autowired
	OfficeDao officeDao;
	@Autowired
	UserDao userDao;
	@Override
	public FileRule get(String id) {
		FileRule fileRule = super.get(id);

		List<FileRule> list = new ArrayList<>();
		list.add(fileRule);
		makeDeptAndUser(list);
		return fileRule;
	}
	@Override
	public List<FileRule> findList(FileRule fileRule) {
		List<FileRule> list = super.findList(fileRule);
		makeDeptAndUser(list);
		return list;
	}
	@Override
	public Page<FileRule> findPage(Page<FileRule> page, FileRule fileRule) {
		fileRule.setPage(page);
		List<FileRule> list = findList(fileRule);
		page.setList(list);
		return page;
	}
	@Override
	@Transactional(readOnly = false)
	public void save(FileRule fileRule) {
		if (fileRule.getIsNewRecord()){
			fileRule.preInsert();
			dao.insert(fileRule);
		}else{
			fileRule.preUpdate();
			dao.update(fileRule);
		}
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(FileRule fileRule) {
		super.delete(fileRule);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(fileRuleDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}




	private void makeDeptAndUser(List<FileRule> list) {
		if(ListUtils.isNull(list)) {
			return;
		}

		Set<String> setDept = new HashSet<String>();
		Set<String> setUser = new HashSet<String>();

		//获取部门、人员的集合
		for (FileRule fileRule : list) {

			if(fileRule == null) {
				continue;
			}

			String deptStr = fileRule.getDeptId();
			if(StringUtils.isNotEmpty(deptStr)) {
				String[] deptArr = deptStr.split(",");
				setDept.addAll(Arrays.asList(deptArr));
			}

			String userStr = fileRule.getUserId();
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
		for (FileRule fileRule : list) {

			if(fileRule == null) {
				continue;
			}

			StringBuffer listDeptName = new StringBuffer();
			if(mapOffice != null && !mapOffice.isEmpty()) {
				String dept = StringUtils.isEmpty(fileRule.getDeptId())?"":fileRule.getDeptId();
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
				fileRule.setDeptName(deptSb.substring(0,deptSb.length() - 1));
			}


			StringBuffer listUserName = new StringBuffer();
			if(mapUser != null && !mapUser.isEmpty()) {
				String use= StringUtils.isEmpty(fileRule.getUserId())?"":fileRule.getUserId();
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
				fileRule.setUserName(userSb.substring(0,userSb.length() - 1));
			}


		}

	}


	public String getUserValue(FileRule fileRule) {

		StringBuffer result = new StringBuffer();

		String id = fileRule.getId();

		FileRule rule = super.get(id);

		String userStr = rule == null?"":rule.getUserId();

		if(StringUtils.isEmpty(userStr)) {
			return result.toString();
		}

		Set<String> setUser = new HashSet<String>();
		String[] userArr = userStr.split(",");
		setUser.addAll(Arrays.asList(userArr));

		//获取人员的实体数据
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

    @Transactional(readOnly = false)
    public void updataUser(String id, String insertId) {
        fileRuleDao.updataUser(id,insertId);
    }

    @Transactional(readOnly = false)
    public String updataUserInfo(FileRule fileRule) {

        String userArr = fileRule.getIdsArr();
        String[] arr = userArr.split(",");

        String existId = "";
        //原先数据
        FileRule rule = get(fileRule.getId());

        //若为空，采用ruleId则新增一条数据
        if(rule == null) {
            Date d = new Date();
            User user = UserUtils.getUser();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(user.getId())){
                fileRule.setUpdateBy(user);
                fileRule.setCreateBy(user);
            }
            fileRule.setUpdateDate(d);
            fileRule.setCreateDate(d);
            fileRule.setId(fileRule.getRuleId());
            dao.insert(fileRule);
        }else {
			existId = rule.getUserId();
		}


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
                return fileRule.getId();
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

        //整合原先数据，并且修改到对应的权限实体
        String insertId = "";
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(existId)) {
            userId.append(existId);
            insertId = userId.toString();
        }else {
            //否则去除末位的逗号
            insertId = userId.substring(0,userId.length()-1);
        }

        updataUser(fileRule.getId(),insertId);
        return fileRule.getId();
    }


	@Transactional(readOnly = false)
	public void deleteList(List<String> list) {
		if(ListUtils.isNull(list)) {
			return;
		}

		fileRuleDao.deleteList(list);
	}
}