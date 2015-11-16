package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Item;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-16T19:48:03")
@StaticMetamodel(Type.class)
public class Type_ { 

    public static volatile SingularAttribute<Type, String> typeName;
    public static volatile SingularAttribute<Type, Integer> typeId;
    public static volatile ListAttribute<Type, Item> itemList;

}