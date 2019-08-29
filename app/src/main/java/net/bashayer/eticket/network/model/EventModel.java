package net.bashayer.eticket.network.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventModel implements Serializable {


    public EventModel(String ima) {
        images.add("https://android-bus-ticket-sale-software.com/en/assets/images/image01-2-671x397.png");
    }

    public List<String> images = new ArrayList<String>();
    public String name = "dance night";
    public String description = "every one will dance on a dead body";
    public Date date = new Date();


}
