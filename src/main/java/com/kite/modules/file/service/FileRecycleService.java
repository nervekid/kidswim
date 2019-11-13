package com.kite.modules.file.service;
/**
 * fastdfs文件管理Service
 * @author yyw
 * @version 2018-08-17
 */
import com.kite.common.service.CrudService;
import com.kite.common.utils.ListUtils;
import com.kite.modules.file.dao.FileFastdfsDao;
import com.kite.modules.file.dao.FileRecycleDao;
import com.kite.modules.file.entity.FileFastdfs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FileRecycleService extends CrudService<FileFastdfsDao, FileFastdfs> {

    @Autowired
    private FileRecycleDao fileRecycleDao;

    @Autowired
    private FileFastdfsDao fileFastdfsDao;


}
