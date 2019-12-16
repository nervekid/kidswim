/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.common.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kite.common.config.Global;
import com.kite.common.utils.CookieUtils;

/**
 * 分頁類
 * @author kite
 * @version 2013-7-2
 * @param <T>
 */
public class Page<T> {

	protected int pageNo = 1; // 當前頁碼
	protected int pageSize = Integer.valueOf(Global.getConfig("page.pageSize")); // 頁面大小，設置為“-1”表示不進行分頁（分頁無效）

	protected long count;// 總記錄數，設置為“-1”表示不查詢總數

	protected int first;// 首頁索引
	protected int last;// 尾頁索引
	protected int prev;// 上壹頁索引
	protected int next;// 下壹頁索引

	private boolean firstPage;//是否是第壹頁
	private boolean lastPage;//是否是最後壹頁

	protected int length = 8;// 顯示頁面長度
	protected int slider = 1;// 前後顯示頁面長度

	private List<T> list = new ArrayList<T>();

	private String orderBy = ""; // 標準查詢有效， 實例： updatedate desc, name asc

	protected String funcName = "page"; // 設置點擊頁碼調用的js函數名稱，默認為page，在壹頁有多個分頁對象時使用。

	protected String funcParam = ""; // 函數的附加參數，第三個參數值。

	private String message = ""; // 設置提示消息，顯示在“共n條”之後

	public Page() {
		this.pageSize = -1;
	}

	/**
	 * 構造方法
	 * @param request 傳遞 repage 參數，來記住頁碼
	 * @param response 用於設置 Cookie，記住頁碼
	 */
	public Page(HttpServletRequest request, HttpServletResponse response){
		this(request, response, -2);
	}

	/**
	 * 構造方法
	 * @param request 傳遞 repage 參數，來記住頁碼
	 * @param response 用於設置 Cookie，記住頁碼
	 * @param defaultPageSize 默認分頁大小，如果傳遞 -1 則為不分頁，返回所有數據
	 */
	public Page(HttpServletRequest request, HttpServletResponse response, int defaultPageSize){
		// 設置頁碼參數（傳遞repage參數，來記住頁碼）
		String no = request.getParameter("pageNo");
		if (StringUtils.isNumeric(no)){
			CookieUtils.setCookie(response, "pageNo", no);
			this.setPageNo(Integer.parseInt(no));
		}else if (request.getParameter("repage")!=null){
			no = CookieUtils.getCookie(request, "pageNo");
			if (StringUtils.isNumeric(no)){
				this.setPageNo(Integer.parseInt(no));
			}
		}
		// 設置頁面大小參數（傳遞repage參數，來記住頁碼大小）
		String size = request.getParameter("pageSize");
		if (StringUtils.isNumeric(size)){
			CookieUtils.setCookie(response, "pageSize", size);
			this.setPageSize(Integer.parseInt(size));
		}else if (request.getParameter("repage")!=null){
			no = CookieUtils.getCookie(request, "pageSize");
			if (StringUtils.isNumeric(size)){
				this.setPageSize(Integer.parseInt(size));
			}
		}else if (defaultPageSize != -2){
			this.pageSize = defaultPageSize;
		}
		// 設置排序參數
		String orderBy = request.getParameter("orderBy");
		if (StringUtils.isNotBlank(orderBy)){
			this.setOrderBy(orderBy);
		}
	}

	/**
	 * 構造方法
	 * @param pageNo 當前頁碼
	 * @param pageSize 分頁大小
	 */
	public Page(int pageNo, int pageSize) {
		this(pageNo, pageSize, 0);
	}

	/**
	 * 構造方法
	 * @param pageNo 當前頁碼
	 * @param pageSize 分頁大小
	 * @param count 數據條數
	 */
	public Page(int pageNo, int pageSize, long count) {
		this(pageNo, pageSize, count, new ArrayList<T>());
	}

	/**
	 * 構造方法
	 * @param pageNo 當前頁碼
	 * @param pageSize 分頁大小
	 * @param count 數據條數
	 * @param list 本頁數據對象列表
	 */
	public Page(int pageNo, int pageSize, long count, List<T> list) {
		this.setCount(count);
		this.setPageNo(pageNo);
		this.pageSize = pageSize;
		this.list = list;
	}

