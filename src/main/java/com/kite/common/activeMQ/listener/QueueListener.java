package com.kite.common.activeMQ.listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


public class QueueListener {
	protected Logger log = LoggerFactory.getLogger(this.getClass());


	@Transactional
	public void displayMail(String mail) {
		log.info("displayMail--我被调用了");
		System.out.println("从ActiveMQ队列myqueue中取出一条消息：");
		System.out.println(mail);
	}
}
