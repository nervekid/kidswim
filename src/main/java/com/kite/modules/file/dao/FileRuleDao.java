/**
 * KITE
 */
package com.kite.modules.file.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.file.entity.FileRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件权限规则DAO接口
 * @author yyw
 * @version 2018-09-20
 */
@MyBatisDao
public interface FileRuleDao extends CrudDao<FileRule> {


    void updataUser(@Param(value="id")String id, @Param(value="userId")String insertId);

    void deleteList(@Param(value="list")List<String> list);
}