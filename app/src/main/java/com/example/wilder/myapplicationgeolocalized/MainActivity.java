package com.example.wilder.myapplicationgeolocalized;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager = null;
    double latitude, longitude;
    String locationProvider = LocationManager.NETWORK_PROVIDER;
    final int MY_PERMISSION_ACCESS_LOCATION = 1234;
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            makeUseOfNewLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, MY_PERMISSION_ACCESS_LOCATION);
            Toast.makeText(MainActivity.this, R.string.need_permission_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(MainActivity.this, R.string.app_start_toast, Toast.LENGTH_SHORT).show();
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    private void makeUseOfNewLocation(Location location) {
        /**rounding the latitude and longitude to 2 decimals*/
        longitude = (double) Math.round(location.getLongitude() * 100d) / 100d;
        latitude = (double) Math.round(location.getLatitude() * 100d) / 100d;
        StringBuilder sb = new StringBuilder();
        sb.append("longitude : ");
        sb.append(longitude);
        sb.append("/ latidude : ");
        sb.append(latitude);
        Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(MainActivity.this, R.string.no_permission_toast, Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
}