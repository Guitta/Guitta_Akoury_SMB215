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

import com.android.gestiondesbiens.model.Salles;
import com.android.gestiondesbiens.parsers.CenterXMLParser;
import com.android.gestiondesbiens.parsers.LocationsXMLParser;
import com.android.gestiondesbiens.parsers.SallesXMLParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class SallesActivity extends Activity {
	
	List<Salles> sallesList;
	List<MyTask> tasks;
	EditText txtSalleName;
	TextView tv;
	Button bsave, bnew, bdelete;
	
	
	public void btnNewSalle_Click(View v){
		tv.setVisibility(View.VISIBLE);
		txtSalleName.setVisibility(View.VISIBLE);
		txtSalleName.requestFocus();
		bnew.setEnabled(false);
		bdelete.setEnabled(false);
		
	}
	
	public void btnSaveSalle_Click(View v){
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
	        if (txtSalleName.getText().toString().trim().equalsIgnoreCase("")){
	        	txtSalleName.setError("Salle name is required");
	        } else{
	        	nameValuePairs.add(new BasicNameValuePair("salle_name", txtSalleName.getText().toString()));
	        }
	        try
	        {
	        	
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("http://192.168.1.67:80/Insert_salle.php");
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);

	           runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					tv.setVisibility(View.INVISIBLE);
					txtSalleName.setVisibility(View.INVISIBLE);
					bnew.setEnabled(true);
					bdelete.setEnabled(true);
					// refresh activity 
					MyTask task = new MyTask();
					task.execute("http://192.168.1.67:8080/GestionDesBiens/webresources/model.salle");
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
			
			sallesList = SallesXMLParser.parseFeed(result);
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
		setContentView(R.layout.activity_salles);
		
		this.txtSalleName = (EditText)findViewById(R.id.txtSalleName);
		txtSalleName.setVisibility(View.GONE);
		this.tv=(TextView)findViewById(R.id.textViewSalle);
		tv.setVisibility(View.GONE);
		this.bsave=(Button) findViewById(R.id.btnSaveSalle);
		this.bnew=(Button) findViewById(R.id.btnNewSalle);
		this.bdelete=(Button) findViewById(R.id.btnDeleteSalle);
	
		this.lstHeader = (ListView)findViewById(R.id.lstSallesHeader);
		this.lstReservedWorkDetails = (ListView)findViewById(R.id.lstSallesDetails);
		tasks = new ArrayList<>();
		this.requestData("http://192.168.1.67:8080/GestionDesBiens/webresources/model.salle");
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
            
                this.mapReservedWorkHeader.put("SalleName", "Salle name");
            
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
            
            for(int i = 0; i < this.sallesList.size(); i++){
             this.mapReservedWorkDetails = new HashMap<String, String>();
             
                 this.mapReservedWorkDetails.put("LocationID", "");
                 this.mapReservedWorkDetails.put("CenterName", this.sallesList.get(i).getSalleName());
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
		getMenuInflater().inflate(R.menu.salles, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_get_centers) {
			Intent I = new Intent(getApplicationContext(), CenterActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_type) {
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
