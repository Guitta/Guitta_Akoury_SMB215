package com.android.gestiondesbiens.parsers;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.gestiondesbiens.model.Type;


public class TypeXMLParser {

	public static List<Type> parseFeed(String content) {
		
		try {
			
		    boolean inDataItemTag = false;
		    String currentTagName = "";
		    Type type = null;
		    List<Type> typeList = new ArrayList<>();

		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    XmlPullParser parser = factory.newPullParser();
		    parser.setInput(new StringReader(content));

		    int eventType = parser.getEventType();

		    while (eventType != XmlPullParser.END_DOCUMENT) {

		        switch (eventType) {
		            case XmlPullParser.START_TAG:
		                currentTagName = parser.getName();
		                if (currentTagName.equals("type")) {
		                    inDataItemTag = true;
		                    type = new Type();
		                    typeList.add(type);
		                }
		                break;

		            case XmlPullParser.END_TAG:
		                if (parser.getName().equals("type")) {
		                    inDataItemTag = false;
		                }
		                currentTagName = "";
		                break;

		            case XmlPullParser.TEXT:
		                if (inDataItemTag && type != null) {
		                    switch (currentTagName) {
		                        case "typeId":
		                        	type.setTypeId(Integer.parseInt(parser.getText()));
		                            break;
		                        case "typeName":
		                        	type.setTypeName(parser.getText());
		                        	break;

		                    }
		                }
		                break;
		        }

		       eventType = parser.next();

		    }

		    return typeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

		
	}
	
}

