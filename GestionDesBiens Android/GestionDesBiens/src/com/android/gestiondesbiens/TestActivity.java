package com.android.gestiondesbiens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.gestiondesbiens.model.Center;
import com.android.gestiondesbiens.model.Items;
import com.android.gestiondesbiens.model.Locations;
import com.android.gestiondesbiens.model.Personnel;
import com.android.gestiondesbiens.model.Salles;
import com.android.gestiondesbiens.model.Transactions;
import com.android.gestiondesbiens.model.Transport;
import com.android.gestiondesbiens.model.Type;
import com.android.gestiondesbiens.parsers.CenterXMLParser;
import com.android.gestiondesbiens.parsers.LocationsXMLParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class TestActivity extends Activity {
	
	List<Locations> locationsList;
	List<Center> centerList;
	List<Salles> sallesList;
	List<Type> typeList;
	List<Personnel> personnelList;
	List<Transport> transportList;
	List<Items> itemsList;
	List<Transactions> transactionsList;
	List<MyTask> tasks;
	
	boolean blnIsLocation;
	
	
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
			String content = HttpManager.getData(params[0], "feeduser", "feedpassword");
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
			
			if(blnIsLocation)
				locationsList = LocationsXMLParser.parseFeed(result);
			else
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
		setContentView(R.layout.activity_test);
		this.lstHeader = (ListView)findViewById(R.id.lstReservedWorkHeader);
		this.lstReservedWorkDetails = (ListView)findViewById(R.id.lstReservedWorkDetails);
		tasks = new ArrayList<>();
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
            if(this.blnIsLocation){
                this.mapReservedWorkHeader.put("LocationID", "Location ID");
                this.mapReservedWorkHeader.put("CenterName", "Center name");
                this.mapReservedWorkHeader.put("SalleName", "Salle name");
                this.mapReservedWorkHeader.put("PersonnelName", "Personnel name");
            }
            else{
                this.mapReservedWorkHeader.put("CenterName", "Center name");
            }
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
            
            for(int i = 0; i < (this.blnIsLocation ? this.locationsList.size() : this.centerList.size()); i++){
             this.mapReservedWorkDetails = new HashMap<String, String>();
             if(this.blnIsLocation){
                 this.mapReservedWorkDetails.put("LocationID", Integer.toString(this.locationsList.get(i).getLocationId()));
                 this.mapReservedWorkDetails.put("CenterName", this.locationsList.get(i).getCenterName());
                 this.mapReservedWorkDetails.put("SalleName", this.locationsList.get(i).getSalleName());
                 this.mapReservedWorkDetails.put("PersonnelName", this.locationsList.get(i).getPersonnelName());
             }
             else{
                 this.mapReservedWorkDetails.put("LocationID", "");
                 this.mapReservedWorkDetails.put("CenterName", this.centerList.get(i).getCenterName());
                 this.mapReservedWorkDetails.put("SalleName", "");
                 this.mapReservedWorkDetails.put("PersonnelName", "");
             }
//            else {
//	             this.mapReservedWorkDetails.put("LocationID", "");
//	             this.mapReservedWorkDetails.put("CenterName", this.sallesList.get(i).getSalleName());
//	             this.mapReservedWorkDetails.put("SalleName", "");
//	             this.mapReservedWorkDetails.put("PersonnelName", "");
//            }
//           else {
//             this.mapReservedWorkDetails.put("LocationID", "");
//             this.mapReservedWorkDetails.put("CenterName", this.typeList.get(i).getTypeName());
//             this.mapReservedWorkDetails.put("SalleName", "");
//             this.mapReservedWorkDetails.put("PersonnelName", "");
//        }
//           else {
//           this.mapReservedWorkDetails.put("LocationID", "");
//           this.mapReservedWorkDetails.put("CenterName", this.personnelList.get(i).getPersonnelName());
//           this.mapReservedWorkDetails.put("SalleName", "");
//           this.mapReservedWorkDetails.put("PersonnelName", "");
//      }
//           else {
//           this.mapReservedWorkDetails.put("LocationID", "");
//           this.mapReservedWorkDetails.put("CenterName", this.transportList.get(i).getPersonnelName());
//           this.mapReservedWorkDetails.put("TransportDate", this.transportList.get(i).getTransportDate());
//           this.mapReservedWorkDetails.put("PersonnelName", "");
//      }
//           else {
//           this.mapReservedWorkDetails.put("DateCreated", this.itemsList.get(i).getItemDateCreated());
//           this.mapReservedWorkDetails.put("ItemName", this.itemsList.get(i).getItemName());
//           this.mapReservedWorkDetails.put("ItemLocation", this.itemsList.get(i).getCenterName()+" "+this.itemsList.get(i).getSalleName());
//           this.mapReservedWorkDetails.put("PersonnelName", this.itemsList.get(i).getPersonnelName());
//      }
//           else {
//           this.mapReservedWorkDetails.put("DateCreated", this.itemsTransactions.get(i).getTransactionDateCreated());
//           this.mapReservedWorkDetails.put("ItemName", this.itemsTransactions.get(i).getItemName());
//           this.mapReservedWorkDetails.put("ItemLocationSource", this.itemsTransactions.get(i).getCenterSrcName()+" "+this.getSalleSrcName.get(i).getSalleName());
//           this.mapReservedWorkDetails.put("PersonnelNameSource", this.itemsList.get(i).getPersonnelSrcName());
//           this.mapReservedWorkDetails.put("ItemName", this.itemsTransactions.get(i).getItemName());
//           this.mapReservedWorkDetails.put("ItemLocationDestination", this.itemsTransactions.get(i).getCenterDesName()+" "+this.getSalleDesName.get(i).getSalleName());
//           this.mapReservedWorkDetails.put("PersonnelNameDestination", this.itemsList.get(i).getPersonnelDesName());
//           this.mapReservedWorkDetails.put("Status", this.itemsTransactions.get(i).getStatus());
//           this.mapReservedWorkDetails.put("UserName", this.getUserName.get(i).getStatus());
//           }
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
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_get_centers) {
			this.blnIsLocation = false;
			this.requestData("http://192.168.1.67:8080/GestionDesBiens/webresources/model.center");
			return true;
		}
		else if (id == R.id.action_get_locations){
			this.blnIsLocation = true;
			this.requestData("http://192.168.1.67:8080/GestionDesBiens/webresources/model.location");
			return true;
		}
		else if(id == R.id.action_get_items){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//new MySQLConnector().testDB();
				}
			}).start();
		}
		return super.onOptionsItemSelected(item);
	}
}
