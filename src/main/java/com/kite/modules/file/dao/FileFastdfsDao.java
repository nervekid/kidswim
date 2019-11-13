/**
 * KITE
 */
package com.kite.modules.file.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.file.entity.FileAuthority;
import com.kite.modules.file.entity.FileFastdfs;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * fastdfs文件管理DAO接口
 * @author yyw
 * @version 2018-08-17
 */
@MyBatisDao
public interface FileFastdfsDao extends CrudDao<FileFastdfs> {


    /**
    　　* @Description: 批量新增
    　　* @author yyw
    　　* @date 2018/8/21 13:42
    */
    void insertList(List<FileFastdfs> insertList);

    void deleteList(@Param(value="list") List<String> list, @Param(value="date") Date date, @Param(value="userId") String userId);

    void deleteRecyclyList(List<String> list);

    void renew(List<String> listId);

    void updataCatalog(@Param(value="listId")List<String> listId, @Param(value="catalogId")String catalogId);

    void updateLevel(@Param(value="listId")List<String> listFileId,@Param(value="level") String level);

    List<FileFastdfs> checkAuthority(FileFastdfs select);

    List<FileFastdfs> findRecyclePage(FileFastdfs fileFastdfs);

    /**
     * 根据Id查找url
     * @param id
     * @return
     */
    String findUrlById(@Param(value="id")String id);
}