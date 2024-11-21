package com.attech.asd.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ {

	public static volatile SingularAttribute<Client, String> forwardAddress;
	public static volatile SingularAttribute<Client, Double> latitude;
	public static volatile SingularAttribute<Client, String> description;
	public static volatile SetAttribute<Client, Areas> areas;
	public static volatile SingularAttribute<Client, Boolean> forwarding;
	public static volatile SingularAttribute<Client, Integer> forwardPort;
	public static volatile SingularAttribute<Client, String> forwardMode;
	public static volatile SingularAttribute<Client, Float> heightMin;
	public static volatile SingularAttribute<Client, Integer> sicFwd;
	public static volatile SingularAttribute<Client, String> name;
	public static volatile SingularAttribute<Client, Integer> forwardingMultiCastTTL;
	public static volatile SingularAttribute<Client, Float> heightMax;
	public static volatile SingularAttribute<Client, Integer> id;
	public static volatile SingularAttribute<Client, String> forwardBindIp;
	public static volatile SingularAttribute<Client, Integer> idSensorFwd;
	public static volatile SingularAttribute<Client, Double> longitude;
	public static volatile SingularAttribute<Client, Integer> bufferSize;
	public static volatile SingularAttribute<Client, Byte> status;
	public static volatile SetAttribute<Client, Circulars> circulars;

}

