package com.kite.modules.file.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kite.common.persistence.Page;
import com.kite.common.result.ResultSupport;
import com.kite.common.utils.api.ResultSupportUtilsV2;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.file.entity.FileFastdfs;
import com.kite.modules.file.service.FileFastdfsService;
import com.kite.modules.file.service.FileRecycleService;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.utils.UserUtils;

/**
 * @author yyw
 * @Description: 回收站
 * @date 2018/8/279:48
 */
@Controller
@RequestMapping(value = "${adminPath}/file/fileRecycle")
public class FileRecycleController extends BaseController implements BasicVerification {

    @Autowired
    private FileRecycleService fileRecycleService;

    @Autowired
    private FileFastdfsService fileFastdfsService;

    @Autowired
    private SysUserCollectionMenuService sysUserCollectionMenuService;



    /**
     　　* @Description: 回收站
     　　* @author yyw
     　　* @date 2018/8/24 13:41
     */
    @RequiresPermissions("file:recycle:list")
    @RequestMapping(value = "recycle")
    public String recycle(FileFastdfs fileFastdfs, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("page",  fileFastdfsService.findRecyclePage(new Page<FileFastdfs>(request, response), fileFastdfs));
        return "modules/file/fileFastdfsRecycle";
    }




    @RequiresPermissions("file:recycle:list")
    @RequestMapping(value = "recycleTree")
    public String recycleTree(FileFastdfs fileFastdfs, Model model) throws Exception{
        return "modules/file/fileFastdfsRecycleTree";
    }


    @RequiresPermissions("file:recycle:del")
    @RequestMapping(value = "deleteAll",method = RequestMethod.POST)
    @ResponseBody
    public ResultSupport<FileFastdfs> deleteAll(@RequestBody FileFastdfs fileFastdfs, RedirectAttributes redirectAttributes) {

        ResultSupport<FileFastdfs> result = ResultSupport.createMisResp();
        User user = UserUtils.getUser();
        try{

            if(user != null && org.apache.commons.lang3.StringUtils.isNotEmpty(user.getId())) {
                fileFastdfsService.deleteRecyclyList(fileFastdfs.getListId(),new Date(),user.getId());
                ResultSupportUtilsV2.fillResultSupport(result, "删除文件成功", new FileFastdfs());
            }else {
                ResultSupportUtilsV2.fillError(result,"删除文件失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResultSupportUtilsV2.fillError(result,"系统出现异常！");
        }
        return result;
    }

    @RequiresPermissions("file:recycle:renew")
    @RequestMapping(value = "renew",method = RequestMethod.POST)
    @ResponseBody
    public ResultSupport<FileFastdfs> renew(@RequestBody FileFastdfs fileFastdfs, RedirectAttributes redirectAttributes) {

        ResultSupport<FileFastdfs> result = ResultSupport.createMisResp();
        User user = UserUtils.getUser();
        try{

                fileFastdfsService.renew(fileFastdfs.getListId());
                ResultSupportUtilsV2.fillResultSupport(result, "恢复文件成功", new FileFastdfs());
        } catch (Exception e) {
            e.printStackTrace();
            ResultSupportUtilsV2.fillError(result,"系统出现异常！");
        }
        return result;
    }


}
