package entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2023-08-22T11:10:53")
@StaticMetamodel(FlightControl.class)
public class FlightControl_ { 

    public static volatile SingularAttribute<FlightControl, String> controller;
    public static volatile SingularAttribute<FlightControl, String> address;
    public static volatile SingularAttribute<FlightControl, Date> assumTime;
    public static volatile SingularAttribute<FlightControl, Date> lastUpdate;
    public static volatile SingularAttribute<FlightControl, String> callSign;
    public static volatile SingularAttribute<FlightControl, String> targetCotroller;
    public static volatile SingularAttribute<FlightControl, Integer> status;

}