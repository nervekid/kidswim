package com.kite.modules.sys.entity;

import com.kite.common.persistence.DataEntity;

/**
 * @author yyw
 * @Description: 流水号
 * @date 2018/6/2811:22
 */
public class SysSequence extends DataEntity<SysSequence> {

    //序列名称
    private String seqName;

    //年月日信息
    private String currentDate;

    //序列当前值
    private Integer currentVal;

    //序列增长步伐
    private Integer increaseVal;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public Integer getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(Integer currentVal) {
        this.currentVal = currentVal;
    }

    public Integer getIncreaseVal() {
        return increaseVal;
    }

    public void setIncreaseVal(Integer increaseVal) {
        this.increaseVal = increaseVal;
    }
}
