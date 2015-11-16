package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-16T19:48:03")
@StaticMetamodel(Groupe.class)
public class Groupe_ { 

    public static volatile SingularAttribute<Groupe, String> description;
    public static volatile SingularAttribute<Groupe, Integer> id;
    public static volatile SingularAttribute<Groupe, String> groupname;
    public static volatile SingularAttribute<Groupe, Users> users;
    public static volatile SingularAttribute<Groupe, String> username;

}