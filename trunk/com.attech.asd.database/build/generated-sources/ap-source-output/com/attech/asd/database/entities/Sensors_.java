package com.attech.asd.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sensors.class)
public abstract class Sensors_ {

	public static volatile SingularAttribute<Sensors, String> ReceivingBindIp;
	public static volatile SingularAttribute<Sensors, Float> latitude;
	public static volatile SingularAttribute<Sensors, String> description;
	public static volatile SingularAttribute<Sensors, String> ReceivingMulticastAddress;
	public static volatile SingularAttribute<Sensors, String> ReceivingMode;
	public static volatile SingularAttribute<Sensors, Integer> ReceivingPort;
	public static volatile SingularAttribute<Sensors, Integer> sic;
	public static volatile SingularAttribute<Sensors, Integer> BufferSize;
	public static volatile SingularAttribute<Sensors, Stations> station;
	public static volatile SingularAttribute<Sensors, Integer> id;
	public static volatile SingularAttribute<Sensors, Byte> sensorMode;
	public static volatile SingularAttribute<Sensors, Float> longitude;
	public static volatile SingularAttribute<Sensors, Byte> status;

}

