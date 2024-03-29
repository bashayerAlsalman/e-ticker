package net.bashayer.eticket.network;


import net.bashayer.eticket.network.model.NewEventModel;
import net.bashayer.eticket.tickets.models.Booking;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EventService {

    @GET("event")
    Observable<List<NewEventModel>> getEvents(@Header("accept") String header);


//    @GET("event")
//        //@Headers("accept:application/json")
//    Observable<List<NewEventModel>> getEvents(@Header("accept") String header);
//
}
