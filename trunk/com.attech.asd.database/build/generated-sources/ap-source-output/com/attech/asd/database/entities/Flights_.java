package com.attech.asd.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Flights.class)
public abstract class Flights_ {

	public static volatile SingularAttribute<Flights, String> icao24Address;
	public static volatile SingularAttribute<Flights, String> code3A;
	public static volatile SingularAttribute<Flights, Integer> totalMessage;
	public static volatile SingularAttribute<Flights, String> callsign;
	public static volatile SingularAttribute<Flights, Date> fromtime;
	public static volatile SingularAttribute<Flights, Sensors> sensor;
	public static volatile SingularAttribute<Flights, Integer> totalFrame;
	public static volatile SingularAttribute<Flights, Integer> id;
	public static volatile SingularAttribute<Flights, Date> totime;
	public static volatile SingularAttribute<Flights, Integer> trackNo;

}

