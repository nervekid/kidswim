package com.kite.modules.file.entity;

import java.util.List;

/**
 * @author yyw
 * @Description: TODO
 * @date 2018/8/2111:19
 */
public class FileInfo {

    //文件名称
    private String name;

    //文件后缀名
    private String ext;

    //文件大小
    private String length;

    //文件id
    private String file_id;

    //文件所属人员
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private List<String> list_file_url;

    public List<String> getList_file_url() {
        return list_file_url;
    }

    public void setList_file_url(List<String> list_file_url) {
        this.list_file_url = list_file_url;
    }

    public FileInfo() {
    }

    public FileInfo(String name, String ext, String length,String file_id) {
        this.name = name;
        this.ext = ext;
        this.length = length;
        this.file_id = file_id;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;

    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

}
