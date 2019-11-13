/**
 * MouTai
 */
package com.kite.modules.file.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * fastdfs文件管理Entity
 * @author yyw
 * @version 2018-08-17
 */
public class FileFastdfs extends DataEntity<FileFastdfs> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 文件编码
	private String name;		// 文件名称
	private String url;		// 文件对应URL
	private String size;		// 文件大小
	private String level;		// 文件等级
	private String type;		// 文件类型 file_type 
	private String group;		// 文件分组类型 file_group

	private String catalogName;	//目录名称

	private String catalogId;	//文件管理目录id
	private String parentIds;	//文件父id集合

    private String delBy;  //删除人

    private Date delDate; //	删除时间

	private String jspUrl;	//页面URL

	private String createName; //创建人名称

	private String json;	//json数据

	private List<FileInfo> listFileInfo;	//文件list

	private String sizeStr;	//页面显示的文件大小

	private String selectDateStr;	//页面查询时间

	private Date beginDate;	//开始时间

	private Date endDate;	//结束时间

	private String delName;	//删除人名称

	private List<String> listId; //主键id

	private String currUserId; //当前人id

	private String currDeptId; //当前部门id


	private String currParentDeptId; //上级部门id

	private String roleType;	//角色类型

	private String ifAdmin = "N";	//角色类型

	private String userId; //所属人


	private String belongName; //所属人

	public String getBelongName() {
		return belongName;
	}

	public void setBelongName(String belongName) {
		this.belongName = belongName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIfAdmin() {
		return ifAdmin;
	}

	public void setIfAdmin(String ifAdmin) {
		this.ifAdmin = ifAdmin;
	}

	public String getCurrDeptId() {
		return currDeptId;
	}

	public void setCurrDeptId(String currDeptId) {
		this.currDeptId = currDeptId;
	}

	public String getCurrParentDeptId() {
		return currParentDeptId;
	}

	public void setCurrParentDeptId(String currParentDeptId) {
		this.currParentDeptId = currParentDeptId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getCurrUserId() {
		return currUserId;
	}

	public void setCurrUserId(String currUserId) {
		this.currUserId = currUserId;
	}

	public List<String> getListId() {
		return listId;
	}

	public void setListId(List<String> listId) {
		this.listId = listId;
	}

	public String getDelName() {
		return delName;
	}

	public void setDelName(String delName) {
		this.delName = delName;
	}


    public String getDelBy() {
        return delBy;
    }

    public void setDelBy(String delBy) {
        this.delBy = delBy;
    }

    public Date getDelDate() {
        return delDate;
    }

    public void setDelDate(Date delDate) {
        this.delDate = delDate;
    }

    public String getSelectDateStr() {
		return selectDateStr;
	}

	public void setSelectDateStr(String selectDateStr) {
		this.selectDateStr = selectDateStr;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSizeStr() {
		BigDecimal size = new BigDecimal(StringUtils.isEmpty(this.size)?"0":this.size);
		size = size.divide(new BigDecimal("1024")).setScale(2,BigDecimal.ROUND_HALF_UP);
		return String.valueOf(size.doubleValue()) + "K";
	}

	public void setSizeStr(String sizeStr) {
		this.sizeStr = sizeStr;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public List<FileInfo> getListFileInfo() {
		return listFileInfo;
	}

	public void setListFileInfo(List<FileInfo> listFileInfo) {
		this.listFileInfo = listFileInfo;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getJspUrl() {
		return jspUrl;
	}

	public void setJspUrl(String jspUrl) {
		this.jspUrl = jspUrl;
	}

	public FileFastdfs() {
		super();
	}

	public FileFastdfs(String id){
		super(id);
	}

	@ExcelField(title="文件编码", align=2, sort=1)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="文件名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="文件对应URL", align=2, sort=3)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="文件大小", align=2, sort=4)
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	@ExcelField(title="文件等级", align=2, sort=5)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@ExcelField(title="文件类型 file_type ", align=2, sort=6)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="文件分组类型 file_group", align=2, sort=7)
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
}