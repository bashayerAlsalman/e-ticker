package net.bashayer.eticket.rate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.annotation.Nullable;

import net.bashayer.eticket.R;
import net.bashayer.eticket.common.BaseActivity;
import net.bashayer.eticket.event.EventListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RateActivity extends BaseActivity {

    @BindView(R.id.rating)
    RatingBar ratingBar;

    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_event);
        Intent intent = getIntent();
        ButterKnife.bind(this);

        ratingBar.setRating(5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRateDialog();
            }
        });
    }

    private void showRateDialog() {
        startActivity(new Intent(this, EventListActivity.class));
    }
}
