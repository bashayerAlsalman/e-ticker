package net.bashayer.eticket.network;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TicketService {

    @GET("verification?tiketid={id}")
        //http://localhost:8080/rest/verfication/v1/verification?tiketid=123
    Observable<Boolean> verifyTicket(@Header("accept") String header, @Path("id") String id);
}
