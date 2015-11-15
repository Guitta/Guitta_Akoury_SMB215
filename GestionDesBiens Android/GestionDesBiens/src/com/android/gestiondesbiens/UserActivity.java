package com.android.gestiondesbiens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.content.res.Resources;
import android.widget.Button;
import android.graphics.drawable.StateListDrawable;


public class UserActivity extends Activity {
	
	
	public void btnTransactions_Click(View v){
		Intent I = new Intent(getApplicationContext(), TestActivity.class);
		startActivity(I);		
	}
	
	public void btnTransport_Click(View v){
		Intent I = new Intent(getApplicationContext(), TestActivity.class);
		startActivity(I);		
	}
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		Resources res = getResources();
        StateListDrawable myButton2 = new StateListDrawable();
        myButton2.addState(new int[] {android.R.attr.state_pressed}, res.getDrawable(R.drawable.button2));
        myButton2.addState(new int[] {android.R.attr.state_focused}, res.getDrawable(R.drawable.button3));
        myButton2.addState(new int[] {}, res.getDrawable(R.drawable.button1));
        
        ((Button)findViewById(R.id.btnTransport)).setBackgroundDrawable(myButton2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
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
