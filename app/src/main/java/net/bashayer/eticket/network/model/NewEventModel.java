package net.bashayer.eticket.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewEventModel implements Serializable {

    @SerializedName("event_id")
    @Expose
    public Integer eventId;
    @SerializedName("is_single")
    @Expose
    public Boolean isSingle;
    @SerializedName("is_free")
    @Expose
    public Boolean isFree;
    @SerializedName("normal_ticket_count")
    @Expose
    public Integer normalTicketCount;
    @SerializedName("latitude")
    @Expose
    public Double latitude;
    @SerializedName("Longtitude")
    @Expose
    public Double longtitude;
    @SerializedName("Vip_ticket_count")
    @Expose
    public Integer vipTicketCount;
    @SerializedName("vip_consumed_ticket")
    @Expose
    public Integer vipConsumedTicket;
    @SerializedName("notmal_consumed_ticket")
    @Expose
    public Integer notmalConsumedTicket;
    @SerializedName("collect_attende_info")
    @Expose
    public Boolean collectAttendeInfo;
    @SerializedName("notmal_ticket_price")
    @Expose
    public Integer notmalTicketPrice;
    @SerializedName("vip_ticket_price")
    @Expose
    public Integer vipTicketPrice;
    @SerializedName("changedDate")
    @Expose
    public String changedDate;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("event_images")
    @Expose
    public List<EventImage> eventImages = null;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("City")
    @Expose
    public String city;
    @SerializedName("event_date")
    @Expose
    public String eventDate;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("sponsers")
    @Expose
    public String sponsers;
    @SerializedName("EventType")
    @Expose
    public String eventType;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("location_description")
    @Expose
    public String locationDescription;
    @SerializedName("recuring_type")
    @Expose
    public String recuringType;


}
