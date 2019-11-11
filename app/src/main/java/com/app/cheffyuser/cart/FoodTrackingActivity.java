package com.app.cheffyuser.cart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cheffyuser.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FoodTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracking);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(FoodTrackingActivity.this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng latLng2 = new LatLng(22.365973, 91.828789);
        LatLng latLng = new LatLng(22.330370, 91.832626);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)).position(latLng));

        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker2)).position(latLng2));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));

        //MarkerOptions marker = new MarkerOptions().position(latLng);

       // new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));


        //for normal map view
        mMap.setMapType(googleMap.MAP_TYPE_NORMAL);

        //for sattelite map view
        // mMap.setMapType(googleMap.MAP_TYPE_SATELLITE);

        //for add zoom in maps
        float zoomLevel = 13.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

//        Polyline line = mMap.addPolyline(new PolylineOptions()
//                .add(new LatLng(22.365973, 91.828789), new LatLng(22.330370, 91.832626))
//                .width(5)
//                .color(Color.BLACK));
    }
}
