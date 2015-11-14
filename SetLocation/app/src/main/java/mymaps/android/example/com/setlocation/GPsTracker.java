package mymaps.android.example.com.setlocation;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.parse.ParseObject;


/**
 * Created by alberta.adm on 12/11/2015.
 */
public class GPsTracker extends Service {
    PowerManager.WakeLock wakeLock;

    private LocationManager locationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);

        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");




        // Toast.makeText(getApplicationContext(), "Service Created",
        // Toast.LENGTH_SHORT).show();

        Log.e("Google", "Service Created");

    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        wakeLock.release();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.removeUpdates(listener);
            }
            }else {

            locationManager.removeUpdates(listener);
        }

    }


    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);


        Log.e("Google", "Service Started");
          Criteria criteria= new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);

        locationManager = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);
        String myProvider = locationManager.getBestProvider(criteria, true);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
              locationManager.requestLocationUpdates(myProvider,
                      6000,0 , listener);

            }


        } else {
             locationManager.requestLocationUpdates(myProvider,
                     6000, 0, listener);
        }
    }

    private LocationListener listener = new LocationListener() {


        @Override
        public void onLocationChanged(Location location) {
            ParseObject parseObject = new ParseObject("Location");
            parseObject.put("lat", location.getLatitude());
            parseObject.put("long", location.getLongitude());
            parseObject.saveInBackground();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
}