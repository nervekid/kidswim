package com.kite.modules.sys.utils;

import com.google.common.collect.Lists;
import com.kite.common.utils.SpringContextHolder;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.entity.User;
import com.kite.modules.xw_tools.dao.XwPushmessageDao;
import com.kite.modules.xw_tools.dao.XwPushmessageRecordDao;
import com.kite.modules.xw_tools.entity.XwPushmessage;
import com.kite.modules.xw_tools.entity.XwPushmessageRecord;
import com.kite.modules.xw_tools.enums.XwMessageStatusEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 * 消息推送工具类
 */
public class MessageUtils {

    private static XwPushmessageDao xwPushmessageDao = SpringContextHolder.getBean(XwPushmessageDao.class);
    private static XwPushmessageRecordDao xwPushmessageRecordDao = SpringContextHolder.getBean(XwPushmessageRecordDao.class);

    /**
     * 保存即将推送的信息
     * @param dataLists 数据集合
     * @param users 用户集合
     * @return
     */
    public static boolean putDataToXwMessage(List<XwPushmessage> dataLists, List<User> users){
        boolean result = false;
        try {
            if(dataLists==null || users==null){
                return false;
            }
            XwPushmessageRecord record = null ;
            for(XwPushmessage xwPushmessage:dataLists){
                xwPushmessage =  initXwPushmessage(xwPushmessage);
                xwPushmessageDao.insert(xwPushmessage);
                for(User u:users){
                    record = initXwPushmessageRecord(xwPushmessage,u);
                    xwPushmessageRecordDao.insert(record);
                }
            }
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 保存即将推送的信息
     * @param xwPushmessage 数据集合
     * @param user 用户集合
     * @return
     */
    public static boolean putDataToXwMessage(XwPushmessage xwPushmessage,User user){
        boolean result = false;
        try {
            if(xwPushmessage==null || user==null){
                return false;
            }
                xwPushmessage =  initXwPushmessage(xwPushmessage);
                xwPushmessageDao.insert(xwPushmessage);
                XwPushmessageRecord  record = initXwPushmessageRecord(xwPushmessage,user);
                xwPushmessageRecordDao.insert(record);
               result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取推送信息接口
     * @param xwPushmessage ,user
     * @param user
     * @return
     */
    public static boolean readXwMessage(XwPushmessage  xwPushmessage, User user){
        boolean result = false;
        try {
            if(null == xwPushmessage || user==null){
                return false;
            }
            XwPushmessageRecord record = new XwPushmessageRecord(xwPushmessage);
            record.setUser(user);
            record.setReadDate(new Date());
            record.setReadFlag(XwMessageStatusEnum.READ.getValue());
            xwPushmessageRecordDao.update(record);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return  result;
    }


    private static  XwPushmessage initXwPushmessage(XwPushmessage xwPushmessage){
        Date newDate = new Date();
        User user = UserUtils.get("1");
        xwPushmessage.preInsert();
        xwPushmessage.setCreateBy(user);
        xwPushmessage.setCreateDate(newDate);
        xwPushmessage.setUpdateBy(user);
        xwPushmessage.setUpdateDate(newDate);
        return xwPushmessage;
    }

    private static  XwPushmessageRecord initXwPushmessageRecord(XwPushmessage xwPushmessage ,User user){
        XwPushmessageRecord  record = new XwPushmessageRecord();
        Date newDate = new Date();
        User nowUser = UserUtils.get("1");
        record.preInsert();
        record.setUser(user);
        record.setReadFlag(XwMessageStatusEnum.NORREAD.getValue());
        record.setPushmeaasgeId(xwPushmessage);
        record.setCreateBy(nowUser);
        record.setCreateDate(newDate);
        record.setUpdateBy(nowUser);
        record.setUpdateDate(newDate);
        return record;
    }


    public static void main(String[] args) {
        List<XwPushmessage> dataLists = Lists.newArrayList();
        XwPushmessage xwPushmessage = new XwPushmessage();
        xwPushmessage.setTitle("测试数据");
        xwPushmessage.setContent("就是测试数据");
        xwPushmessage.setStatus("0");
        xwPushmessage.setRemarks("123123213");
        dataLists.add(xwPushmessage);
        List<User> userList = Lists.newArrayList();
        userList.add(UserUtils.getUser());
        MessageUtils.putDataToXwMessage(dataLists,userList);

    }

}
