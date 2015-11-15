package com.android.gestiondesbiens;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

public class PhpScriptTest extends Activity {
	
	public void phptest(View v){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				insertDeviceId();
			}
		}).start();
	}
	
    public void insertDeviceId()
    {
          InputStream is=null;
          String result=null;
          String line=null;
          int code;
          boolean res = false;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("deviceId","1"));
        nameValuePairs.add(new BasicNameValuePair("onlineUserId", String.valueOf(2)));
       
        try
        {
        	
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.1.67:80/insertTest.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
          //  HttpEntity entity = response.getEntity();
           
           // is = entity.getContent();
           runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
			}
		});
            Log.e("pass 1", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
        }        
       
//        try
//        {
//            BufferedReader reader = new BufferedReader
//            (new InputStreamReader(is,"iso-8859-1"),8);
//            StringBuilder sb = new StringBuilder();
//            while ((line = reader.readLine()) != null)
//            {
//                sb.append(line + "\n");
//            }
//            is.close();
//            result = sb.toString();
//        Log.e("pass 2", "connection success ");
//        }
//        catch(Exception e)
//        {
//            Log.e("Fail 2", e.toString());
//        }    
      
        //return res;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_php_script_test);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.php_script_test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
