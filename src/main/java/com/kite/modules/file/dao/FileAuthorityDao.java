/**
 * KITE
 */
package com.kite.modules.file.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.file.entity.FileAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件权限DAO接口
 * @author yyw
 * @version 2018-08-29
 */
@MyBatisDao
public interface FileAuthorityDao extends CrudDao<FileAuthority> {


    List<FileAuthority> findFileList(FileAuthority fileAuthority);

    FileAuthority getByFile(@Param(value="fileId")String fileId);

    FileAuthority getByFileId(@Param(value="fileId")String fileId);

    void updataUser(@Param(value="id")String id, @Param(value="userId")String insertId);

    void insertList(@Param(value="list") List<FileAuthority> insertFaList);

    void deleteLogicList(@Param(value="list")List<String> list);

    void renewList(@Param(value="list")List<String> list);

    void deleteList(@Param(value="list")List<String> list);

    void addRule(FileAuthority fileAuthority);
}