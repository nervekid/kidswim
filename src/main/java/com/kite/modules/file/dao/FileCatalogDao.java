/**
 * KITE
 */
package com.kite.modules.file.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.TreeDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.file.entity.FileCatalog;

/**
 * 文档目录DAO接口
 * @author yyw
 * @version 2018-08-22
 */
@MyBatisDao
public interface FileCatalogDao extends TreeDao<FileCatalog> {


}