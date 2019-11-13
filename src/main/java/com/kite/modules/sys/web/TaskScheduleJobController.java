/**
 * KITE
 */
package com.kite.modules.sys.web;

import com.google.common.collect.Lists;
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.sys.entity.TaskScheduleJob;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.service.TaskScheduleJobService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务Controller
 * @author cxh
 * @version 2018-10-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/taskScheduleJob")
public class TaskScheduleJobController extends BaseController implements BasicVerification {

	@Autowired
	private TaskScheduleJobService taskScheduleJobService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public TaskScheduleJob get(@RequestParam(required=false) String id) {
		TaskScheduleJob entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = taskScheduleJobService.get(id);
		}
		if (entity == null){
			entity = new TaskScheduleJob();
		}
		return entity;
	}

	/**
	 * 定时任务列表页面
	 */
	@RequiresPermissions("sys:taskScheduleJob:list")
	@RequestMapping(value = {"list", ""})
	public String list(TaskScheduleJob taskScheduleJob, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TaskScheduleJob> page = taskScheduleJobService.findPage(new Page<TaskScheduleJob>(request, response), taskScheduleJob);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/taskScheduleJobList";
	}


	/**
	 * 查看，增加，编辑定时任务表单页面
	 */
	@RequiresPermissions(value={"sys:taskScheduleJob:view","sys:taskScheduleJob:add","sys:taskScheduleJob:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TaskScheduleJob taskScheduleJob, Model model) {
		model.addAttribute("taskScheduleJob", taskScheduleJob);
		if(taskScheduleJob.getId()==null){
			// taskScheduleJob.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/taskScheduleJobForm";
	}

	/**
	 * 查看打印定时任务表单页面
	 */
	@RequiresPermissions(value={"sys:taskScheduleJob:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(TaskScheduleJob taskScheduleJob, Model model) {
		model.addAttribute("taskScheduleJob", taskScheduleJob);
		return "modules/sys/taskScheduleJobView";
	}

	/**
	 * 保存定时任务
	 */
	@RequiresPermissions(value={"sys:taskScheduleJob:add","sys:taskScheduleJob:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TaskScheduleJob taskScheduleJob, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, taskScheduleJob)){
			return form(taskScheduleJob, model);
		}
		if(!taskScheduleJob.getIsNewRecord()){//编辑表单保存
			TaskScheduleJob t = taskScheduleJobService.get(taskScheduleJob.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(taskScheduleJob, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			taskScheduleJobService.save(t);//保存
		}else{//新增表单保存
			taskScheduleJobService.save(taskScheduleJob);//保存
		}
		addMessage(redirectAttributes, "保存定时任务成功");
		return "redirect:"+Global.getAdminPath()+"/sys/taskScheduleJob/?menuId="+taskScheduleJob.getMenuId();
	}

	/**
	 * 删除定时任务
	 */
	@RequiresPermissions("sys:taskScheduleJob:del")
	@RequestMapping(value = "delete")
	public String delete(TaskScheduleJob taskScheduleJob, RedirectAttributes redirectAttributes) {
		taskScheduleJobService.delete(taskScheduleJob);
		addMessage(redirectAttributes, "删除定时任务成功");
		return "redirect:"+Global.getAdminPath()+"/sys/taskScheduleJob/?repage";
	}

	/**
	 * 批量删除定时任务
	 */
	@RequiresPermissions("sys:taskScheduleJob:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			taskScheduleJobService.delete(taskScheduleJobService.get(id));
		}
		addMessage(redirectAttributes, "删除定时任务成功");
		return "redirect:"+Global.getAdminPath()+"/sys/taskScheduleJob/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:taskScheduleJob:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(TaskScheduleJob taskScheduleJob, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "定时任务"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TaskScheduleJob> page = taskScheduleJobService.findPage(new Page<TaskScheduleJob>(request, response, -1), taskScheduleJob);
    		new ExportExcel("定时任务", TaskScheduleJob.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出定时任务记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/taskScheduleJob/?menuId="+taskScheduleJob.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:taskScheduleJob:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, HttpServletResponse response, String menuId, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			this.check(ei);
			if (!ei.isCheckOk) {
				this.isTip = true;
				ei.write(response, "定时任务列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<TaskScheduleJob> list = ei.getDataList(TaskScheduleJob.class);
				for (TaskScheduleJob taskScheduleJob : list){
					try{
						taskScheduleJobService.save(taskScheduleJob);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条定时任务记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条定时任务记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入定时任务失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/taskScheduleJob/?menuId="+menuId;
    }

	@Override
	public void check(ImportExcel ei) {

	}

	@ResponseBody
	@RequestMapping(value = "import/tip")
	public Map<String, String> tip(RedirectAttributes redirectAttributes, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		if (this.isTip) {
			map.put("isAlert", "1");
			return map;
		}
		map.put("isAlert", "0");
		return map;
	}

	/**
	 * 下载导入定时任务数据模板
	 */
	@RequiresPermissions("sys:taskScheduleJob:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "定时任务数据导入模板.xlsx";
    		List<TaskScheduleJob> list = Lists.newArrayList();
    		new ExportExcel("定时任务数据", TaskScheduleJob.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/taskScheduleJob/?repage";
    }


	@RequestMapping(value = "/changeJobStatus")
	public String changeJobStatus(TaskScheduleJob scheduleJob, HttpServletRequest request,
									HttpServletResponse response) {
		String cmd = request.getParameter("cmd");
		try {
			taskScheduleJobService.changeStatus(scheduleJob.getId(), cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/sys/taskScheduleJob/?repage";
	}



}