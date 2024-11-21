package com.attech.asd.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, String> date;
	public static volatile SingularAttribute<Message, String> deliveryTime;
	public static volatile SingularAttribute<Message, String> origin;
	public static volatile SingularAttribute<Message, Date> updatedDate;
	public static volatile SingularAttribute<Message, Integer> priority;
	public static volatile SingularAttribute<Message, Integer> parsingCode;
	public static volatile SingularAttribute<Message, String> content;
	public static volatile SingularAttribute<Message, String> submissionTime;
	public static volatile SingularAttribute<Message, Integer> accountID;
	public static volatile SingularAttribute<Message, Date> createdDate;
	public static volatile SingularAttribute<Message, String> callsign;
	public static volatile SingularAttribute<Message, String> dof;
	public static volatile SingularAttribute<Message, Integer> id;
	public static volatile SingularAttribute<Message, String> category;
	public static volatile SingularAttribute<Message, Integer> seq;

}

