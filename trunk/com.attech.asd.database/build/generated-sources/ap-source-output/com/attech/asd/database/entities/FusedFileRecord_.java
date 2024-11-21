package com.attech.asd.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FusedFileRecord.class)
public abstract class FusedFileRecord_ {

	public static volatile SingularAttribute<FusedFileRecord, Long> countCraft;
	public static volatile SingularAttribute<FusedFileRecord, String> fileName;
	public static volatile SingularAttribute<FusedFileRecord, Long> countFlight;
	public static volatile SingularAttribute<FusedFileRecord, Long> countMessage;
	public static volatile SingularAttribute<FusedFileRecord, Date> fromtime;
	public static volatile SingularAttribute<FusedFileRecord, String> absolutePath;
	public static volatile SingularAttribute<FusedFileRecord, Integer> id;
	public static volatile SingularAttribute<FusedFileRecord, Date> totime;
	public static volatile SingularAttribute<FusedFileRecord, Long> countPackage;
	public static volatile SingularAttribute<FusedFileRecord, Integer> status;

}

