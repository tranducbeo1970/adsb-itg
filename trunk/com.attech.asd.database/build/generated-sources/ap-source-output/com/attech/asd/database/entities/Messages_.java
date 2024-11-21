package com.attech.asd.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Messages.class)
public abstract class Messages_ {

	public static volatile SingularAttribute<Messages, String> atsExtention;
	public static volatile SingularAttribute<Messages, Date> date;
	public static volatile SingularAttribute<Messages, Date> lastUpdated;
	public static volatile SingularAttribute<Messages, Date> createdDate;
	public static volatile SingularAttribute<Messages, Date> deliveryTime;
	public static volatile SingularAttribute<Messages, Date> atsFilingTime;
	public static volatile SingularAttribute<Messages, Long> id;
	public static volatile SingularAttribute<Messages, String> atsPriority;
	public static volatile SingularAttribute<Messages, String> category;
	public static volatile SingularAttribute<Messages, String> atsOhi;
	public static volatile SingularAttribute<Messages, String> content;

}

