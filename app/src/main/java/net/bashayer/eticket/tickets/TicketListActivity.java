package net.bashayer.eticket.tickets;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import net.bashayer.eticket.R;
import net.bashayer.eticket.event.EventAdapter;
import net.bashayer.eticket.event.EventCallback;
import net.bashayer.eticket.event.EventDetailsActivity;
import net.bashayer.eticket.network.EventService;
import net.bashayer.eticket.network.model.EventModel;
import net.bashayer.eticket.network.model.NewEventModel;
import net.bashayer.eticket.tickets.models.BookedTickets;
import net.bashayer.eticket.tickets.models.Booking;
import net.bashayer.eticket.tickets.models.ticketModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketListActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView((R.id.addTickets))
    MaterialButton addTickets;
    public ticketAdapter adapter;
    public static String TICKET_KEY = "ticketKey";
    NewEventModel newEventModel;

    ticketModel vipTicketModel;
    ticketModel platTicketModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        ButterKnife.bind(this);

        newEventModel = (NewEventModel) getIntent().getSerializableExtra("event");
        initAdapter();
        loadMockData();
        addTickets.setOnClickListener(this);
    }

    private void loadMockData() {
        List<ticketModel> ticketModels = new ArrayList<>();

        int vipSeats = newEventModel.vipTicketCount - newEventModel.vipConsumedTicket;
        int platinumSeats = newEventModel.normalTicketCount - newEventModel.notmalConsumedTicket;

        vipTicketModel = new ticketModel("VIP", newEventModel.vipTicketPrice, vipSeats);
        platTicketModel = new ticketModel("Platinum", newEventModel.notmalTicketPrice, platinumSeats);

        ticketModels.add(vipTicketModel);
        ticketModels.add(platTicketModel);

        adapter.updateTickets(ticketModels, addTickets);
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ticketAdapter(this, new ArrayList<ticketModel>());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {

        ArrayList<BookedTickets> bookedTickets = new ArrayList<>();
        System.out.println("before open Activity selected " + this.adapter.selected);


        for (int i = 0; i < this.adapter.totalVIP; i++) {
            String uniqueID = UUID.randomUUID().toString();
            bookedTickets.add(new BookedTickets(uniqueID, "VIP", vipTicketModel.price));
        }

        for (int i = 0; i < this.adapter.totalP; i++) {
            String uniqueID = UUID.randomUUID().toString();
            bookedTickets.add(new BookedTickets(uniqueID, "Platinum", platTicketModel.price));
        }

//        for (ticketModel ticket : this.adapter.selected)
//            for (int i = 0; i <= ticket.selectedQuantity; i++) {
//                String uniqueID = UUID.randomUUID().toString();
//                bookedTickets.add(new BookedTickets(uniqueID, ticket.type, ticket.price));
//            }


        System.out.println("before open Activity " + bookedTickets);
        Booking booking = new Booking(newEventModel.eventId, bookedTickets);


        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("booking", booking);
        startActivity(intent);

    }
}
