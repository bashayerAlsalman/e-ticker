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
import net.bashayer.eticket.tickets.models.BookedTickets;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        ButterKnife.bind(this);
        initAdapter();
        //todo loadData();
        loadMockData();
        addTickets.setOnClickListener(this);
    }

    private void loadMockData() {
        List<ticketModel> ticketModels = new ArrayList<>();
        ticketModel ticketModel = new ticketModel("VIP", 100,100);
        ticketModel ticketModel2 = new ticketModel("Platinum", 50,100);
        ticketModel ticketModel3 = new ticketModel("Gold", 40,100);

        ticketModels.add(ticketModel);
        ticketModels.add(ticketModel2);
        ticketModels.add(ticketModel3);


        adapter.updateTickets(ticketModels , addTickets);

    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ticketAdapter(this , new ArrayList<ticketModel>());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }



    private void loadData() {

        //


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://www.mocky.io/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//        EventService service = retrofit.create(EventService.class);
//
//        service.getEvents()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<ticketModel>>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<ticketModel> value) {
//                        adapter.updateTickets(value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        //todo
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        //todo
//                    }
//                });

    }


//    @Override
//    public void onTicketClicked(ticketModel ticket , int number ) {
//
//        System.out.println("---------"+number);
//
//        Intent intent = new Intent(this, BookActivity.class);
//        intent.putExtra(TICKET_KEY, ticket);
//
//        startActivity(intent);
//
//    }


    @Override
    public void onClick(View view) {

        ArrayList<BookedTickets> bookedTickets = new ArrayList<>();
        for(ticketModel ticket : this.adapter.selected)
            for (int i = 0; i < ticket.selectedQuantity; i++) {
                String uniqueID = UUID.randomUUID().toString();
                bookedTickets.add( new BookedTickets(uniqueID , ticket.type));

            }

        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("tickets",bookedTickets);
        startActivity(intent);

    }
}
