package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Groupe;
import model.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-16T19:48:03")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, String> name;
    public static volatile SingularAttribute<Users, Date> registerDt;
    public static volatile ListAttribute<Users, Transaction> transactionList;
    public static volatile SingularAttribute<Users, Groupe> groupe;
    public static volatile SingularAttribute<Users, String> username;

}