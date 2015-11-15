package com.android.gestiondesbiens.parsers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.gestiondesbiens.model.Items;


public class ItemsXMLParser {

	public static List<Items> parseFeed(String content) {
		
		try {
			
		    boolean inDataItemTag = false;
		    String currentTagName = "";
		    Items items = null;
		    List<Items> itemsList = new ArrayList<>();

		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    XmlPullParser parser = factory.newPullParser();
		    parser.setInput(new StringReader(content));

		    int eventType = parser.getEventType();

		    while (eventType != XmlPullParser.END_DOCUMENT) {

		        switch (eventType) {
		            case XmlPullParser.START_TAG:
		                currentTagName = parser.getName();
		                if (currentTagName.equals("item")) {
		                    inDataItemTag = true;
		                    items = new Items();
		                    itemsList.add(items);
		                }
		                break;

		            case XmlPullParser.END_TAG:
		                if (parser.getName().equals("item")) {
		                    inDataItemTag = false;
		                }
		                currentTagName = "";
		                break;

		            case XmlPullParser.TEXT:
		                if (inDataItemTag && items != null) {
		                    switch (currentTagName) {
		                        case "itemId":
		                        	items.setItemId(Integer.parseInt(parser.getText()));
		                            break;
		                        case "itemCode":
		                        	items.setItemCode(parser.getText());
		                        	break;
		                        case "itemDateCreated":
		                        	items.setItemDateCreated(parser.getText());
		                        	break;
		                        case "itemName":
		                        	items.setItemName(parser.getText());
		                        	break;
		                        case "itemSpecification":
		                        	items.setItemSpecification(parser.getText());
		                            break;
		                        case "centerName":
		                        	items.setCenterName(parser.getText());
		                        	break;
		                        case "salleName":
		                        	items.setSalleName(parser.getText());
		                        	break;
		                        case "personnelName":
		                        	items.setPersonnelName(parser.getText());
		                        	break;
		                        case "typeName":
		                        	items.setTypeName(parser.getText());
		                        	break;

		                    }
		                }
		                break;
		        }

		       eventType = parser.next();

		    }

		    return itemsList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

		
	}
	
}

