package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Location;
import model.Transaction;
import model.Type;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-16T19:48:03")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Integer> itemId;
    public static volatile SingularAttribute<Item, Date> itemDateCreated;
    public static volatile SingularAttribute<Item, String> itemName;
    public static volatile SingularAttribute<Item, Location> locationId;
    public static volatile SingularAttribute<Item, String> itemCode;
    public static volatile ListAttribute<Item, Transaction> transactionList;
    public static volatile SingularAttribute<Item, Type> typeId;
    public static volatile SingularAttribute<Item, String> itemSpecification;

}