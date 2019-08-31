package net.bashayer.eticket.event;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.bashayer.eticket.R;
import net.bashayer.eticket.common.BaseActivity;
import net.bashayer.eticket.network.model.EventImage;
import net.bashayer.eticket.network.model.EventModel;
import net.bashayer.eticket.network.model.NewEvent;
import net.bashayer.eticket.qr.GenerateQrCodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailsActivity extends BaseActivity implements OnMapReadyCallback {


    private NewEvent eventModel;

    @BindView(R.id.slider)
    SliderLayout slider;

    @BindView(R.id.button)
    Button button;

    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        ButterKnife.bind(this);
        eventModel = (NewEvent) getIntent().getSerializableExtra(EventListActivity.EVENT_KEY);
        getSupportActionBar().setTitle(eventModel.eventName);
        initImageSlider();
        initGoogleMap();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToETickets();
            }
        });
    }

    private void goToETickets() {
        Intent intent = new Intent(this, GenerateQrCodeActivity.class);
       // intent.putExtra("","");

        startActivity(intent);

    }

    private void initGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void initImageSlider() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        for (EventImage image : eventModel.eventImages) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.image(image.url)
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true);

            slider.addSlider(textSliderView);

        }
        slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(4000);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location = new LatLng(eventModel.latitude, eventModel.longitude);
        mMap.addMarker(new MarkerOptions().position(location).title(eventModel.eventName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.setMinZoomPreference(15.0f);
        mMap.setMaxZoomPreference(20.0f);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                onMapClicked();
                return false;
            }
        });
    }

    private void onMapClicked() {
        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=" + eventModel.latitude + "," + eventModel.longitude);

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }
}
