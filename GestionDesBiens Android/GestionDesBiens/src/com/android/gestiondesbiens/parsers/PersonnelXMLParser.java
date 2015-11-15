package com.android.gestiondesbiens.parsers;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.gestiondesbiens.model.Personnel;


public class PersonnelXMLParser {

	public static List<Personnel> parseFeed(String content) {
		
		try {
			
		    boolean inDataItemTag = false;
		    String currentTagName = "";
		    Personnel personnel = null;
		    List<Personnel> personnelList = new ArrayList<>();

		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    XmlPullParser parser = factory.newPullParser();
		    parser.setInput(new StringReader(content));

		    int eventType = parser.getEventType();

		    while (eventType != XmlPullParser.END_DOCUMENT) {

		        switch (eventType) {
		            case XmlPullParser.START_TAG:
		                currentTagName = parser.getName();
		                if (currentTagName.equals("personnel")) {
		                    inDataItemTag = true;
		                    personnel = new Personnel();
		                    personnelList.add(personnel);
		                }
		                break;

		            case XmlPullParser.END_TAG:
		                if (parser.getName().equals("personnel")) {
		                    inDataItemTag = false;
		                }
		                currentTagName = "";
		                break;

		            case XmlPullParser.TEXT:
		                if (inDataItemTag && personnel != null) {
		                    switch (currentTagName) {
		                        case "personnelId":
		                        	personnel.setPersonnelId(Integer.parseInt(parser.getText()));
		                            break;
		                        case "personnelName":
		                        	personnel.setPersonnelName(parser.getText());
		                        	break;

		                    }
		                }
		                break;
		        }

		       eventType = parser.next();

		    }

		    return personnelList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

		
	}
	
}

