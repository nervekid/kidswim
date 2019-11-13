/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 字典DAO接口
 * @author kite
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {
	public String findValueByTypeAndLabel(@Param("type") String type, @Param("label") String label);

	public List<String> findTypeList(Dict dict);

	public List<Dict> findDictListByType(@Param("name") String name, @Param("flag") String flag);

	public List<String> findDictLabelListByType(@Param("name") String name, @Param("flag") String flag);

	public String findCountByTypeAndLabelAndFlag(@Param("type") String type, @Param("label") String label, @Param("flag") String flag);

	public String findStringByTypeAndFlag(@Param("type") String name, @Param("flag") String flag);

	List<Dict> findListByTypeArr(@Param("dictType") String[] dictType);

	public Map<String, String> getDictByTypeAndValue(@Param("type") String type, @Param("value") String value);

    void updateStatus(Dict dict);
}
