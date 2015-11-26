package com.android.gestiondesbiens;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class EditTransactionDetails extends Activity {
	
	int transaction_id;
	Spinner spinItems, spinLocationSourceEdit, spinLocationDestinationEdit, spinTransportEdit, spinStatus;
	
	HashMap<String, String> mapItems, mapLocationSource, mapLocationDestination, mapTransport;
	SimpleAdapter adItems, adLocationSource, adLocationDestination, adTransport;
	ArrayList<HashMap<String, String>> arrItems, arrLocationSource, arrLocationDestination, arrTransport;
	
	public void btnSaveTransaction_Click(View v){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String strPhpScriptFileName;
				String strParamsNames[], strParamsValues[];
				if(transaction_id == 0){
					//insert
					strPhpScriptFileName = "Insert_Transaction.php";
					strParamsNames = new String[]{"item_id", "username", "location_id_src", "location_id_dest", "transport_id", "status"};
					strParamsValues = new String[]{arrItems.get(spinItems.getSelectedItemPosition()).get("item_id"),
												   ClsUser.CONNECTED_USER_NAME, 
												   arrLocationSource.get(spinLocationSourceEdit.getSelectedItemPosition()).get("location_id"),
												   arrLocationDestination.get(spinLocationDestinationEdit.getSelectedItemPosition()).get("location_id"),
												   arrTransport.get(spinTransportEdit.getSelectedItemPosition()).get("transport_id"),
												   spinStatus.getSelectedItem().toString()};
				}
				else{
					//update
					strPhpScriptFileName = "Update_Transaction.php";
					strParamsNames = new String[]{"transaction_id", "item_id", "location_id_src", "location_id_dest", "transport_id", "status"};
					strParamsValues = new String[]{Integer.toString(transaction_id),
												   arrItems.get(spinItems.getSelectedItemPosition()).get("item_id"),
												   arrLocationSource.get(spinLocationSourceEdit.getSelectedItemPosition()).get("location_id"),
												   arrLocationDestination.get(spinLocationDestinationEdit.getSelectedItemPosition()).get("location_id"),
												   arrTransport.get(spinTransportEdit.getSelectedItemPosition()).get("transport_id"),
												   spinStatus.getSelectedItem().toString()};
				}
				if(PhpScriptExecuter.insertUpdateDeleteData(strPhpScriptFileName, strParamsNames, strParamsValues)){
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							if(transaction_id == 0)
								Toast.makeText(getApplicationContext(), "A new transaction has been added.", Toast.LENGTH_LONG).show();
							else
								Toast.makeText(getApplicationContext(), "The selected transaction has been updated.", Toast.LENGTH_LONG).show();
							UserTransactionsActivity.blnReloadGrid = true;

							finish();
						}
					});
				}
			}
		}).start();
	}
	
	public void btnDeleteTransaction_Click(View v){
		this.ConfirmDeleteTransaction();
	}
	
	private void ConfirmDeleteTransaction() {
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    	        switch (which){
    	        case DialogInterface.BUTTON_POSITIVE:
    	        	deleteTransaction();
    	            break;

    	        case DialogInterface.BUTTON_NEGATIVE:
    	            //No button clicked
    	            break;
    	        }
    	    }
    	};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure  do you want to delete the selected transaction?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
	}
	void deleteTransaction(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					if(PhpScriptExecuter.insertUpdateDeleteData("Delete_Transaction.php", new String[]{"transaction_id"}, new String[]{Integer.toString(transaction_id)})){
		        		runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), "Transaction deleted.", Toast.LENGTH_LONG).show();
								UserTransactionsActivity.blnReloadGrid = true;
							}
						});
		        		finish();
		        	}
				}
				catch(final Exception e)
				{
					System.out.println(e.getMessage());
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
				}
			}
		}).start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_transaction_details);
		if(getIntent().getStringExtra("transaction_id") == null){
			finish();
			return;
		}
		this.spinItems = (Spinner)findViewById(R.id.spinItemEdit);
		this.spinLocationSourceEdit = (Spinner)findViewById(R.id.spinLocationSourceEdit);
		this.spinLocationDestinationEdit = (Spinner)findViewById(R.id.spinLocationDestinationEdit);
		this.spinTransportEdit = (Spinner)findViewById(R.id.spinTransportEdit);
		this.spinStatus = (Spinner)findViewById(R.id.spinStatus);
		this.transaction_id = Integer.parseInt(getIntent().getStringExtra("transaction_id"));
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				LoadItems();
				LoadLocations();
				LoadTransport();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if(transaction_id > 0) LoadSelectedValues();
					}
				});
			}
		}).start();
	}
	
	void LoadSelectedValues(){
		for(int i = 0; i < this.arrItems.size(); i++)
			if(this.arrItems.get(i).get("item_id").equals(getIntent().getStringExtra("item_id"))){
				this.spinItems.setSelection(i);
				break;
			}
		
		for(int i = 0; i < this.arrLocationSource.size(); i++)
			if(this.arrLocationSource.get(i).get("location_id").equals(getIntent().getStringExtra("location_id_src"))){
				this.spinLocationSourceEdit.setSelection(i);
				break;
			}
	
		for(int i = 0; i < this.arrLocationDestination.size(); i++)
			if(this.arrLocationDestination.get(i).get("location_id").equals(getIntent().getStringExtra("location_id_dest"))){
				this.spinLocationDestinationEdit.setSelection(i);
				break;		
			}
		
		for(int i = 0; i < this.arrTransport.size(); i++)
			if(this.arrTransport.get(i).get("transport_id").equals(getIntent().getStringExtra("transport_id"))){
				this.spinTransportEdit.setSelection(i);
				break;
			}
		
		if(getIntent().getStringExtra("status").equals("Delivered"))
			this.spinStatus.setSelection(0);
		else if(getIntent().getStringExtra("status").equals("Not Delivered"))
			this.spinStatus.setSelection(1);
	}
	
	void LoadTransport(){
		try{
			String sqlData = PhpScriptExecuter.getDataFromPhpScript("Select_Transports.php");
			if(sqlData.equals("")) return;
			String rows[], cols[];
			rows = sqlData.split(";");
			this.arrTransport = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < rows.length; i++){
				if(!rows[i].trim().equals("")){
					cols = rows[i].split(",");
					this.mapTransport = new HashMap<String, String>();
					this.mapTransport.put("transport_id", cols[0]);
					this.mapTransport.put("transport_description", cols[1]);
					this.arrTransport.add(this.mapTransport);
				}
			}
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					try{
						adTransport = new SimpleAdapter(getApplicationContext(), arrTransport, R.layout.spinner_layout_template, new String[]{"transport_id", "transport_description"}, new int[]{R.id.labItemValue, R.id.labItemText});
						spinTransportEdit.setAdapter(adTransport);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	void LoadLocations(){
		try{
			String sqlData = PhpScriptExecuter.getDataFromPhpScript("Select_Locations.php");
			if(sqlData.equals("")) return;
			String rows[], cols[];
			rows = sqlData.split(";");
			this.arrLocationSource = new ArrayList<HashMap<String,String>>();
			this.arrLocationDestination = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < rows.length; i++){
				if(!rows[i].trim().equals("")){
					cols = rows[i].split(",");
					this.mapLocationSource = new HashMap<String, String>();
					this.mapLocationSource.put("location_id", cols[0]);
					this.mapLocationSource.put("location_description", cols[1]);
					this.arrLocationSource.add(this.mapLocationSource);
					
					this.mapLocationDestination = new HashMap<String, String>();
					this.mapLocationDestination.put("location_id", cols[0]);
					this.mapLocationDestination.put("location_description", cols[1]);
					this.arrLocationDestination.add(this.mapLocationDestination);
				}
			}
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					adLocationSource = new SimpleAdapter(getApplicationContext(), arrLocationSource, R.layout.spinner_layout_template, new String[]{"location_id", "location_description"}, new int[]{R.id.labItemValue, R.id.labItemText});
					spinLocationSourceEdit.setAdapter(adLocationSource);
					
					adLocationDestination = new SimpleAdapter(getApplicationContext(), arrLocationDestination, R.layout.spinner_layout_template, new String[]{"location_id", "location_description"}, new int[]{R.id.labItemValue, R.id.labItemText});
					spinLocationDestinationEdit.setAdapter(adLocationDestination);
				}
			});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	void LoadItems(){
		try{
			String sqlData = PhpScriptExecuter.getDataFromPhpScript("Select_Items.php");
			if(sqlData.equals("")) return;
			String rows[], cols[];
			rows = sqlData.split(";");
			this.arrItems = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < rows.length - 1; i++){
				if(!rows[i].trim().equals("")){
					cols = rows[i].split(",");
					this.mapItems = new HashMap<String, String>();
					this.mapItems.put("item_id", cols[0]);
					this.mapItems.put("item_name", cols[1]);
					this.arrItems.add(this.mapItems);
				}
			}
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					adItems = new SimpleAdapter(getApplicationContext(), arrItems, R.layout.spinner_layout_template, new String[]{"item_id", "item_name"}, new int[]{R.id.labItemValue, R.id.labItemText});
					spinItems.setAdapter(adItems);
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
		getMenuInflater().inflate(R.menu.edit_transaction_details, menu);
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