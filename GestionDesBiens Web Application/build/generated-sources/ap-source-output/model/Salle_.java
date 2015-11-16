package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Location;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-16T19:48:03")
@StaticMetamodel(Salle.class)
public class Salle_ { 

    public static volatile ListAttribute<Salle, Location> locationList;
    public static volatile SingularAttribute<Salle, String> salleName;
    public static volatile SingularAttribute<Salle, Integer> salleId;

}