package com.kite.common.activeMQ.service.impl;

import com.kite.common.activeMQ.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("topic")
public class TopicImpl implements Producer {
	@Autowired
	@Qualifier("jmsTopicTemplate")
	private JmsTemplate jmsTopicTemplate;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTopicTemplate = jmsTemplate;
	}

	@Override
	public void sendString(String queueName, String  message){
		//jmsTopicTemplate.convertAndSend(queueName , message);
	}

	@Override
	public void sndJsonString(String queueName, Object object) {

	}

}
