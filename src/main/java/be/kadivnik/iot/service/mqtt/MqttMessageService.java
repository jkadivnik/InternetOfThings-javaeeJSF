package be.kadivnik.iot.service.mqtt;

import be.kadivnik.iot.exception.MqttMessageException;

public interface MqttMessageService {

	void handleMessage(String message) throws MqttMessageException;
	String getTopicToHandle();
}
