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

public class EditCenterDetails extends Activity {
	
	int center_id;

	EditText txtCenterName;
	
	
public void btnSaveCenter_Click(View v){
	
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			String strPhpScriptFileName;
			String strParamsNames[], strParamsValues[];
			if(center_id == 0){
				//insert
				strPhpScriptFileName = "Insert_Center.php";
				strParamsNames = new String[]{"center_name"};
				strParamsValues = new String[]{txtCenterName.getText().toString()};
			}
			else{
				//update
				strPhpScriptFileName = "Update_Center.php";
				strParamsNames = new String[]{"center_id", "center_name"};
				strParamsValues = new String[]{Integer.toString(center_id),
						txtCenterName.getText().toString()};
			}
			if(PhpScriptExecuter.insertUpdateDeleteData(strPhpScriptFileName, strParamsNames, strParamsValues)){
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if(center_id == 0)
							Toast.makeText(getApplicationContext(), "A new center has been added.", Toast.LENGTH_LONG).show();
						else
							Toast.makeText(getApplicationContext(), "The selected center has been updated.", Toast.LENGTH_LONG).show();
						CenterActivity.blnReloadGrid = true;
						finish();
					}
				});
			}
		}
	}).start();

		
	}
	
	public void btnDeleteCenter_Click(View v){
		this.ConfirmDeleteCenter();
	}
	
	private void ConfirmDeleteCenter() {
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    	        switch (which){
    	        case DialogInterface.BUTTON_POSITIVE:
    	        	deleteCenter();
    	            break;

    	        case DialogInterface.BUTTON_NEGATIVE:
    	            //No button clicked
    	            break;
    	        }
    	    }
    	};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure you want to delete the selected center?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
	}
	void deleteCenter(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					if(PhpScriptExecuter.insertUpdateDeleteData("Delete_Center.php", new String[]{"center_id"}, new String[]{Integer.toString(center_id)})){
		        		runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), "Center deleted.", Toast.LENGTH_LONG).show();
								CenterActivity.blnReloadGrid = true;
								
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
		setContentView(R.layout.activity_edit_center_details);
		
		if(getIntent().getStringExtra("center_id") == null){
			finish();
			return;
		}
		this.center_id = Integer.parseInt(getIntent().getStringExtra("center_id"));
		this.txtCenterName = (EditText)findViewById(R.id.txtCenterName);
		
		new Thread(new Runnable() {
					
					@Override
					public void run() {
			
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
							if(center_id > 0) txtCenterName.setText(getIntent().getStringExtra("center_name"));
							}
						});
					}
				}).start();

	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_center_details, menu);
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
