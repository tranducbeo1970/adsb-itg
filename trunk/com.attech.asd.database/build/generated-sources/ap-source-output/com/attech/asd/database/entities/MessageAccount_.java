package com.attech.asd.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MessageAccount.class)
public abstract class MessageAccount_ {

	public static volatile SingularAttribute<MessageAccount, String> connectionString;
	public static volatile SingularAttribute<MessageAccount, String> password;
	public static volatile SingularAttribute<MessageAccount, Date> createdDate;
	public static volatile SingularAttribute<MessageAccount, Integer> updateInterval;
	public static volatile SingularAttribute<MessageAccount, Boolean> enable;
	public static volatile SingularAttribute<MessageAccount, String> name;
	public static volatile SingularAttribute<MessageAccount, String> description;
	public static volatile SingularAttribute<MessageAccount, Integer> id;
	public static volatile SingularAttribute<MessageAccount, Date> updatedDate;
	public static volatile SingularAttribute<MessageAccount, String> checkPoint;
	public static volatile SingularAttribute<MessageAccount, String> account;

}

