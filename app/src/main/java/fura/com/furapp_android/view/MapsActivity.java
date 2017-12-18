package fura.com.furapp_android.view;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fura.com.furapp_android.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    //region GLOBAL FIELDS
    private GoogleMap mMap;
    private double longitude, latitude;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Set value to map
        mMap = googleMap;
        //Gets the parameters from intent
        Intent mapIntent=getIntent();
        longitude=mapIntent.getDoubleExtra("longitude",0);
        latitude=mapIntent.getDoubleExtra("latitude",0);
        LatLng eventLocation = new LatLng(latitude, longitude);
        //Add marker
        mMap.addMarker(new MarkerOptions().position(eventLocation).title(this.getString(R.string.marker_message)));
        //Move camera to the location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eventLocation));
        //Zoom to show details
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
    }
}
