package be.kadivnik.iot.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import be.kadivnik.iot.data.DeviceDAO;
import be.kadivnik.iot.model.Device;

@Stateless
public class DeviceService {

	@Inject
	private DeviceDAO deviceDAO;
	@Inject
	private DeviceRegistrationService deviceRegistrationService;
	
	public Device findDevice(Long id) {
		return deviceDAO.findById(id);
	}

	public Device findDevice(String name) {
		return deviceDAO.findByName(name);
	}
	
	public List<Device> getDevices() {
		return deviceDAO.findAllOrderedByName();
	}

	public Device getDevice(String deviceName) {
		Device device = findDevice(deviceName);
		if (device == null) {
			device = createDevice(deviceName);
			deviceRegistrationService.register(device);
		}
		return device;
	}

	private Device createDevice(String deviceName) {
		Device device = new Device();
		device.setName(deviceName);
		return device;
	}
}
