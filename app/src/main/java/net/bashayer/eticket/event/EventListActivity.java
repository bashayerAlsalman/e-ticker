package net.bashayer.eticket.event;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.bashayer.eticket.R;
import net.bashayer.eticket.network.EventService;
import net.bashayer.eticket.network.UnsafeOkHttpClient;
import net.bashayer.eticket.network.model.EventModel;
import net.bashayer.eticket.network.model.NewEventModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListActivity extends AppCompatActivity implements EventCallback {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.search)
    SearchView search;
    public EventAdapter adapter;
    public static String EVENT_KEY = "eventKey";
    private List<EventModel> eventModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle(R.string.evens);
        initAdapter();
        loadData();
        //loadMockData();
        initSearch();
    }

    private void initSearch() {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void loadMockData() {
//        eventModels = new ArrayList<>();
//        EventModel eventModel = new EventModel();
//
//        eventModels.add(eventModel);
//        eventModels.add(eventModel);
//        eventModels.add(eventModel);
//        eventModels.add(eventModel);
//        eventModels.add(eventModel);
//
//        adapter.updateEvents(eventModels);
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new EventAdapter(this, this, new ArrayList<NewEventModel>());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEventClicked(NewEventModel event) {
        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra(EVENT_KEY, event);

        startActivity(intent);
    }

    private void loadData() {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        //https://e-ticketing-sandbox.mxapps.io/rest/event/v1/event
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://e-ticketing-sandbox.mxapps.io/rest/event/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        EventService service = retrofit.create(EventService.class);

        service.getEvents("application/json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewEventModel>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<NewEventModel> value) {
                         adapter.updateEvents(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


}
