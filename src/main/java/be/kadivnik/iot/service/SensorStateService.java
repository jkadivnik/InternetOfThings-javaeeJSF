package be.kadivnik.iot.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import be.kadivnik.iot.data.SensorStateDAO;
import be.kadivnik.iot.model.Device;
import be.kadivnik.iot.model.SensorState;
import be.kadivnik.iot.util.InternetOfThingsConstants;

@Stateless
public class SensorStateService implements DataAccessService<SensorState> {

    @Inject
    private Logger log;
	@Inject
	private DeviceService deviceService;
	@Inject
	private SensorStateDAO sensorStateDAO;
    @Inject
    private Event<SensorState> sensorStateEventSrc;
    @Inject
    private Event<List<SensorState>> sensorStateListEventSrc;
    

    public SensorState create(SensorState sensorState) {
        log.info("Registering " + sensorState.getName() + " for device " +  sensorState.getDevice().getName());
        SensorState createdSensorState = sensorStateDAO.create(sensorState);
        sensorStateEventSrc.fire(sensorState);
        return createdSensorState;
    }

	/**
	 * Will create a SensorState from a message retrieved through MQTT
	 * 
	 * @param message
	 * @return
	 */
	public SensorState handleMessage(String message) {
		log.log(Level.FINE, "Splitting the message in parts using " + InternetOfThingsConstants.MESSAGE_SEPERATOR + " as the seperator");
		String[] messageParts = message.split(InternetOfThingsConstants.MESSAGE_SEPERATOR);
		
		String deviceName = messageParts[InternetOfThingsConstants.SENSORSTATE_DEVICENAME_POSITION];
		
		SensorState sensorState;
		Device device = deviceService.getDevice(deviceName); // contains the name of the device
		if (device != null) {
			log.log(Level.FINE, "Device " + device.getName() + " already exists.");
			String sensorName = messageParts[InternetOfThingsConstants.SENSORSTATE_NAME_POSITION];
			log.log(Level.FINE, "Checking if sensor " + sensorName + " already exists!");
			sensorState = sensorStateDAO.findByNameAndDevice(sensorName, device);
			if (sensorState != null) {
				log.log(Level.FINE, "Sensor already exists(" + sensorState.getId() + "), so we will update it");
				
			} else {
				sensorState = create(device, messageParts);
			}
		} else {
			device = deviceService.create(deviceName);
			sensorState = create(device, messageParts);
		}
		return sensorState;
	}

	public void delete(SensorState sensorState) {
        log.info("Deleting " + sensorState.getName() + " for device " + sensorState.getDevice().getName());
		sensorStateDAO.delete(sensorState);
		sensorStateEventSrc.fire(sensorState);
	}
	
	public void delete(List<SensorState> sensorStates) {
        log.info("Deleting list of sensorStates : " + sensorStates.size());
		sensorStateDAO.deleteItems(sensorStates.toArray(new SensorState[sensorStates.size()]));
		sensorStateListEventSrc.fire(sensorStates);
	}
	
	public SensorState update(SensorState sensorState) {
		log.log(Level.INFO, "Updating sensor " + sensorState.getName() + " for device " + sensorState.getDevice().getName() +"!");
		SensorState updatedSensorState = sensorStateDAO.update(sensorState);
		sensorStateEventSrc.fire(updatedSensorState);
		return updatedSensorState;
	}

	private SensorState create(Device device, String[] messageParts) {
		log.log(Level.INFO, "Creating a new sensorstate entry from " + messageParts.toString());
		SensorState sensorState = new SensorState();
		sensorState.setDevice(device);
		sensorState.setName(messageParts[InternetOfThingsConstants.SENSORSTATE_NAME_POSITION]);
		sensorState.setSensorType(messageParts[InternetOfThingsConstants.SENSORSTATE_TYPE_POSITION]);
		sensorState.setSensorValue(messageParts[InternetOfThingsConstants.SENSORSTATE_VALUE_POSITION]);
		return create(sensorState);
	}
}
