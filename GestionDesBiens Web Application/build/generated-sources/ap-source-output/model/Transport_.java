package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Personnel;
import model.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-16T19:48:03")
@StaticMetamodel(Transport.class)
public class Transport_ { 

    public static volatile SingularAttribute<Transport, Integer> transportId;
    public static volatile SingularAttribute<Transport, Personnel> personnelId;
    public static volatile SingularAttribute<Transport, Date> transportDate;
    public static volatile ListAttribute<Transport, Transaction> transactionList;

}