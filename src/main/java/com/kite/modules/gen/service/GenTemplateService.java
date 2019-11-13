/**
 * MouTai
 */
package com.kite.modules.gen.service;

import com.kite.common.persistence.Page;
import com.kite.common.service.BaseService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.gen.dao.GenTemplateDao;
import com.kite.modules.gen.entity.GenTemplate;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 代码模板Service
 *
 * @author Czh
 * @version 2013-10-15
 */
@Service
@Transactional(readOnly = true)
public class GenTemplateService extends BaseService {

    @Autowired
    private GenTemplateDao genTemplateDao;

    public GenTemplateService() {
    }

    public GenTemplate get(String id) {
        return genTemplateDao.get(id);
    }

    public Page<GenTemplate> find(Page<GenTemplate> page, GenTemplate genTemplate) {
        genTemplate.setPage(page);
        page.setList(genTemplateDao.findList(genTemplate));
        return page;
    }

    @Transactional(readOnly = false)
    public void save(GenTemplate genTemplate) {
        if (genTemplate.getContent() != null) {
            genTemplate.setContent(StringEscapeUtils.unescapeHtml4(genTemplate.getContent()));
        }
        if (StringUtils.isBlank(genTemplate.getId())) {
            genTemplate.preInsert();
            genTemplateDao.insert(genTemplate);
        } else {
            genTemplate.preUpdate();
            genTemplateDao.update(genTemplate);
        }
    }

    @Transactional(readOnly = false)
    public void delete(GenTemplate genTemplate) {
        genTemplateDao.delete(genTemplate);
    }

}
