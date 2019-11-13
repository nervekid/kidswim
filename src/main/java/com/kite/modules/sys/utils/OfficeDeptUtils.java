package com.kite.modules.sys.utils;

import com.kite.common.utils.SpringContextHolder;
import com.kite.modules.sys.dao.SysOrganizationalDao;
import com.kite.modules.sys.entity.SysOrganizational;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OfficeDeptUtils {
    private static SysOrganizationalDao sysOrganizationalDao = SpringContextHolder.getBean(SysOrganizationalDao.class);


    public static Map<String ,String>  officeOneIdAndName(){
        Map<String,String>   idAndName = new LinkedHashMap<>();
        try {
            List<SysOrganizational>   list = sysOrganizationalDao.findListOfficeOneIdAndName();
            for (SysOrganizational o : list) {
                if(o.getCompanyName()==null  ||  o.getOfficeOneId()==null){
                    continue;
                }
                idAndName.put(o.getOfficeOneId(),o.getCompanyName());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return idAndName;
    }



}
