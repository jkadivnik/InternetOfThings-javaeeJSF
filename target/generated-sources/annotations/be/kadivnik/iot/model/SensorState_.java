package be.kadivnik.iot.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SensorState.class)
public abstract class SensorState_ {

	public static volatile SingularAttribute<SensorState, String> sensorType;
	public static volatile SingularAttribute<SensorState, String> name;
	public static volatile SingularAttribute<SensorState, Long> id;
	public static volatile SingularAttribute<SensorState, Device> device;
	public static volatile SingularAttribute<SensorState, String> sensorValue;

}

