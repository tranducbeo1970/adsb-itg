package com.attech.asd.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Areas.class)
public abstract class Areas_ {

	public static volatile SingularAttribute<Areas, String> name;
	public static volatile ListAttribute<Areas, AreaCoordinates> coordinates;
	public static volatile SingularAttribute<Areas, String> description;
	public static volatile SingularAttribute<Areas, Integer> modifiedBy;
	public static volatile SingularAttribute<Areas, Integer> id;
	public static volatile SingularAttribute<Areas, Date> lastModified;
	public static volatile SingularAttribute<Areas, Integer> type;

}

