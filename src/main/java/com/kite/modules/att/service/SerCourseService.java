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
import com.kite.modules.att.command.RpcAllCourseBeginTimeCommand;
import com.kite.modules.att.command.RpcCourseBeginInfo;
import com.kite.modules.att.command.UnGroupLevelCorrespondCountCommand;
import com.kite.modules.att.dao.SerCourseDao;
import com.kite.modules.att.dao.SerCourseDetailsDao;
import com.kite.modules.att.entity.SerCourse;
import com.kite.modules.att.entity.SerCourseDetails;

/**
 * 課程Service
 * @author lyb
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class SerCourseService extends CrudService<SerCourseDao, SerCourse> {

    @Autowired
	private SerCourseDao serCourseDao;
    @Autowired
	private SerCourseDetailsDao serCourseDetailsDao;
    @Autowired
    private SerCourseDetailsService serCourseDetailsService;

	@Override
	public SerCourse get(String id) {
		return super.get(id);
	}

	@Override
	public List<SerCourse> findList(SerCourse serCourse) {
		return super.findList(serCourse);
	}

	@Override
	public Page<SerCourse> findPage(Page<SerCourse> page, SerCourse serCourse) {
		return super.findPage(page, serCourse);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(SerCourse serCourse) {
		super.save(serCourse);

		//修改课程明细
		List<SerCourseDetails> details = serCourse.getSerCourseDetailsList();
		for (int i = 0; i < details.size(); i++) {
			if (details.get(i).getId() != null && !details.get(i).getId().equals("")) {
				SerCourseDetails serCourseDetails = this.serCourseDetailsDao.get(details.get(i).getId());
				serCourseDetails.setLearnDate(details.get(i).getLearnDate());
				serCourseDetails.setLearnBeginDate(details.get(i).getLearnBeginDate());
				serCourseDetails.setLearnEndDate(details.get(i).getLearnEndDate());
				serCourseDetails.setRollCallStatusFlag(details.get(i).getDelFlag());
				this.serCourseDetailsService.save(serCourseDetails);
			}
			else {
				SerCourseDetails serCourseDetails = new SerCourseDetails();
				serCourseDetails.setLearnDate(details.get(i).getLearnDate());
				serCourseDetails.setLearnBeginDate(details.get(i).getLearnBeginDate());
				serCourseDetails.setLearnEndDate(details.get(i).getLearnEndDate());
				serCourseDetails.setRollCallStatusFlag(details.get(i).getDelFlag());
				this.serCourseDetailsService.save(serCourseDetails);
			}
		}

	}

	@Override
	@Transactional(readOnly = false)
	public void delete(SerCourse serCourse) {
		List<SerCourseDetails> serCourseDetailsList = this.serCourseDetailsDao.findSerCourseDetailsListByCourseId(serCourse.getId());
		serCourseDetailsList.forEach(e -> {
			this.serCourseDetailsDao.deleteByLogic(e);
		});
		super.deleteByLogic(serCourse);
	}

	@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();

		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(serCourseDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}

	/**
	 * 根據地址及課程等級查找當前表格的數量
	 * @param level
	 * @param address
	 * @return
	 */
	public int findCountByLevelAndAddress(String level, String address) {
		return this.dao.findCourseByLevelAndAddress(level, address);
	}

	/**
	 * 根據課程編號查找課程id
	 * @param code
	 * @return
	 */
	public String findCourseIdByCode(String code) {
		return this.dao.findCourseIdByCode(code);
	}

	/**
	 * 根據課程父id查找課程明細
	 * @param courseId
	 * @return
	 */
	public List<SerCourseDetails> findSerCourseDetailsByCourseId(String courseId) {
		return this.serCourseDetailsDao.findSerCourseDetailsListByCourseId(courseId);
	}

	/**
	 * 根據編號查找
	 * @param code
	 * @return
	 */
	public List<SerCourse> findLikeByCode(String code) {
		return this.dao.findByLikeCode(code);
	}

	/**
	 * 根據編號唯壹查找
	 * @param code
	 * @return
	 */
	public SerCourse findSerCourseByCode(String code) {
		return this.dao.findByLikeCodeOnly(code);
	}

	/**
	 * 查看人员对应级别人数情况
	 * @param addressStr
	 * @param learnBeginTimeStr
	 * @param queryBeginDateTime
	 * @param queryEndDateTime
	 * @return
	 */
	public List<UnGroupLevelCorrespondCountCommand> findUnGroupLevelCorrespondCount(String addressStr,
			String learnBeginTimeStr,
			Date queryBeginDateTime,
			Date queryEndDateTime){
		return this.dao.findUnGroupLevelCorrespondCount(addressStr, learnBeginTimeStr, queryBeginDateTime, queryEndDateTime);
	}

	/**
	 * 根据开始日期，结束日期，地址，查找符合条件当天所有的课程明细开始时间
	 * @param address
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<RpcAllCourseBeginTimeCommand> findAllCourseBeginTimeByAddressAndBeginTime(
			String address, Date beginDate, Date endDate) {
		return this.serCourseDao.findCourseBeginTimeByAddressAndDate(address, beginDate, endDate);
	}

	/**
	 * 根据开始时间，结束时间，查找课程上课开始时间列表
	 * @param beginDate
	 * @param endDate
	 * @param address
	 * @return
	 */
	public List<RpcCourseBeginInfo> findRpcCourseBeginInfoByBeginDateAndAddress(Date beginDate,
			Date endDate, String address) {
		return this.serCourseDao.findRpcCourseBeginInfoByCourseBeginDateAndAddress(beginDate, endDate, address);
	}

}