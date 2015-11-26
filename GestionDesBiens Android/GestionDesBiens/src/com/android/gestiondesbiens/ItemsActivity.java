package com.android.gestiondesbiens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.gestiondesbiens.model.Items;
import com.android.gestiondesbiens.parsers.ItemsXMLParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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


public class ItemsActivity extends Activity {
	
	List<Items> itemsList;
	List<MyTask> tasks;
	
	public static boolean blnReloadGrid = false;
	
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
			
	
			itemsList = ItemsXMLParser.parseFeed(result);
	
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

	public void btnNewItem_Click(View v){
		Intent I = new Intent(getApplicationContext(), EditItemDetails.class);
		I.putExtra("item_id", "0");
		startActivity(I);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);

		this.lstHeader = (ListView)findViewById(R.id.lstItemsHeader);
		this.lstReservedWorkDetails = (ListView)findViewById(R.id.lstItemsDetails);
		tasks = new ArrayList<>();
		this.RequestDataItems();
		
		this.LoadGridHeader();
		
		this.lstReservedWorkDetails.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent I = new Intent(getApplicationContext(), EditItemDetails.class);
				I.putExtra("item_id", arrDetails.get(position).get("item_id"));
				startActivity(I);
			}
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
		    	while(true){
		    		if(blnReloadGrid){
		    			blnReloadGrid = false;
		    			RequestDataItems();
		    		}
		    	}
			}
		}).start();
	}
	
	void RequestDataItems(){
		this.requestData("http://" + ClsCommon.SERVER_IP.split(":")[0] + ":8080/GestionDesBiens/webresources/model.item");
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
         
                this.mapReservedWorkHeader.put("ItemCode", "Item Code");
                this.mapReservedWorkHeader.put("ItemName", "Item Name");
                this.mapReservedWorkHeader.put("ItemSpecification", "Item Specification");
                this.mapReservedWorkHeader.put("TypeName", "Type");
                this.mapReservedWorkHeader.put("ItemDateCreated", "Item Date Created");
                this.mapReservedWorkHeader.put("CenterName", "Center Name");
                this.mapReservedWorkHeader.put("SalleName", "Salle Name");
                this.mapReservedWorkHeader.put("PersonnelName", "Personnel Name");
                
          
            this.arrHeader.add(this.mapReservedWorkHeader);
            this.adHeader = new SimpleAdapter(this, arrHeader, R.layout.gridb_template, new String[] {"ItemCode", "ItemName", "ItemSpecification", "TypeName", "ItemDateCreated", "CenterName", "SalleName", "PersonnelName"}, new int[] {R.id.labItemCode, R.id.labItemName, R.id.labItemSpecification, R.id.labTypeName, R.id.labItemDateCreated, R.id.labCenterName, R.id.labSalleName, R.id.labPersonnelName});
            this.lstHeader.setAdapter(this.adHeader);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void LoadGridDetails(){
        try{
        	this.adDetails = null;
        	this.lstReservedWorkDetails.setAdapter(this.adDetails);
        	
            this.arrDetails = new ArrayList<HashMap<String, String>>();
            
            for(int i = 0; i < this.itemsList.size(); i++){
             this.mapReservedWorkDetails = new HashMap<String, String>();
             
             this.mapReservedWorkDetails.put("item_id", Integer.toString(this.itemsList.get(i).getItemId()));
             this.mapReservedWorkDetails.put("ItemCode", this.itemsList.get(i).getItemCode());
             this.mapReservedWorkDetails.put("ItemName", this.itemsList.get(i).getItemName());
             this.mapReservedWorkDetails.put("ItemSpecification", this.itemsList.get(i).getItemSpecification());
             this.mapReservedWorkDetails.put("TypeName", this.itemsList.get(i).getTypeName());
             this.mapReservedWorkDetails.put("ItemDateCreated", this.itemsList.get(i).getItemDateCreated());
             this.mapReservedWorkDetails.put("CenterName", this.itemsList.get(i).getCenterName());
             this.mapReservedWorkDetails.put("SalleName", this.itemsList.get(i).getSalleName());
             this.mapReservedWorkDetails.put("PersonnelName", this.itemsList.get(i).getPersonnelName());
             
      
             this.arrDetails.add(this.mapReservedWorkDetails);
            }
            this.adDetails = new SimpleAdapter(this, arrDetails, R.layout.gridb_template, new String[] {"ItemCode", "ItemName", "ItemSpecification", "TypeName", "ItemDateCreated", "CenterName", "SalleName", "PersonnelName"}, new int[] {R.id.labItemCode, R.id.labItemName, R.id.labItemSpecification, R.id.labTypeName, R.id.labItemDateCreated, R.id.labCenterName, R.id.labSalleName, R.id.labPersonnelName});
            this.lstReservedWorkDetails.setAdapter(this.adDetails);
    }
    catch(Exception e){
        e.printStackTrace();
    }
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.items, menu);
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
		}else if (id == R.id.action_get_salles) {
			Intent I = new Intent(getApplicationContext(), SallesActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_transations) {
			Intent I = new Intent(getApplicationContext(), UserTransactionsActivity.class);
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
