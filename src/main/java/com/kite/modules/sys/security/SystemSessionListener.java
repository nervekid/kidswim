package com.kite.modules.sys.security;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.kite.common.utils.MyBeanUtils;
import com.kite.modules.sys.entity.SessionSituation;
import com.kite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.kite.modules.sys.service.SessionSituationService;
import com.kite.modules.sys.utils.UserUtils;

public class SystemSessionListener implements SessionListener {
	@Autowired private SessionSituationService sessionSituationService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void onStart(Session session) {
		/*System.out.println(this.sdf.format(session.getStartTimestamp()) + "会话创建：" + session.getId());
		SessionSituation entity = new SessionSituation();
		entity.setSessionId(String.valueOf(session.getId()));
		entity.setCreateTime(session.getStartTimestamp());
		entity.setPlanExitTime(Integer.parseInt(String.valueOf(session.getTimeout())));
		entity.setIsAbnormal("0");
		this.sessionSituationService.save(entity);*/
	}

	@Override
	public void onStop(Session session) {
		/*System.out.println(this.sdf.format(new Date()) + "会话退出：" + session.getId());*/
	}

	@Override
	public void onExpiration(Session session) {
		/*System.out.println(this.sdf.format(new Date()) + "会话过期：" + session.getId());
		SessionSituation sessionSituation = this.sessionSituationService.findBySessionId(String.valueOf(session.getId()));
		if (sessionSituation != null) {
			Date date = new Date();
			//获取会话最后操作时间
			long begin = session.getLastAccessTime().getTime();
			long end = date.getTime();
			long difference = end - begin;
			SessionSituation entity = new SessionSituation();
			entity.setActualExitTime(Integer.parseInt(String.valueOf(difference)));
			entity.setExpireTime(date);
			if (difference > sessionSituation.getPlanExitTime()) {
				entity.setIsAbnormal("0");
			}
			else {
				entity.setIsAbnormal("1");
			}
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sessionSituation, entity);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			this.sessionSituationService.save(entity);
		}*/
	}


}
