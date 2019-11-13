/**
 * KITE
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysTToken;
import org.apache.ibatis.annotations.Param;

/**
 * token配置信息DAO接口
 * @author cxh
 * @version 2019-01-24
 */
@MyBatisDao
public interface SysTTokenDao extends CrudDao<SysTToken> {


    SysTToken getByPidAndSecret(@Param("pid") String pid, @Param("secret") String secret);

    SysTToken getByIp(@Param("ip") String ip);

    int checkPidAndScretAndWorspaceIdExist(SysTToken sysTToken);

    int checkPidSecrectAndWorkspaceId(@Param("pid") String pid, @Param("secret") String secret, @Param("workspaceId") String workspaceId);

    SysTToken getByPidSecretAndWorkspaceId(SysTToken sysTToken);
}