	/**
	 * 初始化參數
	 */
	public void initialize(){

		//1
		this.first = 1;

		this.last = (int)(count / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1);

		if (this.count % this.pageSize != 0 || this.last == 0) {
			this.last++;
		}

		if (this.last < this.first) {
			this.last = this.first;
		}

		if (this.pageNo <= 1) {
			this.pageNo = this.first;
			this.firstPage=true;
		}

		if (this.pageNo >= this.last) {
			this.pageNo = this.last;
			this.lastPage=true;
		}

		if (this.pageNo < this.last - 1) {
			this.next = this.pageNo + 1;
		} else {
			this.next = this.last;
		}

		if (this.pageNo > 1) {
			this.prev = this.pageNo - 1;
		} else {
			this.prev = this.first;
		}

		//2
		if (this.pageNo < this.first) {// 如果當前頁小於首頁
			this.pageNo = this.first;
		}

		if (this.pageNo > this.last) {// 如果當前頁大於尾頁
			this.pageNo = this.last;
		}

	}

	/**
	 * 默認輸出當前分頁標簽
	 * <div class="page">${page}</div>
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"fixed-table-pagination\" style=\"display: block;\">");
//		sb.append("<div class=\"dataTables_info\">");
//		sb.append("<li class=\"disabled controls\"><a href=\"javascript:\">當前 ");
//		sb.append("<input type=\"text\" value=\""+pageNo+"\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)");
//		sb.append(funcName+"(this.value,"+pageSize+",'"+funcParam+"');\" onclick=\"this.select();\"/> / ");
//		sb.append("<input type=\"text\" value=\""+pageSize+"\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)");
//		sb.append(funcName+"("+pageNo+",this.value,'"+funcParam+"');\" onclick=\"this.select();\"/> 條，");
//		sb.append("共 " + count + " 條"+(message!=null?message:"")+"</a></li>\n");
//		sb.append("</div>");
		long startIndex = (pageNo-1)*pageSize + 1;
		long endIndex = pageNo*pageSize <=count? pageNo*pageSize:count;

		sb.append("<div class=\"pull-left pagination-detail\">");
		sb.append("<span class=\"pagination-info\">顯示第 "+startIndex+" 到第 "+ endIndex +" 條記錄，總共 "+count+" 條記錄</span>");
		sb.append("<span class=\"page-list\">每頁顯示 <span class=\"btn-group dropup\">");
		sb.append("<button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\">");
		sb.append("<span class=\"page-size\">"+pageSize+"</span> <span class=\"caret\"></span>");
		sb.append("</button>");
		sb.append("<ul class=\"dropdown-menu\" role=\"menu\">");
		sb.append("<li class=\""+getSelected(pageSize,10)+ "\"><a href=\"javascript:"+funcName+"("+pageNo+",10,'"+funcParam+"');\">10</a></li>");
		sb.append("<li class=\""+getSelected(pageSize,25)+ "\"><a href=\"javascript:"+funcName+"("+pageNo+",25,'"+funcParam+"');\">25</a></li>");
		sb.append("<li class=\""+getSelected(pageSize,50)+ "\"><a href=\"javascript:"+funcName+"("+pageNo+",50,'"+funcParam+"');\">50</a></li>");
		sb.append("<li class=\""+getSelected(pageSize,100)+ "\"><a href=\"javascript:"+funcName+"("+pageNo+",100,'"+funcParam+"');\">100</a></li>");
		sb.append("</ul>");
		sb.append("</span> 條記錄</span>");
		sb.append("</div>");
//		sb.append("<p>每頁 <select onChange=\""+funcName+"("+pageNo+",this.value,'"+funcParam+"');\"" +"style=\"display:display  !important;\" class=\"form-control m-b input-sm\">" +
//		        "<option value=\"10\" "+getSelected(pageSize,10)+ ">10</option>" +
//				"<option value=\"25\" "+getSelected(pageSize,25)+ ">25</option>" +
//				"<option value=\"50\" "+getSelected(pageSize,50)+ ">50</option>" +
//				"<option value=\"100\" "+getSelected(pageSize,100)+ ">100</option>" +
//				"</select> 條記錄，顯示 " +startIndex+ " 到 "+ endIndex +" 條，共 "+count+" 條</p>");
//		sb.append("</div>");
//		sb.append("</div>");




		sb.append("<div class=\"pull-right pagination-roll\">");
		sb.append("<ul class=\"pagination pagination-outline\">");
		if (pageNo == first) {// 如果是首頁
			sb.append("<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"></i></a></li>\n");
			sb.append("<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"></i></a></li>\n");
		} else {
			sb.append("<li class=\"paginate_button previous\"><a href=\"javascript:\" onclick=\""+funcName+"("+first+","+pageSize+",'"+funcParam+"');\"><i class=\"fa fa-angle-double-left\"></i></a></li>\n");
			sb.append("<li class=\"paginate_button previous\"><a href=\"javascript:\" onclick=\""+funcName+"("+prev+","+pageSize+",'"+funcParam+"');\"><i class=\"fa fa-angle-left\"></i></a></li>\n");
		}

		int begin = pageNo - (length / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}

		if (begin > first) {
			int i = 0;
			for (i = first; i < first + slider && i < begin; i++) {
				sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
			if (i < begin) {
				sb.append("<li class=\"paginate_button disabled\"><a href=\"javascript:\">...</a></li>\n");
			}
		}

		for (int i = begin; i <= end; i++) {
			if (i == pageNo) {
				sb.append("<li class=\"paginate_button active\"><a href=\"javascript:\">" + (i + 1 - first)
						+ "</a></li>\n");
			} else {
				sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
		}

		if (last - end > slider) {
			sb.append("<li class=\"paginate_button disabled\"><a href=\"javascript:\">...</a></li>\n");
			end = last - slider;
		}

		for (int i = end + 1; i <= last; i++) {
			sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
					+ (i + 1 - first) + "</a></li>\n");
		}

		if (pageNo == last) {
			sb.append("<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"></i></a></li>\n");
			sb.append("<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"></i></a></li>\n");
		} else {
			sb.append("<li class=\"paginate_button next\"><a href=\"javascript:\" onclick=\""+funcName+"("+next+","+pageSize+",'"+funcParam+"');\">"
					+ "<i class=\"fa fa-angle-right\"></i></a></li>\n");
			sb.append("<li class=\"paginate_button next\"><a href=\"javascript:\" onclick=\""+funcName+"("+last+","+pageSize+",'"+funcParam+"');\">"
					+ "<i class=\"fa fa-angle-double-right\"></i></a></li>\n");
		}


        sb.append("</ul>");
        sb.append("</div>");
        sb.append("</div>");
//		sb.insert(0,"<ul>\n").append("</ul>\n");

//		sb.append("<div style=\"clear:both;\"></div>");

//		sb.insert(0,"<div class=\"page\">\n").append("</div>\n");

		return sb.toString();
	}

	protected String getSelected(int pageNo, int selectedPageNo){
		if(pageNo == selectedPageNo){
			//return "selected";
			return "active";
		}else{
			return "";
		}

	}
	/**
	 * 獲取分頁HTML代碼
	 * @return
	 */
	public String getHtml(){
		return toString();
	}

//	public static void main(String[] args) {
//		Page<String> p = new Page<String>(3, 3);
//		System.out.println(p);
//		System.out.println("首頁："+p.getFirst());
//		System.out.println("尾頁："+p.getLast());
//		System.out.println("上頁："+p.getPrev());
//		System.out.println("下頁："+p.getNext());
//	}

