package be.kadivnik.iot.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import be.kadivnik.iot.model.Device;

@RequestScoped
public class DeviceListProducer {

    @Inject
    private DeviceDAO deviceRepository;
    
    private List<Device> devices;
    
    @Produces
    @Named
    public List<Device> getDevices() {
        return devices;
    }

    public void onDeviceListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Device device) {
        retrieveAllDevicesOrderedByName();
    }
    
    @PostConstruct
    public void retrieveAllDevicesOrderedByName() {
        devices = deviceRepository.findAllOrderedByName();
    }
}
