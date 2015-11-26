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

public class EditTypeDetails extends Activity {
	
	int type_id;

	EditText txtTypeName;
	
	public void btnSaveType_Click(View v){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String strPhpScriptFileName;
				String strParamsNames[], strParamsValues[];
				if(type_id == 0){
					//insert
					strPhpScriptFileName = "Insert_type.php";
					strParamsNames = new String[]{"type_name"};
					strParamsValues = new String[]{txtTypeName.getText().toString()};
				}
				else{
					//update
					strPhpScriptFileName = "Update_type.php";
					strParamsNames = new String[]{"type_id", "type_name"};
					strParamsValues = new String[]{Integer.toString(type_id),
							txtTypeName.getText().toString()};
				}
				if(PhpScriptExecuter.insertUpdateDeleteData(strPhpScriptFileName, strParamsNames, strParamsValues)){
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							if(type_id == 0)
								Toast.makeText(getApplicationContext(), "A new type has been added.", Toast.LENGTH_LONG).show();
							else
								Toast.makeText(getApplicationContext(), "The selected type has been updated.", Toast.LENGTH_LONG).show();
							TypeActivity.blnReloadGrid = true;
							finish();
						}
					});
				}
			}
		}).start();

			
		}
		
		public void btnDeleteType_Click(View v){
			this.ConfirmDeleteType();
		}
		
		private void ConfirmDeleteType() {
	    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    	    @Override
	    	    public void onClick(DialogInterface dialog, int which) {
	    	        switch (which){
	    	        case DialogInterface.BUTTON_POSITIVE:
	    	        	deleteType();
	    	            break;

	    	        case DialogInterface.BUTTON_NEGATIVE:
	    	            //No button clicked
	    	            break;
	    	        }
	    	    }
	    	};
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage("Are you sure you want to delete the selected type?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
		}
		void deleteType(){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						if(PhpScriptExecuter.insertUpdateDeleteData("Delete_type.php", new String[]{"type_id"}, new String[]{Integer.toString(type_id)})){
			        		runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(getApplicationContext(), "Type deleted.", Toast.LENGTH_LONG).show();
									TypeActivity.blnReloadGrid = true;
									
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
		setContentView(R.layout.activity_edit_type_details);
		
		if(getIntent().getStringExtra("type_id") == null){
			finish();
			return;
		}
		this.type_id = Integer.parseInt(getIntent().getStringExtra("type_id"));
		this.txtTypeName = (EditText)findViewById(R.id.txtTypeName);
		
		new Thread(new Runnable() {
					
					@Override
					public void run() {
			
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
							if(type_id > 0) txtTypeName.setText(getIntent().getStringExtra("type_name"));
							}
						});
					}
				}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_type_details, menu);
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
