package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Center;
import model.Item;
import model.Personnel;
import model.Salle;
import model.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-16T19:48:03")
@StaticMetamodel(Location.class)
public class Location_ { 

    public static volatile ListAttribute<Location, Transaction> transactionList2;
    public static volatile SingularAttribute<Location, Center> centerId;
    public static volatile ListAttribute<Location, Transaction> transactionList1;
    public static volatile SingularAttribute<Location, Personnel> personnelId;
    public static volatile SingularAttribute<Location, Salle> salleId;
    public static volatile SingularAttribute<Location, Integer> locationId;
    public static volatile ListAttribute<Location, Transaction> transactionList;
    public static volatile ListAttribute<Location, Item> itemList;

}