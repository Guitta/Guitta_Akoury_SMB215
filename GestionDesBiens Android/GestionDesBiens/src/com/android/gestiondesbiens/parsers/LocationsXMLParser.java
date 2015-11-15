package com.android.gestiondesbiens.parsers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.gestiondesbiens.model.Locations;


public class LocationsXMLParser {

	public static List<Locations> parseFeed(String content) {
		
		try {
			
		    boolean inDataItemTag = false;
		    String currentTagName = "";
		    Locations locations = null;
		    List<Locations> locationsList = new ArrayList<>();

		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    XmlPullParser parser = factory.newPullParser();
		    parser.setInput(new StringReader(content));

		    int eventType = parser.getEventType();

		    while (eventType != XmlPullParser.END_DOCUMENT) {

		        switch (eventType) {
		            case XmlPullParser.START_TAG:
		                currentTagName = parser.getName();
		                if (currentTagName.equals("location")) {
		                    inDataItemTag = true;
		                    locations = new Locations();
		                    locationsList.add(locations);
		                }
		                break;

		            case XmlPullParser.END_TAG:
		                if (parser.getName().equals("location")) {
		                    inDataItemTag = false;
		                }
		                currentTagName = "";
		                break;

		            case XmlPullParser.TEXT:
		                if (inDataItemTag && locations != null) {
		                    switch (currentTagName) {
		                        case "locationId":
		                        	locations.setLocationId(Integer.parseInt(parser.getText()));
		                            break;
		                        case "centerName":
		                        	locations.setCenterName(parser.getText());
		                        	break;
		                        case "salleName":
		                        	locations.setSalleName(parser.getText());
		                        	break;
		                        case "personnelName":
		                        	locations.setPersonnelName(parser.getText());
		                        	break;

		                    }
		                }
		                break;
		        }

		       eventType = parser.next();

		    }

		    return locationsList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

		
	}
	
}

