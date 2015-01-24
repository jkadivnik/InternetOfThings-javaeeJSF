package be.kadivnik.iot.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import be.kadivnik.iot.model.Device;

@Stateless
public class DeviceRegistrationService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Device> deviceEventSrc;

    public void register(Device device) {
        log.info("Registering " + device.getName());
        em.persist(device);
        deviceEventSrc.fire(device);
    }
}
