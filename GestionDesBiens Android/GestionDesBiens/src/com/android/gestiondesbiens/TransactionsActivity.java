package com.android.gestiondesbiens;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.android.gestiondesbiens.model.Transactions;
import com.android.gestiondesbiens.parsers.TransactionsXMLParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class TransactionsActivity extends Activity {
	
	List<Transactions> transactionsList;
//	List<MyTask> tasks;
	
/*	private class MyTask extends AsyncTask<String, String, String> {
		
		 

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
			
	
			transactionsList = TransactionsXMLParser.parseFeed(result);
	
			LoadGridDetails();
			}

			
		@Override
		protected void onProgressUpdate(String... values) {
//			updateDisplay(values[0]);
		}
		
	}*/
	
	ArrayList<HashMap<String, String>> arrHeader, arrDetails;
	HashMap<String, String> mapReservedWorkHeader, mapReservedWorkDetails;
	ListAdapter adHeader, adDetails;
	
	ListView lstHeader, lstReservedWorkDetails;
	
	int intTransactionID = 0;

	
	public void btnNewTransaction_Click(View v){
		
	}
	
	public void btnSaveTransaction_Click(View v){
		 new Thread(new Runnable(
				 ) {
			
			@Override
			public void run() {
		        try
		        {
		        	 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		            nameValuePairs.add(new BasicNameValuePair("item_id","1"));
		             nameValuePairs.add(new BasicNameValuePair("username", ClsUser.CONNECTED_USER_NAME));
		             nameValuePairs.add(new BasicNameValuePair("location_id_src", "2"));
		             nameValuePairs.add(new BasicNameValuePair("location_id_dest", "1"));
		             nameValuePairs.add(new BasicNameValuePair("transport_id", "1"));
		        	
		        	// nameValuePairs.add(new BasicNameValuePair("deviceId","1"));
		             //nameValuePairs.add(new BasicNameValuePair("onlineUserId", String.valueOf(2)));
		             
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpPost httppost = new HttpPost("http://192.168.1.67:80/Insert_Transaction.php");
		            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		            HttpResponse response = httpclient.execute(httppost);
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
			}
		}).start();
	}
	
	public void btnDeleteTransaction_Click(View v){
		
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transactions);

		this.lstHeader = (ListView)findViewById(R.id.lstTransactionHeader);
		this.lstReservedWorkDetails = (ListView)findViewById(R.id.lstTransactionDetails);
		
		this.LoadGridHeader();
		
//		tasks = new ArrayList<>();
//		this.requestData("http://192.168.1.67:8080/GestionDesBiens/webresources/model.transaction");
		
		this.lstReservedWorkDetails.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
		
		new Thread() {
		    public void run(){
		        //phpRequest();
		    	insertDeviceId();
		    }
		}.start();
	}
	
	/*private void requestData(String uri) {
		MyTask task = new MyTask();
		task.execute(uri);
	}*/
	
	  public boolean insertDeviceId()
	    {
	          InputStream is=null;
	          String result=null;
	          String line=null;
	          String code;
	          boolean res = false;
	      /*  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("deviceId",deviceId));
	        nameValuePairs.add(new BasicNameValuePair("onlineUserId", String.valueOf(onlineUserId)));*/
	       
	        try
	        {
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("http://192.168.1.67:80/Select_Transaction.php");
	           // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
	            Log.e("pass 1", "connection success ");
	        }
	        catch(Exception e)
	        {
	            Log.e("Fail 1", e.toString());
	        }        
	       
	        
	        try
	        {
	            BufferedReader reader = new BufferedReader (new InputStreamReader(is,"iso-8859-1"),8);
	            StringBuilder sb = new StringBuilder();
	            while ((line = reader.readLine()) != null)
	            {
	                sb.append(line + "\n");
	            }
	            is.close();
	            result = sb.toString();
	            LoadGridDetails(result);
	        Log.e("pass 2", "connection success ");
	        }
	        catch(Exception e)
	        {
	            Log.e("Fail 2", e.toString());
	        }    
	      
	        return res;
	    }



	
	/*public void phpRequest()
    {

         try
         {
             HttpClient client = new DefaultHttpClient();
             final String url = "http://192.168.1.67:80/Select_Transaction.php";
             client.execute(new HttpGet(url));
         }
         catch(Exception e)
         {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show(); 
         }


    }*/

	
    void LoadGridHeader(){
        try{
        	this.adHeader = null;
        	this.lstHeader.setAdapter(this.adHeader);
            this.arrHeader = new ArrayList<HashMap<String, String>>();
            this.mapReservedWorkHeader = new HashMap<String, String>();
         
                this.mapReservedWorkHeader.put("ItemCode", "Item ID");
                this.mapReservedWorkHeader.put("ItemName", "User Name");
                this.mapReservedWorkHeader.put("ItemSpecification", "Location Id Source");
                this.mapReservedWorkHeader.put("TypeName", "Location Id Destination");
                this.mapReservedWorkHeader.put("ItemDateCreated", "Transport Id");
                this.mapReservedWorkHeader.put("CenterName", "Status");
                this.mapReservedWorkHeader.put("SalleName", "Date Transaction");
                
                
          
            this.arrHeader.add(this.mapReservedWorkHeader);
            this.adHeader = new SimpleAdapter(this, arrHeader, R.layout.gridb_template, new String[] {"ItemCode", "ItemName", "ItemSpecification", "TypeName", "ItemDateCreated", "CenterName", "SalleName", "PersonnelName"}, new int[] {R.id.labItemCode, R.id.labItemName, R.id.labItemSpecification, R.id.labTypeName, R.id.labItemDateCreated, R.id.labCenterName, R.id.labSalleName, R.id.labPersonnelName});
            this.lstHeader.setAdapter(this.adHeader);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void LoadGridDetails(String strResult){
        try{
        	
        	this.adDetails = null;
        	
        	runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					lstReservedWorkDetails.setAdapter(adDetails);
				}
			});
        	
            this.arrDetails = new ArrayList<HashMap<String, String>>();
            
            
            String rows[] = strResult.split(";");
            String col[];
            for(int i = 0; i < rows.length - 1; i++){
            	 col = rows[i].split(",");
            	 this.mapReservedWorkDetails = new HashMap<String, String>();
                 
                 this.mapReservedWorkDetails.put("ItemCode", col[1]);
                 this.mapReservedWorkDetails.put("ItemName", col[2]);
                 this.mapReservedWorkDetails.put("ItemSpecification", col[3]);
                 this.mapReservedWorkDetails.put("TypeName", col[4]);
                 this.mapReservedWorkDetails.put("ItemDateCreated", col[5]);
                 this.mapReservedWorkDetails.put("CenterName", col[6]);
                 this.mapReservedWorkDetails.put("SalleName", col[7]);
                 
             
      
             this.arrDetails.add(this.mapReservedWorkDetails);
            }
            
          
            this.adDetails = new SimpleAdapter(this, arrDetails, R.layout.gridb_template, new String[] {"ItemCode", "ItemName", "ItemSpecification", "TypeName", "ItemDateCreated", "CenterName", "SalleName", "PersonnelName"}, new int[] {R.id.labItemCode, R.id.labItemName, R.id.labItemSpecification, R.id.labTypeName, R.id.labItemDateCreated, R.id.labCenterName, R.id.labSalleName, R.id.labPersonnelName});
			runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								lstReservedWorkDetails.setAdapter(adDetails);
							}
						});
    }
    catch(Exception e){
        e.printStackTrace();
    }
    }



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transactions, menu);
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
		}else if (id == R.id.action_get_salles) {
			Intent I = new Intent(getApplicationContext(), SallesActivity.class);
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
