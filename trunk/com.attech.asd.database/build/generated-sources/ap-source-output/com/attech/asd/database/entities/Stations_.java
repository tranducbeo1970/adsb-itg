package com.attech.asd.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Stations.class)
public abstract class Stations_ {

	public static volatile SetAttribute<Stations, Sensors> sensors;
	public static volatile SingularAttribute<Stations, String> stationDescription;
	public static volatile SingularAttribute<Stations, Integer> sortNumber;
	public static volatile SingularAttribute<Stations, String> stationName;
	public static volatile SingularAttribute<Stations, Integer> id;

}

