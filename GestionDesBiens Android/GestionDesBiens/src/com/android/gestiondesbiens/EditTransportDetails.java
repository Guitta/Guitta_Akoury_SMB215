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

public class EditTransportDetails extends Activity {
	
	int transport_id;
	
	Spinner spinPersonnelTransport;
	
	HashMap<String, String> mapPersonnel;
	SimpleAdapter adPersonnel;
	ArrayList<HashMap<String, String>> arrPersonnel;
	
	public void btnSaveTransport_Click(View v){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String strPhpScriptFileName;
				String strParamsNames[], strParamsValues[];
				if(transport_id == 0){
					//insert
					//personnel_id
					strPhpScriptFileName = "Insert_Transport.php";
					strParamsNames = new String[]{"personnel_id"};
					strParamsValues = new String[]{arrPersonnel.get(spinPersonnelTransport.getSelectedItemPosition()).get("personnel_id")};
				}
				else{
					//update
					//transport_id, personnel_id
					strPhpScriptFileName = "Update_Transport.php";
					strParamsNames = new String[]{"transport_id", "personnel_id"};
					strParamsValues = new String[]{Integer.toString(transport_id), arrPersonnel.get(spinPersonnelTransport.getSelectedItemPosition()).get("personnel_id")};
				}
				if(PhpScriptExecuter.insertUpdateDeleteData(strPhpScriptFileName, strParamsNames, strParamsValues)){
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							if(transport_id == 0)
								Toast.makeText(getApplicationContext(), "A new Transport has been added.", Toast.LENGTH_LONG).show();
							else
								Toast.makeText(getApplicationContext(), "The selected Transport has been updated.", Toast.LENGTH_LONG).show();
							TransportActivity.blnReloadGrid = true;
							finish();
						}
					});
				}
			}
		}).start();
	}
	
	public void btnDeleteTransport_Click(View v){
		this.ConfirmDeleteLocation();
	}
	
	private void ConfirmDeleteLocation() {
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    	        switch (which){
    	        case DialogInterface.BUTTON_POSITIVE:
    	        	deleteTransport();
    	            break;

    	        case DialogInterface.BUTTON_NEGATIVE:
    	            //No button clicked
    	            break;
    	        }
    	    }
    	};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure  do you want to delete the selected Transport?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
	}
	
	
	
	void deleteTransport(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					if(PhpScriptExecuter.insertUpdateDeleteData("Delete_Transport.php", new String[]{"transport_id"}, new String[]{Integer.toString(transport_id)})){
		        		runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), "Transport deleted.", Toast.LENGTH_LONG).show();
								TransportActivity.blnReloadGrid = true;
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
		setContentView(R.layout.activity_edit_transport_details);
		
		if(getIntent().getStringExtra("transport_id") == null){
			finish();
			return;
		}
		
		this.spinPersonnelTransport = (Spinner)findViewById(R.id.spinPersonnelTransport);
		transport_id = Integer.parseInt(getIntent().getStringExtra("transport_id"));
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				LoadPersonnel();
				if(transport_id > 0) LoadSelectedTransport();
			}
		}).start();
		
	}
	
	void LoadPersonnel(){
		try{
			String sqlData = PhpScriptExecuter.getDataFromPhpScript("Select_Personnel.php");
			if(sqlData.equals("")) return;
			String rows[], cols[];
			rows = sqlData.split(";");
			this.arrPersonnel = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < rows.length; i++){
				if(!rows[i].trim().equals("")){
					cols = rows[i].split(",");
					this.mapPersonnel = new HashMap<String, String>();
					this.mapPersonnel.put("personnel_id", cols[0]);
					this.mapPersonnel.put("personnel_name", cols[1]);
					this.arrPersonnel.add(this.mapPersonnel);
				}
			}
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					adPersonnel = new SimpleAdapter(getApplicationContext(), arrPersonnel, R.layout.spinner_layout_template, new String[]{"personnel_id", "personnel_name"}, new int[]{R.id.labItemValue, R.id.labItemText});
					spinPersonnelTransport.setAdapter(adPersonnel);
				}
			});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	void LoadSelectedTransport(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					String strLocationDetails = PhpScriptExecuter.getDataFromPhpScript("Select_Transport_ByID.php", new String[]{"transport_id"}, new String[]{Integer.toString(transport_id)});
					if(strLocationDetails != null)
						if(!strLocationDetails.equals("")){
							String cols[], rows[];
							rows = strLocationDetails.split(";");
							for(int i = 0; i < rows.length; i++){//it should return only one row
								if(!rows[i].trim().equals("")){
									cols = rows[i].split(",");
									// 	personnel_id
									final String colsData[] = cols;
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											for(int j = 0; j < arrPersonnel.size(); j++)
												if(arrPersonnel.get(j).get("personnel_id").equals(colsData[0])){
													spinPersonnelTransport.setSelection(j);
													break;
												}
										}
									});
								break;//to leave the for loop when fetching the first record, but anyways it only should be one record
								}
							}
						}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_transport_details, menu);
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
