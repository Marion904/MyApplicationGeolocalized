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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };

            Toast.makeText(MainActivity.this, "Vous devez autoriser la géolocalisation", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(MainActivity.this,"Go tiger", Toast.LENGTH_SHORT).show();
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    private void makeUseOfNewLocation(Location location){
        longitude = (double)Math.round(location.getLongitude()*100d)/100d;
        latitude =(double)Math.round(location.getLatitude()*100d)/100d;
        StringBuilder sb = new StringBuilder();
        sb.append("longitude : ");
        sb.append(longitude);
        sb.append("/ latidude : ");
        sb.append(latitude);
        Toast.makeText(MainActivity.this,sb.toString(),Toast.LENGTH_SHORT).show();
    }
}
