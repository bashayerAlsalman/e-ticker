package net.bashayer.eticket.tickets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import net.bashayer.eticket.R;
import net.bashayer.eticket.common.BaseActivity;
import net.bashayer.eticket.event.EventAdapter;
import net.bashayer.eticket.network.BookService;
import net.bashayer.eticket.network.UnsafeOkHttpClient;
import net.bashayer.eticket.network.model.NewEventModel;
import net.bashayer.eticket.payment.PaymentAactivity;
import net.bashayer.eticket.tickets.models.BookedTickets;
import net.bashayer.eticket.tickets.models.Booking;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.booking)
    MaterialButton book;
    @BindView(R.id.attendeeName)
    TextInputEditText name;
    @BindView(R.id.attendeeEmail)
    TextInputEditText email;

    @BindView(R.id.eventIcon)
    AppCompatImageView eventIcon;

    public Booking booking;
    public NewEventModel eventModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Intent intent = getIntent();
        booking = (Booking) intent.getSerializableExtra("booking");
        eventModel = (NewEventModel) intent.getSerializableExtra("event");

        getSupportActionBar().setTitle(eventModel.name);
        ButterKnife.bind(this);

        if (eventModel.eventImages != null && eventModel.eventImages.size() >= 1) {
            Glide.with(this).load(EventAdapter.url + eventModel.eventImages.get(0).file_uiid).into(eventIcon);
        }

        book.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String bookName = name.getText().toString();
        String bookedEmail = email.getText().toString();
        for (BookedTickets bookedTicket : booking.getTickets()) {
            bookedTicket.setAttendeName(bookName);
            bookedTicket.setAttendeeEmail(bookedEmail);
            System.out.println("------booked ticket " + bookedTicket);

        }
        System.out.println("booking ---------- " + booking);
        // TODO: call post service
        //todo set event id
        postData();


    }

    private void nextPayment() {
        Intent intent = new Intent(this, PaymentAactivity.class);
        intent.putExtra("booking", booking);
        intent.putExtra("event", eventModel);
        //intent.putExtra("clientId", clientId);
        startActivity(intent);
    }

    private void postData() {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://e-ticketing-sandbox.mxapps.io/rest/booking/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        BookService service = retrofit.create(BookService.class);

        service.postBooking("application/json", booking)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        nextPayment();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

}
