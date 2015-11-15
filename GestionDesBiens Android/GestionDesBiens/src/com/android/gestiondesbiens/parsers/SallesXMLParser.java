package com.android.gestiondesbiens.parsers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.gestiondesbiens.model.Salles;


public class SallesXMLParser {

	public static List<Salles> parseFeed(String content) {
		
		try {
			
		    boolean inDataItemTag = false;
		    String currentTagName = "";
		    Salles salles = null;
		    List<Salles> sallesList = new ArrayList<>();

		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    XmlPullParser parser = factory.newPullParser();
		    parser.setInput(new StringReader(content));

		    int eventType = parser.getEventType();

		    while (eventType != XmlPullParser.END_DOCUMENT) {

		        switch (eventType) {
		            case XmlPullParser.START_TAG:
		                currentTagName = parser.getName();
		                if (currentTagName.equals("salle")) {
		                    inDataItemTag = true;
		                    salles = new Salles();
		                    sallesList.add(salles);
		                }
		                break;

		            case XmlPullParser.END_TAG:
		                if (parser.getName().equals("salle")) {
		                    inDataItemTag = false;
		                }
		                currentTagName = "";
		                break;

		            case XmlPullParser.TEXT:
		                if (inDataItemTag && salles != null) {
		                    switch (currentTagName) {
		                        case "salleId":
		                            salles.setSalleId(Integer.parseInt(parser.getText()));
		                            break;
		                        case "salleName":
		                        	salles.setSalleName(parser.getText());
		                        	break;

		                    }
		                }
		                break;
		        }

		       eventType = parser.next();

		    }

		    return sallesList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

		
	}
	
}

