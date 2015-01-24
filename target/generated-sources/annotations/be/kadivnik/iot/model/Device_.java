package be.kadivnik.iot.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Device.class)
public abstract class Device_ {

	public static volatile ListAttribute<Device, SensorState> sensorStates;
	public static volatile SingularAttribute<Device, String> name;
	public static volatile SingularAttribute<Device, Long> id;

}

