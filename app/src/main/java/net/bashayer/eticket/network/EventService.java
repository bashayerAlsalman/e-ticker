package net.bashayer.eticket.network;


import net.bashayer.eticket.network.model.NewEvent;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface EventService {

    @GET("event")
        //@Headers("accept:application/json")
    Observable<List<NewEvent>> getEvents(@Header("accept") String header);
}
