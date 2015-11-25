package com.android.gestiondesbiens;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.android.gestiondesbiens.model.Users;
import com.android.gestiondesbiens.parsers.UsersXMLParser;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class UsersActivity extends Activity {
	
	List<Users> usersList;
	List<MyTask> tasks;
	EditText txtUserName, txtPwd, txtName;
	TextView tvUser, tvPwd, tvName;
	Button bsave, bnew, bdelete;
	RadioButton rdAdmin, rdUser;
	
	
	public void btnNewUsers_Click (View v){

		rdAdmin.setVisibility(View.VISIBLE);
		rdUser.setVisibility(View.VISIBLE);		
		rdUser.setSelected(true);

		txtUserName.setVisibility(View.VISIBLE);
		txtPwd.setVisibility(View.VISIBLE);
		txtName.setVisibility(View.VISIBLE);
		
		tvName.setVisibility(View.VISIBLE);
		tvPwd.setVisibility(View.VISIBLE);
		tvUser.setVisibility(View.VISIBLE);
		
		bnew.setEnabled(false);
		bdelete.setEnabled(false);
		
	}
	
	public void btnSaveUsers_Click (View v){
		new Thread(new Runnable() {
					
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						insertDeviceId();
					}
				}).start();
			}
			
			   public void insertDeviceId()
			    {
			          InputStream is=null;
			          String result=null;
			          String line=null;
			          int code;
			          boolean res = false;
			        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			        if (txtUserName.getText().toString().trim().equalsIgnoreCase("") || txtPwd.getText().toString().equals("") || txtName.getText().toString().equals("")){
			        	if (txtUserName.getText().toString().trim().equalsIgnoreCase("")) txtUserName.setError("User name is required");
			        	if (txtPwd.getText().toString().equals("")) txtPwd.setError("Password is required");
			        	if (txtName.getText().toString().equals("")) txtName.setError("Name is required");
			        } else{
			        	for (int i=0; i<usersList.size(); i++){
			        		if (usersList.get(i).getUsername().equalsIgnoreCase(txtUserName.getText().toString().trim())){
			        			txtUserName.setError("User name exist please choose another");
			        			txtUserName.selectAll();
			        			return;
			        		}
			        	}
			        	nameValuePairs.add(new BasicNameValuePair("username", txtUserName.getText().toString()));
			        	nameValuePairs.add(new BasicNameValuePair("password", generateHash(txtPwd.getText().toString())));
			        	nameValuePairs.add(new BasicNameValuePair("name", txtName.getText().toString()));
			        	if (rdAdmin.isSelected()){
				        	nameValuePairs.add(new BasicNameValuePair("groupname", "admin"));
			        	} else {
				        	nameValuePairs.add(new BasicNameValuePair("groupname", "user"));

			        	}

			        }
			        try
			        {
			        	
			            HttpClient httpclient = new DefaultHttpClient();
			            HttpPost httppost = new HttpPost("http://192.168.1.67:80/Insert_Users.php");
			            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			            HttpResponse response = httpclient.execute(httppost);
		
			           runOnUiThread(new Runnable() {
						
						@Override
						public void run(){
							rdAdmin.setVisibility(View.GONE);
							rdUser.setVisibility(View.GONE);
							tvUser.setVisibility(View.GONE);
							txtUserName.setVisibility(View.GONE);
							tvPwd.setVisibility(View.GONE);
							txtPwd.setVisibility(View.GONE);
							tvName.setVisibility(View.GONE);
							txtName.setVisibility(View.GONE);
							bnew.setEnabled(true);
							bdelete.setEnabled(true);
							
							// refresh activity 
							MyTask task = new MyTask();
							task.execute("http://" + ClsCommon.SERVER_IP.split(":")[0] + ":8080/GestionDesBiens/webresources/model.users");
							Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
						}
					});
			            Log.e("pass 1", "connection success ");
			        }
			        catch(Exception e)
			        {
			            Log.e("Fail 1", e.toString());
			        }   
			    }
			   
				    public static String generateHash (String password){
				        String output = Hashing.sha256().hashString(password, Charsets.UTF_8).toString();
				        return output;
				    }    
				

	
	
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
			String content = HttpManager.getData(params[0]);
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
			
	
				usersList = UsersXMLParser.parseFeed(result);
	
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
		setContentView(R.layout.activity_users);
		
		this.txtUserName = (EditText)findViewById(R.id.txtUserName);
		txtUserName.setVisibility(View.GONE);
		this.txtPwd=(EditText)findViewById(R.id.txtPassword);
		txtPwd.setVisibility(View.GONE);
		this.txtName=(EditText)findViewById(R.id.txtName);
		txtName.setVisibility(View.GONE);
		
		this.tvUser=(TextView)findViewById(R.id.textViewUser);
		tvUser.setVisibility(View.GONE);
		this.tvPwd=(TextView)findViewById(R.id.textViewPwd);
		tvPwd.setVisibility(View.GONE);
		this.tvName=(TextView)findViewById(R.id.textViewName);
		tvName.setVisibility(View.GONE);
		
		this.bsave=(Button) findViewById(R.id.btnSaveCenter);
		this.bnew=(Button) findViewById(R.id.btnNewUsers);
		this.bdelete=(Button) findViewById(R.id.btnDeleteUsers);
		
		this.rdAdmin=(RadioButton)findViewById(R.id.radioAdmin);
		rdAdmin.setVisibility(View.GONE);
		this.rdUser=(RadioButton)findViewById(R.id.radioUser);
		rdUser.setVisibility(View.GONE);
	
		this.lstHeader = (ListView)findViewById(R.id.lstUsersHeader);
		this.lstReservedWorkDetails = (ListView)findViewById(R.id.lstUsersDetails);
		tasks = new ArrayList<>();
		this.requestData("http://192.168.1.67:8080/GestionDesBiens/webresources/model.users");
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
         
                this.mapReservedWorkHeader.put("LocationID", "Name");
                this.mapReservedWorkHeader.put("CenterName", "User name");
                this.mapReservedWorkHeader.put("SalleName", "Group name");
                this.mapReservedWorkHeader.put("PersonnelName", "Register date");
          
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
            
            for(int i = 0; i < this.usersList.size(); i++){
             this.mapReservedWorkDetails = new HashMap<String, String>();
             
                 this.mapReservedWorkDetails.put("LocationID", this.usersList.get(i).getName());
                 this.mapReservedWorkDetails.put("CenterName", this.usersList.get(i).getUsername());
                 this.mapReservedWorkDetails.put("SalleName", this.usersList.get(i).getGroupname());
                 this.mapReservedWorkDetails.put("PersonnelName", this.usersList.get(i).getRegisterDt());
             
      
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
		getMenuInflater().inflate(R.menu.users, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_get_centers) {
			Intent I = new Intent(getApplicationContext(), CenterActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_type) {
			Intent I = new Intent(getApplicationContext(), TypeActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_personnel) {
			Intent I = new Intent(getApplicationContext(), PersonnelActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_locations) {
			Intent I = new Intent(getApplicationContext(), LocationsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_groups) {
			Intent I = new Intent(getApplicationContext(), GroupsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_items) {
			Intent I = new Intent(getApplicationContext(), ItemsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_salles) {
			Intent I = new Intent(getApplicationContext(), SallesActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_transations) {
			Intent I = new Intent(getApplicationContext(), TransactionsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_transport) {
			Intent I = new Intent(getApplicationContext(), TransportActivity.class);
			startActivity(I);		
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
