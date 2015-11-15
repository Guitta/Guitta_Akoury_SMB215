package com.android.gestiondesbiens;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.android.gestiondesbiens.model.Center;

import com.android.gestiondesbiens.parsers.CenterXMLParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class CenterActivity extends Activity {
	
	
	List<Center> centerList;
	List<MyTask> tasks;
	EditText txtCenterName;
	TextView tv;
	Button bsave, bnew, bdelete;
	
	
	public void btnNewCenter_Click(View v){
		tv.setVisibility(View.VISIBLE);
		txtCenterName.setVisibility(View.VISIBLE);
		txtCenterName.requestFocus();

		
	}
	
	public void btnSaveCenter_Click(View v){
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
	        if (txtCenterName.getText().toString().trim().equalsIgnoreCase("")){
	        	txtCenterName.setError("Center name is required");
	        } else{
	        	nameValuePairs.add(new BasicNameValuePair("center_name", txtCenterName.getText().toString()));
	        }
	        try
	        {
	        	
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("http://192.168.1.67:80/Insert_Center.php");
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);

	           runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					tv.setVisibility(View.GONE);
					txtCenterName.setVisibility(View.GONE);

					// refresh activity 
					MyTask task = new MyTask();
					task.execute("http://192.168.1.67:8080/GestionDesBiens/webresources/model.center");
					 Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
				}
			});
	            Log.e("pass 1", "connection success ");
	        }
	        catch(Exception e)
	        {
	            Log.e("Fail 1", e.toString());
	        }   
	    }
	   
	   
	
	private class MyTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
//			updateDisplay("Starting task");
			
			if (tasks.size() == 0) {
				//pb.setVisibility(View.VISIBLE);
			}
			tasks.add(this);
		}
		
		@Override
		protected String doInBackground(String... params) {
			Log.w("params:", params[0]);
			String content = HttpManager.getData(params[0]);
			HttpManager hm = new HttpManager();
			String data = hm.getData(params[0]);
			//HttpManager
			System.out.println("RESULT === "+data);
			return data;
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			tasks.remove(this);
			if (tasks.size() == 0) {
				//pb.setVisibility(View.INVISIBLE);
			}
			
			if (result == null) {
				Toast.makeText(getApplicationContext(), "Web service not available", Toast.LENGTH_LONG).show();
				return;
			}
			
			centerList = CenterXMLParser.parseFeed(result);
			LoadGridDetails();
			}

			
		@Override
		protected void onProgressUpdate(String... values) {
//			updateDisplay(values[0]);
		}
		
	}
	
	
	ArrayList<HashMap<String, String>> arrHeader, arrDetails;
	HashMap<String, String> mapReservedWorkHeader, mapReservedWorkDetails;
	ListAdapter adHeader, adDetails;
	
	ListView lstHeader, lstReservedWorkDetails;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center);
		this.txtCenterName = (EditText)findViewById(R.id.txtCenterName);
		txtCenterName.setVisibility(View.GONE);
		this.tv=(TextView)findViewById(R.id.textView1);
		tv.setVisibility(View.GONE);
		this.bsave=(Button) findViewById(R.id.btnSaveCenter);
		
		this.bnew=(Button) findViewById(R.id.btnNewCenter);
		
		this.bdelete=(Button) findViewById(R.id.btnDeleteCenter);
		
		this.lstHeader = (ListView)findViewById(R.id.lstCenterHeader);
		this.lstReservedWorkDetails = (ListView)findViewById(R.id.lstCenterDetails);
		tasks = new ArrayList<>();
		this.requestData("http://192.168.1.67:8080/GestionDesBiens/webresources/model.center");
	}
	
	private void requestData(String uri) {
		MyTask task = new MyTask();
		task.execute(uri);
	}

	
    void LoadGridHeader(){
    
            try{
            	this.adHeader = null;
            	this.lstHeader.setAdapter(this.adHeader);
                this.arrHeader = new ArrayList<HashMap<String, String>>();
                this.mapReservedWorkHeader = new HashMap<String, String>();
            
                    this.mapReservedWorkHeader.put("CenterName", "Center name");
                
                this.arrHeader.add(this.mapReservedWorkHeader);
                this.adHeader = new SimpleAdapter(this, arrHeader, R.layout.grid_template, new String[] {"LocationID", "CenterName", "SalleName", "PersonnelName"}, new int[] {R.id.labLocationID, R.id.labCenterName, R.id.labSalleName, R.id.labPersonnelName});
                this.lstHeader.setAdapter(this.adHeader);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    
    void LoadGridDetails(){
        try{
        	this.LoadGridHeader();
        	this.adDetails = null;
        	this.lstReservedWorkDetails.setAdapter(this.adDetails);
        	
            this.arrDetails = new ArrayList<HashMap<String, String>>();
            
            for(int i = 0; i < this.centerList.size(); i++){
             this.mapReservedWorkDetails = new HashMap<String, String>();
             
                   
                 this.mapReservedWorkDetails.put("LocationID", "");
                 this.mapReservedWorkDetails.put("CenterName", this.centerList.get(i).getCenterName());
                 this.mapReservedWorkDetails.put("SalleName", "");
                 this.mapReservedWorkDetails.put("PersonnelName", "");
             

             this.arrDetails.add(this.mapReservedWorkDetails);
            }
            this.adDetails = new SimpleAdapter(this, arrDetails, R.layout.grid_template, new String[] {"LocationID", "CenterName", "SalleName", "PersonnelName"}, new int[] {R.id.labLocationID, R.id.labCenterName, R.id.labSalleName, R.id.labPersonnelName});
            this.lstReservedWorkDetails.setAdapter(this.adDetails);
    }
    catch(Exception e){
        e.printStackTrace();
    }
    }

    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.center, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_get_type) {
			Intent I = new Intent(getApplicationContext(), TypeActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_personnel) {
			Intent I = new Intent(getApplicationContext(), PersonnelActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_locations) {
			Intent I = new Intent(getApplicationContext(), LocationsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_users) {
			Intent I = new Intent(getApplicationContext(), UsersActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_groups) {
			Intent I = new Intent(getApplicationContext(), GroupsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_items) {
			Intent I = new Intent(getApplicationContext(), ItemsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_salles) {
			Intent I = new Intent(getApplicationContext(), SallesActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_transations) {
			Intent I = new Intent(getApplicationContext(), TransactionsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_transport) {
			Intent I = new Intent(getApplicationContext(), TransportActivity.class);
			startActivity(I);		
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
