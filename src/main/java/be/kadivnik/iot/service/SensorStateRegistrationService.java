package be.kadivnik.iot.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import be.kadivnik.iot.model.SensorState;

@Stateless
public class SensorStateRegistrationService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<SensorState> sensorStateEventSrc;

    public void register(SensorState sensorState) {
        log.info("Registering " + sensorState.getName() + " for device " +  sensorState.getDevice().getName());
        em.persist(sensorState);
        sensorStateEventSrc.fire(sensorState);
    }
    
}
