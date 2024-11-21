package com.attech.asd.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Airports.class)
public abstract class Airports_ {

	public static volatile SingularAttribute<Airports, String> address;
	public static volatile SingularAttribute<Airports, String> iata;
	public static volatile SingularAttribute<Airports, Double> latitude;
	public static volatile SingularAttribute<Airports, String> name;
	public static volatile SingularAttribute<Airports, String> icao;
	public static volatile SingularAttribute<Airports, String> description;
	public static volatile SingularAttribute<Airports, Integer> id;
	public static volatile SingularAttribute<Airports, String> type;
	public static volatile SingularAttribute<Airports, Double> longitude;

}

