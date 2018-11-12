package com.example.manhtuan.myapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String EXTRA_LATITUDE = "com.example.manhtuan.myapplication.extraLatitude";
    public static final String EXTRA_LONGTITUDE = "com.example.manhtuan.myapplication.extraLongtitude";
    public static final String EXTRA_BUNDLE = "com.example.manhtuan.myapplication.extraBundle";
    public static final String EXTRA_ADDRESS = "com.example.manhtuan.myapplication.extraAddress";

    private GoogleMap mMap;
    private Marker mMarker;
    private PlaceAutocompleteFragment mPlaceAutoComplete;
    private Button mButtonWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mappingView();
        setView();
    }

    private void mappingView() {
        mButtonWeather = findViewById(R.id.buttonWeather);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mPlaceAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
    }

    private void setView() {
        mPlaceAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if(mMap == null){
                    Toast.makeText(MapsActivity.this, "mMap null", Toast.LENGTH_SHORT).show();
                }
                mMarker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title("Marker in "+ place.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),15));
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }

            @Override
            public void onError(Status status) {
                Log.d("asdf", "An error occurred: " + status);
            }
        });

        mButtonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMarker == null){
                    Toast.makeText(MapsActivity.this, "Choose Location", Toast.LENGTH_SHORT).show();
                }
                else{
                    double latitude = mMarker.getPosition().latitude;
                    double longtitude = mMarker.getPosition().longitude;
                    Bundle bundle = new Bundle();
                    bundle.putDouble(EXTRA_LATITUDE,latitude);
                    bundle.putDouble(EXTRA_LONGTITUDE,longtitude);
                    bundle.putString(EXTRA_ADDRESS,getAddress(latitude,longtitude));
                    Intent intent = new Intent(MapsActivity.this,ListWeatherActivity.class);
                    intent.putExtra(EXTRA_BUNDLE,bundle);
                    startActivity(intent);
                }
            }
        });
    }
    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            String address;
            if(addresses.size() == 0){
                address = "Unknown";
            }
            else{
                Address obj = addresses.get(0);
                if(obj.getCountryName() == null){
                    address = "Unknown";
                } else if(obj.getAdminArea() == null){
                    address = obj.getCountryName();
                }
                else address = obj.getAdminArea() + ", " + obj.getCountryName();

            }
//            address = obj.getAddressLine(0);
//            address = address + "\n" + obj.getCountryName();
//            address = address + "\n" + obj.getAdminArea();
//            address = address + "\n" + obj.getSubAdminArea();
//            address = address + "\n" + obj.getLocality();
//            return obj.getCountryName() + ","
            Log.d("asdf", "Address : " + address);
            return address;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return "Unknown";
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a mMarker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latlng) {
                // TODO Auto-generated method stub
                if (mMarker != null) {
                    mMarker.remove();
                }
                mMarker = mMap.addMarker(new MarkerOptions().position(latlng));
                getAddress(mMarker.getPosition().latitude,mMarker.getPosition().longitude);
            }
        });
    }

}
