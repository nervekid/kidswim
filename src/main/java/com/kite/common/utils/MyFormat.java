package com.kite.common.utils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyFormat {

    /**
     * 计算年龄：出生日期至给定的截止日期，计算其年龄，不满一年的舍去，2年个月算2岁；6年11个月算6岁
     * @param startDate
     * @param endDate
     * @return
     */
    public static String  caculateAge(Date startDate , Date endDate) {
        if(startDate == null || endDate == null) {
            return "0";
        }
        int flag = 0;
        int year = 0;

        String Age = "0";
        try {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(startDate);
            end.setTime(endDate);
            if(start.equals(end)) {
                return "0";
            }
            if (start.after(end)){
                Calendar temp = start;
                start = end;
                end = temp;
            }
            if(end.get(Calendar.YEAR ) == start.get(Calendar.YEAR)){
                return "0";
            }else {
                if (end.get(Calendar.MONTH) > start.get(Calendar.MONTH)){
                    flag = 0 ;
                }else {
                    if (end.get(Calendar.DAY_OF_MONTH) >= start.get(Calendar.DAY_OF_MONTH)) {
                        flag = 0;
                    } else {
                        flag = 1;
                    }
                }
            }
            year = end.get(Calendar.YEAR ) - start.get(Calendar.YEAR) - flag ;
            Age = ""+year;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return Age ;
    }




    /**
     * 计算工龄，传入时间参数先后顺序不管，返回小数格式字符串，小数点前面为 年，后面份月份；1.5 ，则是1年 5个月，2.11 则是2年 11个月
     * @param startDate
     * @param endDate
     * @return String
     */
    public static String  caculateWorkingAge(Date startDate , Date endDate) {
        if(startDate == null || endDate == null) {
            return "0.0";
        }
        int iMonth = 0;
        int flag = 0;
        int year = 0;
        int month = 0;
        String workingAge = "";
        try {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(startDate);
            end.setTime(endDate);
            if(start.equals(end)) {
                return "0.0";
            }
            if (start.after(end)){
                Calendar temp = start;
                start = end;
                end = temp;
            }
            if (end.get(Calendar.DAY_OF_MONTH) < start.get(Calendar.DAY_OF_MONTH)) {     flag = 1;    }
            if (end.get(Calendar.YEAR) > start.get(Calendar.YEAR)) {
                iMonth = (  (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12 + end.get(Calendar.MONTH) - flag) - start.get(Calendar.MONTH);
            } else {
                iMonth = end.get(Calendar.MONTH)   - start.get(Calendar.MONTH) - flag;
            }
            //System.out.println("两个日期相差的月数 = "+ iMonth);
            year = iMonth / 12 ;
            month = iMonth % 12;
            workingAge = year+"."+month;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workingAge ;
    }


    public static void main(String[] args ){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("2018-04-16");
            String age = MyFormat.caculateAge(new Date(), date );
            System.out.println(age);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 自定义对象序列化方法
     * @param object
     * @return
     */
    public static byte[] mySerialize( Object object) {
        byte[] dataArray = null;
        try {
            //1、创建OutputStream对象
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //2、创建OutputStream的包装对象ObjectOutputStream，PS：对象将写到OutputStream流中
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            //3、将对象写到OutputStream流中
            objectOutputStream.writeObject(object);
            //4、将OutputStream流转换成字节数组
            dataArray = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataArray;
    }


    /**
     * 自定义对象反序列化方法
     * @param objectData
     * @return
     */
    public static Object myDeserialize( byte[] objectData) {
        Object object = null;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(objectData);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            object = objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return object;
    }


    /******************* 2.5 格式化数据类型方法开始：percent ***********************/
    /**
     * 两个值占比：部分数据占总数据N%
     * @param data 部分数据
     * @param total 总数据
     * @return 经规格化后的结果
     */
    public static String getPercentByIntAndInt(int data, int total) {
        String result="";//接受百分比的值
        double x_double=data *1.0;
        double tempresult= x_double /total;
        //numberformat nf   =   numberformat.getpercentinstance();    // 注释掉的也是一种方法
        //nf.setminimumfractiondigits( 2 );       // 保留到小数点后几位
        //result=nf.format(tempresult);

        DecimalFormat df1 = new DecimalFormat("0.00%");    //##.00%   百分比格式，后面不足2位的用0补齐
        result= df1.format(tempresult);
        return result;
        //return MyFormat.getPercent(getPercent2Double(data, total));
    }


    /**
     * 两个值占比：部分数据占总数据N%
     * @param data 部分数据
     * @param total 总数据
     * @return 经规格化后的结果
     */
    public static String getPercent(double data, double total) {
        return MyFormat.getPercent(getPercent2Double(data, total));
    }

    /**
     * 两个值占比：部分数据占总数据N%
     * @param data 部分数据
     * @param total 总数据
     * @return 经规格化后的结果
     */
    public static String getPercentByInt(int data, int total) {
        return MyFormat.getPercentByInt(getPercent2Double(data, total));
    }

    /**
     * 两个值占比：部分数据占总数据N%
     * @param data 部分数据
     * @param total 总数据
     * @return 经规格化后的结果
     */
    public static double getPercent2Double(double data, double total) {
        if (total <= 0)
            return 0;
        if (data < 0)
            data = 0;
        return data * 1.0 / total;
    }

    /**
     * 规格化百分数，返回整数(输入0.015，输出2)
     * @param d 实际值
     * @return 00
     */
    public final static String getPercentByInt(double d) {
        DecimalFormat f = new DecimalFormat("0");
        return f.format(roundingDouble(d * 100, 0));
    }

    /**
     * 规格化百分数，返回带一位小数金额(输入0.015，输出1.5)
     * @param d 实际值
     * @return 00.0
     */
    public final static String getPercent(double d) {
        DecimalFormat f = new DecimalFormat("0.#");
        return f.format(roundingDouble(d * 100, 1));
    }

    /**
     * 对double按位数进行四舍五入，返回带digit位小数
     * @param d
     * @param digit 小数点位数
     * @return
     */
    public final static double roundingDouble(double d, int digit) {
        int bit = 1;
        for (int i = 0; i < digit; i++) {
            bit *= 10;
        }
        boolean minus = d < 0;
        if (minus) {
            d = -d;
        }
        return (((long) (d * bit + 1.5)) - 1) * 1.0 / bit * (minus ? -1 : 1);
    }
    /******************* 2.5 格式化数据类型方法结束：percent ***********************/

}
