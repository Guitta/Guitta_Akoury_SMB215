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


public class EditSalleDetails extends Activity {

	int salle_id;

	EditText txtSalleName;
	
	
public void btnSaveSalle_Click(View v){
	
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			String strPhpScriptFileName;
			String strParamsNames[], strParamsValues[];
			if(salle_id == 0){
				//insert
				strPhpScriptFileName = "Insert_Salle.php";
				strParamsNames = new String[]{"salle_name"};
				strParamsValues = new String[]{txtSalleName.getText().toString()};
			}
			else{
				//update
				strPhpScriptFileName = "Update_Salle.php";
				strParamsNames = new String[]{"salle_id", "salle_name"};
				strParamsValues = new String[]{Integer.toString(salle_id),
						txtSalleName.getText().toString()};
			}
			if(PhpScriptExecuter.insertUpdateDeleteData(strPhpScriptFileName, strParamsNames, strParamsValues)){
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if(salle_id == 0)
							Toast.makeText(getApplicationContext(), "A new salle has been added.", Toast.LENGTH_LONG).show();
						else
							Toast.makeText(getApplicationContext(), "The selected salle has been updated.", Toast.LENGTH_LONG).show();
						SallesActivity.blnReloadGrid = true;
						finish();
					}
				});
			}
		}
	}).start();

		
	}
	
	public void btnDeleteSalle_Click(View v){
		this.ConfirmDeleteSalle();
	}
	
	private void ConfirmDeleteSalle() {
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    	        switch (which){
    	        case DialogInterface.BUTTON_POSITIVE:
    	        	deleteSalle();
    	            break;

    	        case DialogInterface.BUTTON_NEGATIVE:
    	            //No button clicked
    	            break;
    	        }
    	    }
    	};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure you want to delete the selected salle?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
	}
	void deleteSalle(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					if(PhpScriptExecuter.insertUpdateDeleteData("Delete_Salle.php", new String[]{"salle_id"}, new String[]{Integer.toString(salle_id)})){
		        		runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), "Salle deleted.", Toast.LENGTH_LONG).show();
								SallesActivity.blnReloadGrid = true;
								
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
		setContentView(R.layout.activity_edit_salle_details);
		
		if(getIntent().getStringExtra("salle_id") == null){
			finish();
			return;
		}
		this.salle_id = Integer.parseInt(getIntent().getStringExtra("salle_id"));
		this.txtSalleName = (EditText)findViewById(R.id.txtSalleName);
		
		new Thread(new Runnable() {
					
					@Override
					public void run() {
			
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
							if(salle_id > 0) txtSalleName.setText(getIntent().getStringExtra("salle_name"));
							}
						});
					}
				}).start();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_salle_details, menu);
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
