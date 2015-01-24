package be.kadivnik.iot.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import be.kadivnik.iot.model.Device;
import be.kadivnik.iot.model.SensorState;

@ApplicationScoped
public class SensorStateDAO {

    @Inject
    private EntityManager em;

    public SensorState findById(Long id) {
        return em.find(SensorState.class, id);
    }

    public SensorState findByDevice(Device device) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SensorState> criteria = cb.createQuery(SensorState.class);
        Root<SensorState> sensorState = criteria.from(SensorState.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
        criteria.select(sensorState).where(cb.equal(sensorState.get("device"), device));
        return em.createQuery(criteria).getSingleResult();
    }

    public SensorState findByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SensorState> criteria = cb.createQuery(SensorState.class);
        Root<SensorState> sensorState = criteria.from(SensorState.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
        criteria.select(sensorState).where(cb.equal(sensorState.get("name"), name));
        return em.createQuery(criteria).getSingleResult();
    }

    public SensorState findByNameAndDevice(String name, Device device) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SensorState> criteria = cb.createQuery(SensorState.class);
        Root<SensorState> sensorState = criteria.from(SensorState.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
        criteria.select(sensorState).where(cb.equal(sensorState.get("name"), name), cb.equal(sensorState.get("device"),  device));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<SensorState> findAllOrderedByDeviceAndName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SensorState> criteria = cb.createQuery(SensorState.class);
        Root<SensorState> sensorState = criteria.from(SensorState.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(sensorState).orderBy(cb.asc(sensorState.get("name")), cb.asc(sensorState.get("device")));
        return em.createQuery(criteria).getResultList();
    }
}
