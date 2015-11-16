package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Location;
import model.Transport;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-16T19:48:03")
@StaticMetamodel(Personnel.class)
public class Personnel_ { 

    public static volatile ListAttribute<Personnel, Location> locationList;
    public static volatile SingularAttribute<Personnel, String> personnelName;
    public static volatile ListAttribute<Personnel, Transport> transportList;
    public static volatile SingularAttribute<Personnel, Integer> personnelId;

}