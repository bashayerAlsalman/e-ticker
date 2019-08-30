package net.bashayer.eticket.tickets;

import android.content.Intent;
import android.os.Bundle;

import net.bashayer.eticket.R;
import net.bashayer.eticket.event.EventAdapter;
import net.bashayer.eticket.event.EventCallback;
import net.bashayer.eticket.event.EventDetailsActivity;
import net.bashayer.eticket.network.EventService;
import net.bashayer.eticket.network.model.EventModel;
import net.bashayer.eticket.tickets.models.ticketModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class TicketListActivity extends AppCompatActivity implements TicketCallback {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
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
    }

    private void loadMockData() {
        List<ticketModel> ticketModels = new ArrayList<>();
        ticketModel ticketModel = new ticketModel("eee", 100,100);

        ticketModels.add(ticketModel);
        ticketModels.add(ticketModel);
        ticketModels.add(ticketModel);
        ticketModels.add(ticketModel);
        ticketModels.add(ticketModel);

        adapter.updateTickets(ticketModels);
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ticketAdapter(this, this, new ArrayList<ticketModel>());
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


    @Override
    public void onTicketClicked(ticketModel ticket) {
        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra(TICKET_KEY, ticket);

        startActivity(intent);

    }
}
