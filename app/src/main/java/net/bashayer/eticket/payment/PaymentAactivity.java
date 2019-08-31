package net.bashayer.eticket.payment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import net.bashayer.eticket.R;
import net.bashayer.eticket.network.model.NewEventModel;
import net.bashayer.eticket.qr.GenerateQrCodeActivity;
import net.bashayer.eticket.tickets.models.BookedTickets;
import net.bashayer.eticket.tickets.models.Booking;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.bashayer.eticket.event.EventListActivity.MY_PREFS_NAME;

public class PaymentAactivity extends AppCompatActivity implements View.OnClickListener {

    //The views
    @BindView(R.id.buttonPay)
    Button buttonPay;

//    @BindView(R.id.editTextAmount)
//    EditText editTextAmount;

    @BindView(R.id.vipCount)
    TextView vipCount;

    @BindView(R.id.vipSinglePrice)
    TextView vipSinglePrice;

    @BindView(R.id.vipTotalPrice)
    TextView vipTotalPrice;

    @BindView(R.id.platTicketCount)
    TextView platTicketCount;

    @BindView(R.id.platSinglePrice)
    TextView platSinglePrice;

    @BindView(R.id.platTotalPrice)
    TextView platTotalPrice;


    @BindView(R.id.allTicketPrice)
    TextView allTicketPrice;


    ArrayList<BookedTickets> bookedTickets = new ArrayList<>();
    ArrayList<BookedTickets> bookedTicketsVIP = new ArrayList<>();
    ArrayList<BookedTickets> bookedTicketsPlatinum = new ArrayList<>();
    double vipTotal = 0;
    double platinumTotal = 0;
    double totalAmount = 0;
    //Payment Amount
    private String paymentAmount;


    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    Booking booking;
    NewEventModel eventModel;
    //Paypal Configuration Object
    private static PayPalConfiguration config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ButterKnife.bind(this);

        booking = (Booking) getIntent().getSerializableExtra("booking");
        eventModel = (NewEventModel) getIntent().getSerializableExtra("event");

        //  String clientId = getIntent().getStringExtra("clientId");
        buttonPay.setOnClickListener(this);

        String vipPrice = "";
        String platPrice = "";

        ArrayList<BookedTickets> bookedTickets = booking.getTickets();
        for (BookedTickets bookedTicket : bookedTickets) {
            if (bookedTicket.getType().equals("VIP")) {
                vipPrice = bookedTicket.getPrice() + "";
                bookedTicketsVIP.add(bookedTicket);
                vipTotal += bookedTicket.getPrice();
            }
            if (bookedTicket.getType().equals("Platinum")) {
                platPrice = bookedTicket.getPrice() + "";

                bookedTicketsPlatinum.add(bookedTicket);
                platinumTotal += bookedTicket.getPrice();
            }
        }

        totalAmount = vipTotal + platinumTotal;


        vipCount.setText(bookedTicketsVIP.size() + "");
        vipSinglePrice.setText(vipPrice + "");
        vipTotalPrice.setText(vipTotal + "");

        platTicketCount.setText(bookedTicketsPlatinum.size() + "");
        platSinglePrice.setText(platPrice + "");
        platTotalPrice.setText(platinumTotal + "");

        System.out.println("--------- price " + totalAmount);

        allTicketPrice.setText(totalAmount + "");
        paymentAmount = totalAmount + "";

        config = new PayPalConfiguration()
                // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
                // or live (ENVIRONMENT_PRODUCTION)
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                //  .clientId(clientId)
                .clientId(PayPalConfig.PAYPAL_CLIENT_ID)
                .acceptCreditCards(false);


        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra("booking", booking);
        intent.putExtra("event", eventModel);

        startService(intent);

        Intent n = new Intent(this, GenerateQrCodeActivity.class);
        n.putExtra("booking", booking);
        n.putExtra("event", eventModel);

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        Gson gson = new Gson();

        editor.putString("booking", gson.toJson(booking));
        editor.putString("event", gson.toJson(eventModel));

        editor.commit();
    }


    @Override
    public void onClick(View view) {
        getPayment();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }


    private void getPayment() {
        //Creating a paypalpayment

        PayPalPayment payment = new PayPalPayment(new BigDecimal(totalAmount), "USD", "E-Ticketing",
                PayPalPayment.PAYMENT_INTENT_SALE);
        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }


}
