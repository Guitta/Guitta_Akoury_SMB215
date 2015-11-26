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

public class EditLocationDetails extends Activity {
	
	Spinner spinCenterIdEdit, spinSalleIdEdit, spinPersonnelEdit;

	HashMap<String, String> mapSalle, mapCenter, mapPersonnel;
	SimpleAdapter adSalle, adCenter, adPersonnel;
	ArrayList<HashMap<String, String>> arrSalle, arrCenter, arrPersonnel;
	
	int location_id;
	
	public void btnSaveLocation_Click(View v){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String strPhpScriptFileName;
				String strParamsNames[], strParamsValues[];
				if(location_id == 0){
					//insert
					//center_id, salle_id, personnel_id
					strPhpScriptFileName = "Insert_Location.php";
					strParamsNames = new String[]{"center_id", "salle_id", "personnel_id"};
					strParamsValues = new String[]{arrCenter.get(spinCenterIdEdit.getSelectedItemPosition()).get("center_id"),
												   arrSalle.get(spinSalleIdEdit.getSelectedItemPosition()).get("salle_id"),
												   arrPersonnel.get(spinPersonnelEdit.getSelectedItemPosition()).get("personnel_id")};
				}
				else{
					//update
					//location_id, center_id, salle_id, personnel_id
					strPhpScriptFileName = "Update_Location.php";
					strParamsNames = new String[]{"location_id", "center_id", "salle_id", "personnel_id"};
					strParamsValues = new String[]{Integer.toString(location_id),
												   arrCenter.get(spinCenterIdEdit.getSelectedItemPosition()).get("center_id"),
												   arrSalle.get(spinSalleIdEdit.getSelectedItemPosition()).get("salle_id"),
												   arrPersonnel.get(spinPersonnelEdit.getSelectedItemPosition()).get("personnel_id")};
				}
				if(PhpScriptExecuter.insertUpdateDeleteData(strPhpScriptFileName, strParamsNames, strParamsValues)){
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							if(location_id == 0)
								Toast.makeText(getApplicationContext(), "A new location has been added.", Toast.LENGTH_LONG).show();
							else
								Toast.makeText(getApplicationContext(), "The selected location has been updated.", Toast.LENGTH_LONG).show();
							LocationsActivity.blnReloadGrid = true;
							finish();
						}
					});
				}
			}
		}).start();
	}
	
	public void btnDeleteLocation_Click(View v){
		this.ConfirmDeleteLocation();
	}
	
	private void ConfirmDeleteLocation() {
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    	        switch (which){
    	        case DialogInterface.BUTTON_POSITIVE:
    	        	deleteLocation();
    	            break;

    	        case DialogInterface.BUTTON_NEGATIVE:
    	            //No button clicked
    	            break;
    	        }
    	    }
    	};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure  do you want to delete the selected location?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
	}
	
	
	
	void deleteLocation(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					if(PhpScriptExecuter.insertUpdateDeleteData("Delete_Location.php", new String[]{"location_id"}, new String[]{Integer.toString(location_id)})){
		        		runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), "Location deleted.", Toast.LENGTH_LONG).show();
								LocationsActivity.blnReloadGrid = true;
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
		setContentView(R.layout.activity_edit_location_details);
		
		if(getIntent().getStringExtra("location_id") == null){
			finish();
			return;
		}
		
		this.location_id = Integer.parseInt(getIntent().getStringExtra("location_id"));
		
		this.spinCenterIdEdit = (Spinner)findViewById(R.id.spinCenterIdEdit);
		this.spinSalleIdEdit = (Spinner)findViewById(R.id.spinSalleIdEdit);
		this.spinPersonnelEdit = (Spinner)findViewById(R.id.spinPersonnelEdit);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				LoadCenter();
				LoadSalle();
				LoadPersonnel();
				if(location_id > 0) LoadSelectedLocation();
			}
		}).start();
	}
	
	void LoadCenter(){
		try{
			String sqlData = PhpScriptExecuter.getDataFromPhpScript("Select_Centers.php");
			if(sqlData.equals("")) return;
			String rows[], cols[];
			rows = sqlData.split(";");
			this.arrCenter = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < rows.length; i++){
				if(!rows[i].trim().equals("")){
					cols = rows[i].split(",");
					this.mapCenter = new HashMap<String, String>();
					this.mapCenter.put("center_id", cols[0]);
					this.mapCenter.put("center_name", cols[1]);
					this.arrCenter.add(this.mapCenter);
				}
			}
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					adCenter = new SimpleAdapter(getApplicationContext(), arrCenter, R.layout.spinner_layout_template, new String[]{"center_id", "center_name"}, new int[]{R.id.labItemValue, R.id.labItemText});
					spinCenterIdEdit.setAdapter(adCenter);
				}
			});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	void LoadSalle(){
		try{
			String sqlData = PhpScriptExecuter.getDataFromPhpScript("Select_Salles.php");
			if(sqlData.equals("")) return;
			String rows[], cols[];
			rows = sqlData.split(";");
			this.arrSalle = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < rows.length; i++){
				if(!rows[i].trim().equals("")){
					cols = rows[i].split(",");
					this.mapSalle = new HashMap<String, String>();
					this.mapSalle.put("salle_id", cols[0]);
					this.mapSalle.put("salle_name", cols[1]);
					this.arrSalle.add(this.mapSalle);
				}
			}
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					adSalle = new SimpleAdapter(getApplicationContext(), arrSalle, R.layout.spinner_layout_template, new String[]{"salle_id", "salle_name"}, new int[]{R.id.labItemValue, R.id.labItemText});
					spinSalleIdEdit.setAdapter(adSalle);
				}
			});
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
					spinPersonnelEdit.setAdapter(adPersonnel);
				}
			});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	void LoadSelectedLocation(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					String strLocationDetails = PhpScriptExecuter.getDataFromPhpScript("Select_Location_ByID.php", new String[]{"location_id"}, new String[]{Integer.toString(location_id)});
					if(strLocationDetails != null)
						if(!strLocationDetails.equals("")){
							String cols[], rows[];
							rows = strLocationDetails.split(";");
							for(int i = 0; i < rows.length; i++){//it should return only one row
								if(!rows[i].trim().equals("")){
									cols = rows[i].split(",");
									//center_id, salle_id, personnel_id
									final String colsData[] = cols;
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											for(int j = 0; j < arrCenter.size(); j++)
												if(arrCenter.get(j).get("center_id").equals(colsData[0])){
													spinCenterIdEdit.setSelection(j);
													break;
												}
											for(int k = 0; k < arrSalle.size(); k++)
												if(arrSalle.get(k).get("salle_id").equals(colsData[1])){
													spinSalleIdEdit.setSelection(k);
													break;
												}
											for(int k = 0; k < arrPersonnel.size(); k++)
												if(arrPersonnel.get(k).get("personnel_id").equals(colsData[2])){
													spinPersonnelEdit.setSelection(k);
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
		getMenuInflater().inflate(R.menu.edit_location_details, menu);
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
