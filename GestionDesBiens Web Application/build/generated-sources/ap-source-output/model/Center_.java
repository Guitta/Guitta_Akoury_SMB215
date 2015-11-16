package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Location;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-16T19:48:03")
@StaticMetamodel(Center.class)
public class Center_ { 

    public static volatile SingularAttribute<Center, Integer> centerId;
    public static volatile ListAttribute<Center, Location> locationList;
    public static volatile SingularAttribute<Center, String> centerName;

}