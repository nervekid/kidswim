package com.kite.modules.sys.entity;

/**
 * Created by chx on 2018/9/30.
 */

public class FileUploadEntity {

    private String name;
    private String ext;
    private String length;
    private String file_id;
    private String list_file_url;

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

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getList_file_url() {
        return list_file_url;
    }

    public void setList_file_url(String list_file_url) {
        this.list_file_url = list_file_url;
    }
}