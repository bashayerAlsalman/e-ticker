package net.bashayer.eticket.network;


import net.bashayer.eticket.network.model.EventModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface EventService {

    @GET("v2/5d1e362a30000058b6d72565/")
    Observable<List<EventModel>> getEvents();
}