	/**
	 * 獲取設置總數
	 * @return
	 */
	public long getCount() {
		return count;
	}

	/**
	 * 設置數據總數
	 * @param count
	 */
	public void setCount(long count) {
		this.count = count;
		if (pageSize >= count){
			pageNo = 1;
		}
	}

	/**
	 * 獲取當前頁碼
	 * @return
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 設置當前頁碼
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 獲取頁面大小
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 設置頁面大小（最大500）
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize <= 0 ? 10 : pageSize;// > 500 ? 500 : pageSize;
	}

	/**
	 * 首頁索引
	 * @return
	 */
	@JsonIgnore
	public int getFirst() {
		return first;
	}

	/**
	 * 尾頁索引
	 * @return
	 */
	@JsonIgnore
	public int getLast() {
		return last;
	}

	/**
	 * 獲取頁面總數
	 * @return getLast();
	 */
	@JsonIgnore
	public int getTotalPage() {
		return getLast();
	}

	/**
	 * 是否為第壹頁
	 * @return
	 */
	@JsonIgnore
	public boolean isFirstPage() {
		return firstPage;
	}

	/**
	 * 是否為最後壹頁
	 * @return
	 */
	@JsonIgnore
	public boolean isLastPage() {
		return lastPage;
	}

	/**
	 * 上壹頁索引值
	 * @return
	 */
	@JsonIgnore
	public int getPrev() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	/**
	 * 下壹頁索引值
	 * @return
	 */
	@JsonIgnore
	public int getNext() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	/**
	 * 獲取本頁數據對象列表
	 * @return List<T>
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 設置本頁數據對象列表
	 * @param list
	 */
	public Page<T> setList(List<T> list) {
		this.list = list;
		initialize();
		return this;
	}

