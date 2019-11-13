package com.kite.modules.sys.utils;

/**
 * 此工具用于移动端分页查询,目的是为了得到开始数与结束数
 * @author lyb
 *
 */
public class PagingUtils {
	/**
     * 计算页数
     * @param total 总数
     * @param pageCount 每页数量
     * @return
     */
    public static int getPageNum(int total, int pageCount) {
        if (total <= pageCount) {
            return 1;
        }
        else {
            if (total % pageCount == 0) {
                return total / pageCount;
            }
            else {
                return total / pageCount + 1;
            }
        }
    }

    /**
     * 开始数
     * @param pageNum 页码
     * @param pageCount 页数量
     * @return
     */
    public static int getBeginPagNum(int pageNum, int pageCount) {
        return (pageNum - 1) * pageCount + 1;
    }

    /**
     * 结束数
     * @param pageNum 页码
     * @param pageCount 页数量
     * @param total 总数
     * @return
     */
    public static int getEndPagNum(int pageNum, int total, int pageCount) {
        if (total <= pageCount) {
            return total;
        }
        else {
            if (pageCount * pageNum < total) {
                return pageCount * pageNum;
            }
            else {
                int remainder = total % pageCount;
                return (pageNum - 1) * pageCount + remainder;
            }
        }

    }
}
