package com.android.gestiondesbiens.parsers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.gestiondesbiens.model.Groups;


public class GroupsXMLParser {
	
public static List<Groups> parseFeed(String content) {
		
		try {
			
		    boolean inDataItemTag = false;
		    String currentTagName = "";
		    Groups groups = null;
		    List<Groups> groupsList = new ArrayList<>();

		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    XmlPullParser parser = factory.newPullParser();
		    parser.setInput(new StringReader(content));

		    int eventType = parser.getEventType();

		    while (eventType != XmlPullParser.END_DOCUMENT) {

		        switch (eventType) {
		            case XmlPullParser.START_TAG:
		                currentTagName = parser.getName();
		                if (currentTagName.equals("groupe")) {
		                    inDataItemTag = true;
		                    groups = new Groups();
		                    groupsList.add(groups);
		                }
		                break;

		            case XmlPullParser.END_TAG:
		                if (parser.getName().equals("groupe")) {
		                    inDataItemTag = false;
		                }
		                currentTagName = "";
		                break;

		            case XmlPullParser.TEXT:
		                if (inDataItemTag && groups != null) {
		                    switch (currentTagName) {
			                    case "Id":
		                            groups.setId(Integer.parseInt(parser.getText()));
		                            break;
		                        case "username":
		                            groups.setUsername(parser.getText());
		                            break;
		                        case "groupname":
		                        	groups.setGroupname(parser.getText());
		                        	break;
		                        case "description":
		                        	groups.setDescription(parser.getText());
		                        	break;

		                    }
		                }
		                break;
		        }

		       eventType = parser.next();

		    }

		    return groupsList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

		
	}
	

}
