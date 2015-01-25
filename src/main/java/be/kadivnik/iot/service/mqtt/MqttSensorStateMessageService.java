package be.kadivnik.iot.service.mqtt;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import be.kadivnik.iot.exception.MqttMessageException;
import be.kadivnik.iot.service.SensorStateService;
import be.kadivnik.iot.util.InternetOfThingsConstants;
import be.kadivnik.iot.util.MqttMessageTopicEnum;

/**
 * @author johnny
 *
 * Handles the SensorState messages being sent to the MQTT broker.
 * The message should contain 4 parts
 * 
 * <pre>
 *  devicename|sensorname|sensortype|sensorvalue
 * </pre>
 *
 */
@Stateless
public class MqttSensorStateMessageService implements MqttMessageService {

	@Inject 
	private Logger log;
	
	@Inject
	private SensorStateService sensorStateService;
	
	@Override
	public void handleMessage(String message) throws MqttMessageException {
		log.log(Level.INFO, "Checking the message correctness");
		if (hasMessageCorrectNumberOfParts(message)) {
			log.log(Level.FINE, "Sensorstate message has the correct number of parts");
			sensorStateService.handleMessage(message);
		}
	}

	@Override
	public String getTopicToHandle() {
		return MqttMessageTopicEnum.STATUS.getTopic();
	}

	/**
	 * The message should contain 4 parts divided by InternetOfThingsConstants.MESSAGE_SEPERATOR.
	 * 
	 * @param message
	 * @return true if the message is correct
	 */
	private boolean hasMessageCorrectNumberOfParts(String message) {
		boolean isMessageCorrect = Boolean.FALSE;

		if (message.split(InternetOfThingsConstants.MESSAGE_SEPERATOR).length == 4) {
			isMessageCorrect = Boolean.TRUE;
		}
		
		return isMessageCorrect;
	}

}
