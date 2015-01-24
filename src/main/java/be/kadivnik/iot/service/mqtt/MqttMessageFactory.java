package be.kadivnik.iot.service.mqtt;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import be.kadivnik.iot.exception.MqttMessageException;

@Stateless
public class MqttMessageFactory {

	@Inject
	private Logger log;
	
	@Named("sensorStateMessageService")
	private MqttMessageService mqttSensorStateMessageService;

	public MqttMessageService getMessageService(String topic) throws MqttMessageException {
		if (topic.equalsIgnoreCase(mqttSensorStateMessageService.getTopicToHandle())) {
			return mqttSensorStateMessageService;
		} else {
			String message = topic + " is not a topic I can handle!";
			log.log(Level.SEVERE, message);
			throw new MqttMessageException(message);
		}
	}
	
}
