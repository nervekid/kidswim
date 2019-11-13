package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysSequence;

/**
 * @author yyw
 * @Description: 流水号表
 * @date 2018/6/2811:24
 */
@MyBatisDao
public interface SysSequenceDao extends CrudDao<SysSequence>{


    public SysSequence getByName(String name);

    public void increase(String name);
    @Override
    public int update(SysSequence sysSequence);

}
