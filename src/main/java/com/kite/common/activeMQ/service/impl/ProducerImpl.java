package com.kite.common.activeMQ.service.impl;


import com.kite.common.activeMQ.service.Producer;
import com.kite.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("producer")
public class ProducerImpl implements Producer {


	@Autowired
	@Qualifier("jmsTemplate")
	private JmsTemplate jmsTemplate;


	private JsonUtils jsonUtils  = new JsonUtils();


	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}


	@Override
	public void sendString(String queueName ,String  message) {
		jmsTemplate.convertAndSend(queueName ,message);
	}

	@Override
	public void sndJsonString(String queueName, Object object) {
		jmsTemplate.convertAndSend(queueName , jsonUtils.objectToJson(object) );

	}

}
