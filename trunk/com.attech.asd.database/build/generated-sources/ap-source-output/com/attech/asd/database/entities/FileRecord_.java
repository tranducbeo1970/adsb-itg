package com.attech.asd.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FileRecord.class)
public abstract class FileRecord_ {

	public static volatile SingularAttribute<FileRecord, Long> countCraft;
	public static volatile SingularAttribute<FileRecord, String> fileName;
	public static volatile SingularAttribute<FileRecord, Long> countFlight;
	public static volatile SingularAttribute<FileRecord, Long> countMessage;
	public static volatile SingularAttribute<FileRecord, Date> fromtime;
	public static volatile SingularAttribute<FileRecord, String> absolutePath;
	public static volatile SingularAttribute<FileRecord, Sensors> sensor;
	public static volatile SingularAttribute<FileRecord, Integer> id;
	public static volatile SingularAttribute<FileRecord, Date> totime;
	public static volatile SingularAttribute<FileRecord, Long> countPackage;
	public static volatile SingularAttribute<FileRecord, Integer> status;

}

