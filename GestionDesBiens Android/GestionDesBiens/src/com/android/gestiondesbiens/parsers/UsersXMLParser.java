package com.android.gestiondesbiens.parsers;

import java.io.StringReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.gestiondesbiens.model.Users;


public class UsersXMLParser {

	public static List<Users> parseFeed(String content) {
		
		try {
			
		    boolean inDataItemTag = false;
		    String currentTagName = "";
		    Users users = null;
		    List<Users> usersList = new ArrayList<>();

		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    XmlPullParser parser = factory.newPullParser();
		    parser.setInput(new StringReader(content));

		    int eventType = parser.getEventType();

		    while (eventType != XmlPullParser.END_DOCUMENT) {

		        switch (eventType) {
		            case XmlPullParser.START_TAG:
		                currentTagName = parser.getName();
		                if (currentTagName.equals("users")) {
		                    inDataItemTag = true;
		                    users = new Users();
		                    usersList.add(users);
		                }
		                break;

		            case XmlPullParser.END_TAG:
		                if (parser.getName().equals("users")) {
		                    inDataItemTag = false;
		                }
		                currentTagName = "";
		                break;

		            case XmlPullParser.TEXT:
		                if (inDataItemTag && users != null) {
		                    switch (currentTagName) {
		                        case "username":
		                            users.setUsername(parser.getText());
		                            break;
		                        case "password":
		                        	users.setPassword(parser.getText());
		                        	break;
		                        case "groupname":
		                        	users.setGroupname(parser.getText());
		                        	break;
		                        case "name":
		                        	users.setName(parser.getText());
		                        	break;
		                        case "registerDt":
		                        	users.setRegisterDt(parser.getText());
		                        	break;

		                    }
		                }
		                break;
		        }

		       eventType = parser.next();

		    }

		    return usersList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

		
	}
	
}

