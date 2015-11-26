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
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class EditItemDetails extends Activity {

	int item_id;
	EditText txtItemCodeForEdit, txtItemNameForEdit, txtItemSpecificationForEdit;
	Spinner spinItemTypeForEdit, spinItemLocationForEdit;
	
	HashMap<String, String> mapLocation, mapType;
	SimpleAdapter adLocation, adType;
	ArrayList<HashMap<String, String>> arrLocation, arrType;
	
	public void btnDeleteItem_Click(View v){
		this.ConfirmDeleteItem();
	}
	
	public void btnSaveItem_Click(View v){
		new Thread(new Runnable() {
					
					@Override
					public void run() {
						String strPhpScriptFileName;
						String strParamsNames[], strParamsValues[];
						if(item_id == 0){
							//insert
							//item_code, item_name, type_id, item_specification, location_id
							strPhpScriptFileName = "Insert_Item.php";
							strParamsNames = new String[]{"item_code", "item_name", "type_id", "item_specification", "location_id"};
							strParamsValues = new String[]{txtItemCodeForEdit.getText().toString(),
														   txtItemNameForEdit.getText().toString(), 
														   arrType.get(spinItemTypeForEdit.getSelectedItemPosition()).get("type_id"),
														   txtItemSpecificationForEdit.getText().toString(),
														   arrLocation.get(spinItemLocationForEdit.getSelectedItemPosition()).get("location_id")};
						}
						else{
							//update
							//item_id, item_code, item_name, type_id, item_specification, location_id
							strPhpScriptFileName = "Update_Item.php";
							strParamsNames = new String[]{"item_id", "item_code", "item_name", "type_id", "item_specification", "location_id"};
							strParamsValues = new String[]{Integer.toString(item_id),
														   txtItemCodeForEdit.getText().toString(),
														   txtItemNameForEdit.getText().toString(), 
														   arrType.get(spinItemTypeForEdit.getSelectedItemPosition()).get("type_id"),
														   txtItemSpecificationForEdit.getText().toString(),
														   arrLocation.get(spinItemLocationForEdit.getSelectedItemPosition()).get("location_id")};
						}
						if(PhpScriptExecuter.insertUpdateDeleteData(strPhpScriptFileName, strParamsNames, strParamsValues)){
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									if(item_id == 0)
										Toast.makeText(getApplicationContext(), "A new item has been added.", Toast.LENGTH_LONG).show();
									else
										Toast.makeText(getApplicationContext(), "The selected item has been updated.", Toast.LENGTH_LONG).show();
									ItemsActivity.blnReloadGrid = true;
									finish();
								}
							});
						}
					}
				}).start();
	}
	
	private void ConfirmDeleteItem() {
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    	        switch (which){
    	        case DialogInterface.BUTTON_POSITIVE:
    	        	deleteItem();
    	            break;

    	        case DialogInterface.BUTTON_NEGATIVE:
    	            //No button clicked
    	            break;
    	        }
    	    }
    	};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure  do you want to delete the selected item?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
	}
	
	void deleteItem(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					if(PhpScriptExecuter.insertUpdateDeleteData("Delete_Item.php", new String[]{"item_id"}, new String[]{Integer.toString(item_id)})){
		        		runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), "Item deleted.", Toast.LENGTH_LONG).show();
								ItemsActivity.blnReloadGrid = true;
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
		setContentView(R.layout.activity_edit_item_details);
		if(getIntent().getStringExtra("item_id") == null){
			finish();
			return;
		}
		item_id = Integer.parseInt(getIntent().getStringExtra("item_id"));
		this.txtItemCodeForEdit = (EditText)findViewById(R.id.txtItemCodeForEdit);
		this.txtItemNameForEdit = (EditText)findViewById(R.id.txtItemNameForEdit);
		this.txtItemSpecificationForEdit = (EditText)findViewById(R.id.txtItemSpecificationForEdit);
		this.spinItemTypeForEdit = (Spinner)findViewById(R.id.spinItemTypeForEdit);
		this.spinItemLocationForEdit = (Spinner)findViewById(R.id.spinItemLocationForEdit);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				LoadLocations();
				LoadType();
				if(item_id > 0) LoadSelectedItemDetails();
			}
		}).start();
		
	}
	
	void LoadType(){
		try{
			String sqlData = PhpScriptExecuter.getDataFromPhpScript("Select_Types.php");
			if(sqlData.equals("")) return;
			String rows[], cols[];
			rows = sqlData.split(";");
			this.arrType = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < rows.length; i++){
				if(!rows[i].trim().equals("")){
					cols = rows[i].split(",");
					this.mapType = new HashMap<String, String>();
					this.mapType.put("type_id", cols[0]);
					this.mapType.put("type_name", cols[1]);
					this.arrType.add(this.mapType);
				}
			}
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					adType = new SimpleAdapter(getApplicationContext(), arrType, R.layout.spinner_layout_template, new String[]{"type_id", "type_name"}, new int[]{R.id.labItemValue, R.id.labItemText});
					spinItemTypeForEdit.setAdapter(adType);
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
			this.arrLocation = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < rows.length; i++){
				if(!rows[i].trim().equals("")){
					cols = rows[i].split(",");
					this.mapLocation = new HashMap<String, String>();
					this.mapLocation.put("location_id", cols[0]);
					this.mapLocation.put("location_description", cols[1]);
					this.arrLocation.add(this.mapLocation);
				}
			}
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					adLocation = new SimpleAdapter(getApplicationContext(), arrLocation, R.layout.spinner_layout_template, new String[]{"location_id", "location_description"}, new int[]{R.id.labItemValue, R.id.labItemText});
					spinItemLocationForEdit.setAdapter(adLocation);
				}
			});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void LoadSelectedItemDetails() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					String strItemDetails = PhpScriptExecuter.getDataFromPhpScript("Select_Item_ByID.php", new String[]{"item_id"}, new String[]{Integer.toString(item_id)});
					if(strItemDetails != null)
						if(!strItemDetails.equals("")){
							String cols[], rows[];
							rows = strItemDetails.split(";");
							for(int i = 0; i < rows.length; i++){//it should return only one row
								if(!rows[i].trim().equals("")){
									cols = rows[i].split(",");
									//item_code, item_name, type_id, item_specification, location_id
									final String colsData[] = cols;
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											txtItemCodeForEdit.setText(colsData[0]);
											txtItemNameForEdit.setText(colsData[1]);
											for(int j = 0; j < arrType.size(); j++)
												if(arrType.get(j).get("type_id").equals(colsData[2])){
													spinItemTypeForEdit.setSelection(j);
													break;
												}
											txtItemSpecificationForEdit.setText(colsData[3]);
											for(int k = 0; k < arrLocation.size(); k++)
												if(arrLocation.get(k).get("location_id").equals(colsData[4])){
													spinItemLocationForEdit.setSelection(k);
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
		getMenuInflater().inflate(R.menu.edit_item_details, menu);
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