	/**
	 * 獲取查詢排序字符串
	 * @return
	 */
	@JsonIgnore
	public String getOrderBy() {
		// SQL過濾，防止註入
		String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
					+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
		Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		if (sqlPattern.matcher(orderBy).find()) {
			return "";
		}
		return orderBy;
	}

	/**
	 * 設置查詢排序，標準查詢有效， 實例： updatedate desc, name asc
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 獲取點擊頁碼調用的js函數名稱
	 * function ${page.funcName}(pageNo){location="${ctx}/list-${category.id}${urlSuffix}?pageNo="+i;}
	 * @return
	 */
	@JsonIgnore
	public String getFuncName() {
		return funcName;
	}

	/**
	 * 設置點擊頁碼調用的js函數名稱，默認為page，在壹頁有多個分頁對象時使用。
	 * @param funcName 默認為page
	 */
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	/**
	 * 獲取分頁函數的附加參數
	 * @return
	 */
	@JsonIgnore
	public String getFuncParam() {
		return funcParam;
	}

	/**
	 * 設置分頁函數的附加參數
	 * @return
	 */
	public void setFuncParam(String funcParam) {
		this.funcParam = funcParam;
	}

	/**
	 * 設置提示消息，顯示在“共n條”之後
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 分頁是否有效
	 * @return this.pageSize==-1
	 */
	@JsonIgnore
	public boolean isDisabled() {
		return this.pageSize==-1;
	}

	/**
	 * 是否進行總數統計
	 * @return this.count==-1
	 */
	@JsonIgnore
	public boolean isNotCount() {
		return this.count==-1;
	}

	/**
	 * 獲取 Hibernate FirstResult
	 */
	public int getFirstResult(){
		int firstResult = (getPageNo() - 1) * getPageSize();
		if (firstResult >= getCount() || firstResult<0) {
			firstResult = 0;
		}
		return firstResult;
	}
	/**
	 * 獲取 Hibernate MaxResults
	 */
	public int getMaxResults(){
		return getPageSize();
	}

//	/**
//	 * 獲取 Spring data JPA 分頁對象
//	 */
//	public Pageable getSpringPage(){
//		List<Order> orders = new ArrayList<Order>();
//		if (orderBy!=null){
//			for (String order : StringUtils.split(orderBy, ",")){
//				String[] o = StringUtils.split(order, " ");
//				if (o.length==1){
//					orders.add(new Order(Direction.ASC, o[0]));
//				}else if (o.length==2){
//					if ("DESC".equals(o[1].toUpperCase())){
//						orders.add(new Order(Direction.DESC, o[0]));
//					}else{
//						orders.add(new Order(Direction.ASC, o[0]));
//					}
//				}
//			}
//		}
//		return new PageRequest(this.pageNo - 1, this.pageSize, new Sort(orders));
//	}
//
//	/**
//	 * 設置 Spring data JPA 分頁對象，轉換為本系統分頁對象
//	 */
//	public void setSpringPage(org.springframework.data.domain.Page<T> page){
//		this.pageNo = page.getNumber();
//		this.pageSize = page.getSize();
//		this.count = page.getTotalElements();
//		this.list = page.getContent();
//	}

}
