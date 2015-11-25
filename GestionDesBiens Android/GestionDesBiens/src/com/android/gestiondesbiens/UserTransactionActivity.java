package com.android.gestiondesbiens;

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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.gestiondesbiens.model.Transactions;


public class UserTransactionActivity extends Activity {
	
	List<Transactions> transactionsList;

	
	ArrayList<HashMap<String, String>> arrHeader, arrDetails;
	HashMap<String, String> mapReservedWorkHeader, mapReservedWorkDetails;
	ListAdapter adHeader, adDetails;
	
	ListView lstHeader, lstReservedWorkDetails;
	
	int intTransactionID = 0;

	
	public void btnNewTransaction_Click(View v){
		Intent I = new Intent(this, EditTransactionDetails.class);
		I.putExtra("transaction_id", "0");
		startActivity(I);
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
				Intent I = new Intent(getApplicationContext(), EditTransactionDetails.class);
				I.putExtra("transaction_id", arrDetails.get(position).get("transaction_id"));
				I.putExtra("item_id", arrDetails.get(position).get("ItemCode"));
				I.putExtra("username", arrDetails.get(position).get("ItemName"));
				I.putExtra("location_id_src", arrDetails.get(position).get("ItemSpecification"));
				I.putExtra("location_id_dest", arrDetails.get(position).get("TypeName"));
				I.putExtra("transport_id", arrDetails.get(position).get("ItemDateCreated"));
				I.putExtra("status", arrDetails.get(position).get("CenterName"));
				I.putExtra("transaction_date", arrDetails.get(position).get("SalleName"));
				
				startActivity(I);
			}
		});
		new Thread() {
		    public void run(){
		    	LoadGridDetails();
		    	while(true){
		    		if(blnReloadGrid){
		    			LoadGridDetails();
				    	blnReloadGrid = false;
		    		}
		    	}
		    	
		    }
		}.start();

	}
	public static boolean blnReloadGrid = false;
	
	  public  void LoadGridDetails()
	    {
		  String result = PhpScriptExecuter.getDataFromPhpScript("Select_Transaction.php");
          LoadGridDetails(result);
	    }


	
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
        	
        	adDetails = null;
        	
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
                 
            	 this.mapReservedWorkDetails.put("transaction_id", col[0]);
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
		getMenuInflater().inflate(R.menu.user_transaction, menu);
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
