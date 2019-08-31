package net.bashayer.eticket.payment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import net.bashayer.eticket.R;
import net.bashayer.eticket.network.model.NewEventModel;
import net.bashayer.eticket.qr.GenerateQrCodeActivity;
import net.bashayer.eticket.tickets.models.Booking;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        //Getting Intent
        Intent intent = getIntent();
        Booking booking = (Booking) getIntent().getSerializableExtra("booking");
        NewEventModel eventModel = (NewEventModel) getIntent().getSerializableExtra("event");


        Intent intent1 = new Intent(this, GenerateQrCodeActivity.class);
        intent1.putExtra("booking", booking);
        intent1.putExtra("event", eventModel);
        startActivity(intent1);
        finish();
//
//        try {
//            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
//
//            //Displaying payment details
//            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
//        } catch (JSONException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
//            //Views
//            TextView textViewId = (TextView) findViewById(R.id.paymentId);
//            TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
//            TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);
//
//            //Showing the details from json object
//            textViewId.setText(jsonDetails.getString("id"));
//            textViewStatus.setText(jsonDetails.getString("state"));
//            textViewAmount.setText(paymentAmount+" USD");


    }
}
/**/
