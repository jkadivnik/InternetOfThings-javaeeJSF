package be.kadivnik.iot.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import be.kadivnik.iot.model.Device;

@ApplicationScoped
public class DeviceDAO {

    @Inject
    private EntityManager em;

    public Device findById(Long id) {
        return em.find(Device.class, id);
    }

    public Device findByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> criteria = cb.createQuery(Device.class);
        Root<Device> device = criteria.from(Device.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
        criteria.select(device).where(cb.equal(device.get("name"), name));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Device> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> criteria = cb.createQuery(Device.class);
        Root<Device> device = criteria.from(Device.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(device).orderBy(cb.asc(device.get("name")));
        return em.createQuery(criteria).getResultList();
    }
}
