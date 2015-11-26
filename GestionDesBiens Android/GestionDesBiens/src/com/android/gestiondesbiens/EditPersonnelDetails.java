package com.android.gestiondesbiens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditPersonnelDetails extends Activity {
	
	int personnel_id;

	EditText txtPersonnelName;
	
	public void btnSavePersonnel_Click(View v){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String strPhpScriptFileName;
				String strParamsNames[], strParamsValues[];
				if(personnel_id == 0){
					//insert
					strPhpScriptFileName = "Insert_Personnel.php";
					strParamsNames = new String[]{"personnel_name"};
					strParamsValues = new String[]{txtPersonnelName.getText().toString()};
				}
				else{
					//update
					strPhpScriptFileName = "Update_Personnel.php";
					strParamsNames = new String[]{"personnel_id", "personnel_name"};
					strParamsValues = new String[]{Integer.toString(personnel_id),
							txtPersonnelName.getText().toString()};
				}
				if(PhpScriptExecuter.insertUpdateDeleteData(strPhpScriptFileName, strParamsNames, strParamsValues)){
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							if(personnel_id == 0)
								Toast.makeText(getApplicationContext(), "A new personnel has been added.", Toast.LENGTH_LONG).show();
							else
								Toast.makeText(getApplicationContext(), "The selected personnel has been updated.", Toast.LENGTH_LONG).show();
							PersonnelActivity.blnReloadGrid = true;
							finish();
						}
					});
				}
			}
		}).start();

			
		}
		
		public void btnDeletePersonnel_Click(View v){
			this.ConfirmDeletePersonnel();
		}
		
		private void ConfirmDeletePersonnel() {
	    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    	    @Override
	    	    public void onClick(DialogInterface dialog, int which) {
	    	        switch (which){
	    	        case DialogInterface.BUTTON_POSITIVE:
	    	        	deletePersonnel();
	    	            break;

	    	        case DialogInterface.BUTTON_NEGATIVE:
	    	            //No button clicked
	    	            break;
	    	        }
	    	    }
	    	};
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage("Are you sure you want to delete the selected personnel?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
		}
		void deletePersonnel(){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						if(PhpScriptExecuter.insertUpdateDeleteData("Delete_Personnel.php", new String[]{"personnel_id"}, new String[]{Integer.toString(personnel_id)})){
			        		runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(getApplicationContext(), "Personnel deleted.", Toast.LENGTH_LONG).show();
									PersonnelActivity.blnReloadGrid = true;
									
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
		setContentView(R.layout.activity_edit_personnel_details);
		
		if(getIntent().getStringExtra("personnel_id") == null){
			finish();
			return;
		}
		this.personnel_id = Integer.parseInt(getIntent().getStringExtra("personnel_id"));
		this.txtPersonnelName = (EditText)findViewById(R.id.txtPersonnelName);
		
		new Thread(new Runnable() {
					
					@Override
					public void run() {
			
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
							if(personnel_id > 0) txtPersonnelName.setText(getIntent().getStringExtra("personnel_name"));
							}
						});
					}
				}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_personnel_details, menu);
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
