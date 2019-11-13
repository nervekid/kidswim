package com.kite.modules.gen.web;


import com.kite.common.utils.StringUtils;
import com.kite.common.web.BaseController;
import com.kite.modules.gen.entity.GenScheme;
import com.kite.modules.gen.service.CgAutoListService;
import com.kite.modules.gen.service.GenSchemeService;
import com.kite.modules.gen.service.GenTableService;
import com.kite.modules.gen.util.GenUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping({"${adminPath}/gen/cgAutoList"})
public class CgAutoListController extends BaseController {
    private static Logger logger = Logger.getLogger(CgAutoListController.class);
    @Autowired
    private GenSchemeService genSchemeService;
    @Autowired
    private GenTableService genTableService;
    @Autowired
    private CgAutoListService cgAutoListService;

    public CgAutoListController() {
    }

    @ModelAttribute
    private GenScheme get(@RequestParam(required = false) String id) {
        return StringUtils.isNotBlank(id) ? genSchemeService.get(id) : new GenScheme();
    }

    @RequestMapping({"list"})
    private void list(GenScheme genScheme, HttpServletResponse response) {
        long start = System.currentTimeMillis();

        Map<String, Object> dataModel = GenUtils.getDataModel(genScheme);
        String html = cgAutoListService.generateCode(genScheme);
        cgAutoListService.generateListCode(genScheme);

        try {
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-store");
            PrintWriter writer = response.getWriter();
            writer.println(html);
            writer.flush();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        long end = System.currentTimeMillis();
        logger.debug("动态列表生成耗时：" + (end - start) + " ms");
    }

    @RequestMapping({"test", ""})
    private static ModelAndView test() {
        return new ModelAndView("com/mtsite/modules/gen/template/viewList");
    }
}
