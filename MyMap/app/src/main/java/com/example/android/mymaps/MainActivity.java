package com.example.android.mymaps;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener {
private Handler handler=new Handler();
    private Marker marker;
private String lastLat="";
    private String lastLong="";
    private static final int ERROR_DIALOG_REQUEST=9001;
    GoogleMap mMap;
    private static final double
            SEATTLE_LAT = 47.60621,
            SEATTLE_LNG = -122.33207,
            SYDNEY_LAT = -33.867487,
            SYDNEY_LNG = 151.20699,
            NEWYORK_LAT = 40.714353,
            NEWYORK_LNG = -74.005973;


    private GoogleApiClient mLocationClient;
    private com.google.android.gms.location.LocationListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (servicesOK()) {
            setContentView(R.layout.activity_map);

            if (initMap()) {

                gotoLocation(SEATTLE_LAT, SEATTLE_LNG, 15);

                mLocationClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();

                mLocationClient.connect();

    //            mMap.setMyLocationEnabled(true);
            } else {

                Toast.makeText(this, "Map not connected!", Toast.LENGTH_SHORT).show();
            }


        } else {
            setContentView(R.layout.activity_main);
        }
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "nvlOlG2xjqcTOptsoQtEV3GceVjoSItfbcYB9DXn", "kAnL0yvvgk9wzRqfceBVtJZAGrIMcqnVYC42eNIg");
              handler.postDelayed(runnable,100);


    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            getparserecord();
      /* and here comes the "trick" */
            handler.postDelayed(this, 100);
        }
    };
        private void getparserecord(){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
            query.orderByDescending("updatedAt");
            //query.whereEqualTo("playerName", "Dan Stemkoski");
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject Object, ParseException e) {
                    if (Object != null) {
                        //Toast.makeText(MainActivity.this,Location.size(),Toast.LENGTH_LONG).show();


                            String lat = Object.get("lat").toString();


                            String longt = Object.get("long").toString();

                            if (!lat.equals(lastLat) && !longt.equals(lastLong)) {
                                lastLat = lat;
                                lastLong = longt;
                                gotoLocation(Double.parseDouble(lat), Double.parseDouble(longt), 16);
                            }
                        }else {
                        //   Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });
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

        switch (id) {
            case R.id.mapTypeNone:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean servicesOK() {

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {

            return true;

        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {

            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,this,ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {

            Toast.makeText(this, "can't connect to mapping service", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private boolean initMap () {

        if (mMap == null) {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();
        }
        return (mMap != null);
    }

    private void gotoLocation(double lat, double lng, float zoom) {
        mMap.clear();

        marker=mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("BUS").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
        LatLng latLng = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager imm =
                (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void geoLocate(View v) throws IOException {

        hideSoftKeyboard(v);

        TextView tv = (TextView) findViewById(R.id.editText1);
        String searchString = tv.getText().toString();


        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(searchString, 1);

        if (list.size() > 0) {
            Address add = list.get(0);
            String locality = add.getLocality();
            Toast.makeText(this, "Found: " + locality, Toast.LENGTH_SHORT).show();

            double lat = add.getLatitude();
            double lng = add.getLongitude();
            gotoLocation(lat, lng, 15);

            MarkerOptions options = new MarkerOptions()
                    .title(locality)
                    .position(new LatLng(lat, lng));
            mMap.addMarker(options);
        }

    }

    public void showCurrentLocation (MenuItem item) {

        Location currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mLocationClient);

        if (currentLocation == null) {

            Toast.makeText(this, "Couldn't connect!", Toast.LENGTH_SHORT).show();
        } else {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.animateCamera(update);
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();

        mListener = new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


            }
         };

        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5000);
        request.setFastestInterval(1000);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mLocationClient, request, mListener
        );
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mLocationClient,mListener
        );

    }
}
