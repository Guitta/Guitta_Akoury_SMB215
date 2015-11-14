package mymaps.android.example.com.setlocation;

import android.app.ActivityManager;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.Parse;

public class MainActivity extends AppCompatActivity
        {

    public double latitude;
    public double longitude;
    private GoogleApiClient mLocationClient;
    private com.google.android.gms.location.LocationListener mListener;
    Location location;
    LocationManager locationManager;
    LocationListener loclistener;
    TextView text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.enableLocalDatastore(getApplicationContext());

        Parse.initialize(getApplicationContext(), "nvlOlG2xjqcTOptsoQtEV3GceVjoSItfbcYB9DXn", "kAnL0yvvgk9wzRqfceBVtJZAGrIMcqnVYC42eNIg");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Button Start=(Button)findViewById(R.id.btn_start);
        if (isMyServiceRunning()) Start.setText("Stop GPS...");

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMyServiceRunning()) {
                    Intent Gpsservice = new Intent(MainActivity.this, GPsTracker.class);
                    startService(Gpsservice);
                    Start.setText("Stop GPS...");
                } else{
                    Intent Gpsservice = new Intent(MainActivity.this, GPsTracker.class);
                    stopService(Gpsservice);

                    Start.setText("Start GPS");
                }
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent Gpsservice = new Intent(MainActivity.this, GPsTracker.class);
                stopService(Gpsservice);
                        System.exit(0);
                    }







        });





        // Enable Local Datastore.







}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



            private boolean isMyServiceRunning() {
                ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                    if ("mymaps.android.example.com.setlocation.GPsTracker".equals(service.service.getClassName())) {
                        return true;
                    }
                }
                return false;
            }
        }
