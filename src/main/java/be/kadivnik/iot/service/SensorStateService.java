package be.kadivnik.iot.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import be.kadivnik.iot.model.Device;
import be.kadivnik.iot.model.SensorState;
import be.kadivnik.iot.util.InternetOfThingsConstants;

@Stateless
public class SensorStateService {

	@Inject
	private Logger log;
	
	@Inject
	private DeviceService deviceService;
	@Inject
	private SensorStateRegistrationService sensorStateRegistrationService;
	
	/**
	 * Will create a SensorState from a message retrieved through MQTT
	 * 
	 * @param message
	 * @return
	 */
	public SensorState createSensorState(String message) {
		log.log(Level.INFO, "Creating a new sensorstate entry from " + message);
		String[] messageParts = message.split(InternetOfThingsConstants.MESSAGE_SEPERATOR);
		
		Device device = deviceService.getDevice(messageParts[InternetOfThingsConstants.SENSORSTATE_DEVICENAME_POSITION]); // contains the name of the device
		SensorState sensorState = new SensorState();
		sensorState.setDevice(device);
		sensorState.setName(messageParts[InternetOfThingsConstants.SENSORSTATE_NAME_POSITION]);
		sensorState.setSensorType(messageParts[InternetOfThingsConstants.SENSORSTATE_TYPE_POSITION]);
		sensorState.setSensorValue(messageParts[InternetOfThingsConstants.SENSORSTATE_VALUE_POSITION]);
		
		sensorStateRegistrationService.register(sensorState);
		return sensorState;
	}
}
