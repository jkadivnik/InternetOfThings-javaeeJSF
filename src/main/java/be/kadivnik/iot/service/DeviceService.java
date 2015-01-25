package be.kadivnik.iot.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import be.kadivnik.iot.data.DeviceDAO;
import be.kadivnik.iot.model.Device;
import be.kadivnik.iot.model.SensorState;

@Stateless
public class DeviceService implements DataAccessService<Device> {

    @Inject
    private Logger log;
	@Inject
	private DeviceDAO deviceDAO;
	@Inject
	private SensorStateService sensorStateService;
	@Inject
    private Event<Device> deviceEventSrc;
	
	public Device findDevice(Long id) {
		return deviceDAO.find(id);
	}

	public Device findDevice(String name) {
		return deviceDAO.findByName(name);
	}
	
	public List<Device> getDevices() {
		return deviceDAO.findAllOrderedByName();
	}

	public Device getDevice(String deviceName) {
		return findDevice(deviceName);
	}

	public Device create(Device device) {
        log.info("Creating " + device.getName());
		Device createdDevice = deviceDAO.create(device);
		deviceEventSrc.fire(device);
		return createdDevice;
	}

	public Device create(String deviceName) {
        log.info("Creating " + deviceName);
		Device device = new Device();
		device.setName(deviceName);
		return create(device);
	}
	
	public Device update(Device device) {
        log.info("Updating device with id : " + device.getId());
		Device updatedDevice = deviceDAO.update(device);
		deviceEventSrc.fire(device);
		return updatedDevice;
	}
	
	public void delete(Device device) {
        log.info("Deleting device with name : " + device.getName());
		List<SensorState> sensorStates = device.attachedSensors();
		sensorStateService.delete(sensorStates);
		deviceDAO.delete(device);
		deviceEventSrc.fire(device);
	}
}
