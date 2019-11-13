package com.kite.common.activeMQ.service;



public interface Producer {
	public void sendString(String queueName ,String message);

	public void sndJsonString(String queueName ,Object object);
}
