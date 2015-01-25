package be.kadivnik.iot.data.producer;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import be.kadivnik.iot.data.SensorStateDAO;
import be.kadivnik.iot.model.SensorState;

@RequestScoped
public class SensorStateListProducer {

	@Inject
	private SensorStateDAO sensorStateRepository;
	
	private List<SensorState> sensorStates;
	
	@Produces
	@Named
	public List<SensorState> getSensorStates() {
		return sensorStates;
	}
	
	public void onSensorStateChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final SensorState sensorState) {
		retrieveAllSensorStatesOrderedByNameAndDevice();
	}

	public void onSensorStateChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final List<SensorState> sensorStates) {
		retrieveAllSensorStatesOrderedByNameAndDevice();
	}

	@PostConstruct
	private void retrieveAllSensorStatesOrderedByNameAndDevice() {
		sensorStates = sensorStateRepository.findAllOrderedByDeviceAndName();
	}
}
