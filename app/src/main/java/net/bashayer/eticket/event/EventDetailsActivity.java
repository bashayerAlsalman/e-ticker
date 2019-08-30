package net.bashayer.eticket.event;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;

import net.bashayer.eticket.R;
import net.bashayer.eticket.common.BaseActivity;
import net.bashayer.eticket.network.model.EventModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailsActivity extends BaseActivity {


    private EventModel eventModel;

    @BindView(R.id.slider)
    SliderLayout slider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        ButterKnife.bind(this);
        eventModel = (EventModel) getIntent().getSerializableExtra(EventListActivity.EVENT_KEY);
        initImageSlider();
        initGoogleMap();
    }

    private void initGoogleMap() {
//    todo    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//xml android:name="com.google.android.gms.maps.SupportMapFragment"
    }

//    @Override todo
//    public void onMapReady(GoogleMap googleMap) {
//        // Add a marker in Sydney, Australia,
//        // and move the map's camera to the same location.
//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions().position(sydney)
//                .title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }

    private void initImageSlider() {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        for (String image : eventModel.images) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.image(image)
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true);

            slider.addSlider(textSliderView);

        }
        slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(4000);
//        slider.addOnPageChangeListener(this);
    }
}
