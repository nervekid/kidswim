package com.kite.modules.sys.result;

import com.kite.modules.sys.entity.FileUploadEntity;

import java.io.Serializable;
import java.util.List;

/**
　　* @Description: 结果返回集
　　* @author yyw
　　* @date 2018/6/28 15:51
*/
public class WebResult implements Serializable{

    /**返回结果*/
    private List<FileUploadEntity> object;

    private Object obj;

    /**运行结果*/
    private Boolean result = true;

    /**返回提示信息*/
    private String message = "";

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public List<FileUploadEntity> getObject() {
        return object;
    }

    public void setObject(List<FileUploadEntity> object) {
        this.object = object;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
