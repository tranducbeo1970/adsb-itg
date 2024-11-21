package com.attech.asd.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MessageIndex.class)
public abstract class MessageIndex_ {

	public static volatile SingularAttribute<MessageIndex, Integer> accountID;
	public static volatile SingularAttribute<MessageIndex, Date> lastUpdatedDate;
	public static volatile SingularAttribute<MessageIndex, Date> createdDate;
	public static volatile SingularAttribute<MessageIndex, String> subject;
	public static volatile SingularAttribute<MessageIndex, Integer> retryCount;
	public static volatile SingularAttribute<MessageIndex, String> origin;
	public static volatile SingularAttribute<MessageIndex, Integer> errorCode;
	public static volatile SingularAttribute<MessageIndex, String> description;
	public static volatile SingularAttribute<MessageIndex, Integer> id;
	public static volatile SingularAttribute<MessageIndex, Integer> seq;
	public static volatile SingularAttribute<MessageIndex, String> submissionTime;

}

