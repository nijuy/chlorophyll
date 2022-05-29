package com.example.myapplication.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class LocationTracker implements LocationListener {

    private android.content.Context context;
    private Location location;
    private double latitude, longitude;

    LocationTracker(android.content.Context c){
        this.context = c;
        getLocation();

    }

    public Location getLocation() {
        try{
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            Log.d("@@", Boolean.toString(lm.isProviderEnabled(LocationManager.GPS_PROVIDER))); //gps 동작 확인
            Log.d("@@", Boolean.toString(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))); //network 동작 확인

            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
               ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, this);

                if(lm != null){
                    location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if(location != null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }

            }

        } catch (Exception e){
            Log.d("@",e.toString());
        }
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderDisabled(String provider){ }

    @Override
    public void onProviderEnabled(String provider){ }

}
