package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Item;
import model.Location;
import model.Transport;
import model.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-16T19:48:03")
@StaticMetamodel(Transaction.class)
public class Transaction_ { 

    public static volatile SingularAttribute<Transaction, Item> itemId;
    public static volatile SingularAttribute<Transaction, Transport> transportId;
    public static volatile SingularAttribute<Transaction, Location> locationIdDest;
    public static volatile SingularAttribute<Transaction, Location> locationIdSrc;
    public static volatile SingularAttribute<Transaction, Location> locationIdNow;
    public static volatile SingularAttribute<Transaction, Date> transactionDate;
    public static volatile SingularAttribute<Transaction, Integer> transactionId;
    public static volatile SingularAttribute<Transaction, String> status;
    public static volatile SingularAttribute<Transaction, Users> username;

}