package net.bashayer.eticket.event;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.bashayer.eticket.R;
import net.bashayer.eticket.network.EventService;
import net.bashayer.eticket.network.model.EventModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListActivity extends AppCompatActivity implements EventCallback {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public EventAdapter adapter;
    public static String EVENT_KEY = "eventKey";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ButterKnife.bind(this);
        initAdapter();
        //todo loadData();
        loadMockData();
    }

    private void loadMockData() {
        List<EventModel> eventModels = new ArrayList<>();
        EventModel eventModel = new EventModel("");

        eventModels.add(eventModel);
        eventModels.add(eventModel);
        eventModels.add(eventModel);
        eventModels.add(eventModel);
        eventModels.add(eventModel);

        adapter.updateEvents(eventModels);
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new EventAdapter(this, this, new ArrayList<EventModel>());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEventClicked(EventModel event) {
        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra(EVENT_KEY, event);

        startActivity(intent);
    }

    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        EventService service = retrofit.create(EventService.class);

        service.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<EventModel>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<EventModel> value) {
                        adapter.updateEvents(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //todo
                    }

                    @Override
                    public void onComplete() {
                        //todo
                    }
                });

    }


}
