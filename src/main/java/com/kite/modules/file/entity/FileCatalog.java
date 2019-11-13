/**
 * MouTai
 */
package com.kite.modules.file.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.persistence.TreeEntity;
import com.kite.common.utils.excel.annotation.ExcelField;
import com.kite.modules.sys.entity.Office;

/**
 * 文档目录Entity
 * @author yyw
 * @version 2018-08-22
 */
public class FileCatalog extends TreeEntity<FileCatalog> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 文件目录名称
	private String parentId;		// 父id
	private String createId;		// 创建人id
	private String parentIds;		// 父id集合

	private String parentName;	//父目录名称

	private String extId; 		//已经选择的id用于过滤

	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public FileCatalog() {
		super();
	}

	public FileCatalog(String id){
		super(id);
	}
	@Override
	@ExcelField(title="文件目录名称", align=2, sort=1)
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	@ExcelField(title="父id", align=2, sort=2)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@ExcelField(title="创建人id", align=2, sort=4)
	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}
	@Override
	@ExcelField(title="父id集合", align=2, sort=7)
	public String getParentIds() {
		return parentIds;
	}
	@Override
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	@Override
	public FileCatalog getParent() {
		return parent;
	}
	@Override
	public void setParent(FileCatalog parent) {
		this.parent = parent;
	}
}