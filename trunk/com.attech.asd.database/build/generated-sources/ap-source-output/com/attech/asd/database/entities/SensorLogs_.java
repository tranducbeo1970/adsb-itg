package com.attech.asd.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SensorLogs.class)
public abstract class SensorLogs_ {

	public static volatile SingularAttribute<SensorLogs, String> note;
	public static volatile SingularAttribute<SensorLogs, String> warningContent;
	public static volatile SingularAttribute<SensorLogs, Date> createdDate;
	public static volatile SingularAttribute<SensorLogs, Sensors> sensor;
	public static volatile SingularAttribute<SensorLogs, Integer> id;
	public static volatile SingularAttribute<SensorLogs, Integer> priority;
	public static volatile SingularAttribute<SensorLogs, Date> lastModifyDate;
	public static volatile SingularAttribute<SensorLogs, Integer> status;

}

