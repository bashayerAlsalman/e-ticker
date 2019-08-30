package net.bashayer.eticket.network.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventModel implements Serializable {



    public EventModel() {
        //default constructor
    }

    public List<String> images = new ArrayList<String>();
    public double latitude = 0.0;
    public double longitude = 0.0;
    public String name;
    public String description;
    public String city;
    public Date date = new Date();


//    public EventModel(String ima) {
//        images.add("https://android-bus-ticket-sale-software.com/en/assets/images/image01-2-671x397.png");
//        images.add("https://android-bus-ticket-sale-software.com/en/assets/images/image01-2-671x397.png");
//        images.add("https://www.techotopia.com/images/7/7b/Android_master_detail_small_screen.png");
//    }
//
//    public List<String> images = new ArrayList<String>();
//    public double latitude = 24.714432;
//    public double longitude = 46.672001;
//    public String name = "Elm Hackathon";
//    public String description = "MVP hackathon for elm employees";
//    public String city = "Riyadh";
//    public Date date = new Date();
//
//
}
