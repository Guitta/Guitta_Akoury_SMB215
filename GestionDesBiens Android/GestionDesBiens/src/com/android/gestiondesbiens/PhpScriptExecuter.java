package com.android.gestiondesbiens;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class PhpScriptExecuter {
	
	public static String getDataFromPhpScript(String phpScriptFileName, String strParamsNames[], String strParamsValues[]){
	    try
	    {
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	for(int i = 0; i < strParamsNames.length; i++)
        		nameValuePairs.add(new BasicNameValuePair(strParamsNames[i], strParamsValues[i]));
        	
			 InputStream is;
			 String result, line;
		     HttpClient httpclient = new DefaultHttpClient();
		     HttpPost httppost = new HttpPost(String.format("http://%s/%s", ClsCommon.SERVER_IP, phpScriptFileName));//"http://" + ClsCommon.SERVER_IP + "/" + phpScriptFileName
		     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		     HttpResponse response = httpclient.execute(httppost);
		     HttpEntity entity = response.getEntity();
		     is = entity.getContent();
 	 	     BufferedReader reader = new BufferedReader (new InputStreamReader(is,"iso-8859-1"),8);
 		     StringBuilder sb = new StringBuilder();
 		     while ((line = reader.readLine()) != null)
 		         sb.append(line + "\n");
 	         is.close();
	         result = sb.toString();
	         return result;
	     }
	     catch(Exception e)
	     {
	         e.printStackTrace();
	         return "";
	     }    
	}

	public static String getDataFromPhpScript(String phpScriptFileName){
	    try
	    {
			 InputStream is;
			 String result, line;
		     HttpClient httpclient = new DefaultHttpClient();
		     HttpPost httppost = new HttpPost(String.format("http://%s/%s", ClsCommon.SERVER_IP, phpScriptFileName));//"http://" + ClsCommon.SERVER_IP + "/" + phpScriptFileName
		     HttpResponse response = httpclient.execute(httppost);
		     HttpEntity entity = response.getEntity();
		     is = entity.getContent();
 	 	     BufferedReader reader = new BufferedReader (new InputStreamReader(is,"iso-8859-1"),8);
 		     StringBuilder sb = new StringBuilder();
 		     while ((line = reader.readLine()) != null)
 		         sb.append(line + "\n");
 	         is.close();
	         result = sb.toString();
	         return result;
	     }
	     catch(Exception e)
	     {
	         e.printStackTrace();
	         return "";
	     }    
	}
	
   public static boolean insertUpdateDeleteData(String phpScriptFileName, String strParamsNames[], String strParamsValues[]) {
        try
        {
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	for(int i = 0; i < strParamsNames.length; i++)
        		nameValuePairs.add(new BasicNameValuePair(strParamsNames[i], strParamsValues[i]));
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(String.format("http://%s/%s", ClsCommon.SERVER_IP, phpScriptFileName));//"http://" + ClsCommon.SERVER_IP + "/" + phpScriptFileName
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }   
	   
    }
}
