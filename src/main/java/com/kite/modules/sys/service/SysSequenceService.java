package com.kite.modules.sys.service;

import com.kite.common.service.CrudService;
import com.kite.common.utils.DateUtlis;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.dao.SysSequenceDao;
import com.kite.modules.sys.entity.SysSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author yyw
 * @Description: 流水号Service
 * @date 2018/6/2811:26
 */
@Service
@Transactional(readOnly = true)
public class SysSequenceService extends CrudService<SysSequenceDao, SysSequence> {

    @Autowired
    private SysSequenceDao sysSequenceDao;

    /**
     * 　　* @Description: 根据名称获取流水信息
     * 　　* @author yyw
     * 　　* @date 2018/6/28 11:28
     */
    public SysSequence getByName(String name) throws Exception {
        if (StringUtils.isEmpty(name)) {
            throw new Exception("根据名称获取流水信息，传入的名称是空");
        }
        return sysSequenceDao.getByName(name);
    }

    /**
     * 　　* @Description: 自增加
     * 　　* @author yyw
     * 　　* @date 2018/6/28 11:28
     */
    @Transactional(readOnly = false)
    public void increase(String name) throws Exception {

        if (StringUtils.isEmpty(name)) {
            throw new Exception("根据名称修改流水信息，传入的名称是空");
        }

        sysSequenceDao.increase(name);
    }

    /**
     * 　　* @Description: 修改
     * 　　* @author yyw
     * 　　* @date 2018/6/28 11:36
     */
    @Transactional(readOnly = false)
    public void update(SysSequence sysSequence) throws Exception {
        sysSequenceDao.update(sysSequence);
    }

    /**
     * 　　* @Description: 获取流水
     * 　　* @author yyw
     *
     * @date 2018/6/28 11:34
     */
    @Transactional(readOnly = false)
    public SysSequence getWaterNumber(String name) throws Exception {

        if (StringUtils.isEmpty(name)) {
            throw new Exception("根据名称获取流水信息，传入的名称是空");
        }

        SysSequence sysSequence = sysSequenceDao.getByName(name);
        if (sysSequence == null) {
            return null;
        }
        String date = DateUtlis.getDateFormat(new Date(), "yyyyMM");
        if (!StringUtils.equals(sysSequence.getCurrentDate(), DateUtlis.getDateFormat(new Date(), "yyyyMM"))) {
            SysSequence updateSequence = new SysSequence();
            updateSequence.setCurrentDate(date);
            updateSequence.setCurrentVal(sysSequence.getIncreaseVal());
            updateSequence.setIncreaseVal(sysSequence.getIncreaseVal());
            updateSequence.setSeqName(sysSequence.getSeqName());
            sysSequenceDao.update(updateSequence);
        } else {
            sysSequenceDao.increase(name);
        }

        return sysSequenceDao.getByName(name);
    }

    /**
     * 　　* @Description: 获取编码
     * 　　* @author yyw
     * 　　* @date 2018/6/28 13:56
     */
    @Transactional(readOnly = false)
    public String getNumber(String name, Integer size) throws Exception {

        //获取流水
        SysSequence sysSequence = getWaterNumber(name);

        //获取流水格式
        StringBuffer patter = new StringBuffer();
        for (int i = 0; i < size; i++) {
            patter.append(0);
        }

        //流水格式化
        DecimalFormat df = new DecimalFormat(patter.toString());

        String waterNumber = df.format(sysSequence == null ? "" : sysSequence.getCurrentVal());
        StringBuffer sb = new StringBuffer();
        sb.append(name).append("-").append(DateUtlis.getDateFormat(new Date(), "yyyyMM")).append("-").append(waterNumber);

        return sb.toString();
    }

}
