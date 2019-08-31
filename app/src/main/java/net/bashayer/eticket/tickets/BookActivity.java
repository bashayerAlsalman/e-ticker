package net.bashayer.eticket.tickets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import net.bashayer.eticket.R;
import net.bashayer.eticket.common.BaseActivity;
import net.bashayer.eticket.tickets.models.BookedTickets;
import net.bashayer.eticket.tickets.models.Booking;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.booking)
    MaterialButton book;
    @BindView(R.id.attendeeName)
    TextInputEditText name;
    @BindView(R.id.attendeeEmail)
    TextInputEditText email;

    public Booking booking = new Booking();
    public ArrayList<BookedTickets> bookedTickets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Intent intent = getIntent();
        bookedTickets = (ArrayList<BookedTickets>) intent.getSerializableExtra("tickets");
        ButterKnife.bind(this);
        book.setOnClickListener(this);

        System.out.println("tickets--" +bookedTickets);


    }

    @Override
    public void onClick(View view) {
        String bookName = name.getText().toString();
        String bookedEmail = email.getText().toString();
        for ( BookedTickets bookedTicket : bookedTickets)
        {bookedTicket.setAttendeName(bookName);
        bookedTicket.setAttendeeEmail(bookedEmail);
        System.out.println("------booked ticket "+ bookedTicket);

        }

        booking.setTickets(bookedTickets);
        System.out.println("booking ---------- " + booking);
        // TODO: call post service



    }
}
