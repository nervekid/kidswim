package com.kite.modules.sys.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kite.modules.sys.utils.PagingUtils;

public class PagingUtilsTest {

	@Test
    public void divide2PagingTest() {
        // given

        // when

        // then
        assertEquals(31, PagingUtils.getBeginPagNum(3, 15));

        assertEquals(1, PagingUtils.getBeginPagNum(1, 15));

        assertEquals(44, PagingUtils.getEndPagNum(3, 44, 15));

        assertEquals(4, PagingUtils.getPageNum(46, 15));

        assertEquals(1, PagingUtils.getPageNum(3, 15));

        assertEquals(2, PagingUtils.getPageNum(30, 15));

        assertEquals(2, PagingUtils.getPageNum(29, 15));
    }
}
