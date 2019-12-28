/**
 * KITE
 */
package com.kite.modules.att.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.att.dao.SerGroupDao;
import com.kite.modules.att.entity.SerGroup;
import com.kite.modules.att.entity.SerGroupDetails;
import com.kite.modules.att.enums.KidSwimDictEnum;

/**
 * 分组Service
 * @author lyb
 * @version 2019-12-19
 */
@Service
@Transactional(readOnly = true)
public class SerGroupService extends CrudService<SerGroupDao, SerGroup> {

    @Autowired
	private SerGroupDao serGroupDao;

    @Autowired
    private SerGroupDetailsService serGroupDetailsService;

    @Autowired
    private SerSaleService serSaleService;

	@Override
	public SerGroup get(String id) {
		return super.get(id);
	}

	@Override
	public List<SerGroup> findList(SerGroup serGroup) {
		return super.findList(serGroup);
	}

	@Override
	public Page<SerGroup> findPage(Page<SerGroup> page, SerGroup serGroup) {
		return super.findPage(page, serGroup);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(SerGroup serGroup) {
		super.save(serGroup);

		//插入明细
		String code = serGroup.getCode();
		SerGroup entity = this.dao.findSerGroupByCode(code);
		if (entity != null) {
			for (int i = 0; i < serGroup.getSaleIds().size(); i++) {
				try {
					//插入分组明细
					SerGroupDetails serGroupDetails = new SerGroupDetails();
					serGroupDetails.setCreateBy(entity.getCreateBy());
					serGroupDetails.setSaleId(serGroup.getSaleIds().get(i));
					serGroupDetails.setGroupId(entity.getId());
					this.serGroupDetailsService.save(serGroupDetails);

					//修改分组状态
					this.serSaleService.updateSetGroupFlagStatus(serGroup.getSaleIds().get(i), KidSwimDictEnum.yesNo.是.getName());

				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(SerGroup serGroup) {
		super.delete(serGroup);
	}

	@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();

		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(serGroupDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}

	/**
	 * 根据课程地址及分组开始时间查找分组列表
	 * @param courseAddress
	 * @param groupLearnBeginTime
	 * @return
	 */
	public List<SerGroup> findSerGroupListByAddressAndBeginTime(String courseAddress, String groupLearnBeginTime) {
		return this.dao.findSerGroupListByAddressAndBeginTime(courseAddress, groupLearnBeginTime);
	}

	/**
	 * 根据课程地址查看分组数量
	 * @param courseAddress
	 * @return
	 */
	public int findSerGroupByAddressNum(String courseAddress) {
		return this.dao.findSerGroupByAddressNum(courseAddress);
	}

	/**
	 * 根据编号查找分组
	 * @param code
	 * @return
	 */
	public SerGroup findSerGroupByCode(String code) {
		return this.dao.findSerGroupByCode(code);
	}

	/**
	 * 根据条件查找编号列表
	 * @param address
	 * @param leanBeginStr
	 * @param queryBegin
	 * @param queryEnd
	 * @return
	 */
	public List<String> findCodeStrListByCondition(String address, String leanBeginStr, Date queryBegin, Date queryEnd) {
		return this.dao.findCodesByCondition(address, leanBeginStr, queryBegin, queryEnd);
	}
}