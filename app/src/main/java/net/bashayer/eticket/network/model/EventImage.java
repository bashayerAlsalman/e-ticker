package net.bashayer.eticket.network.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventImage {

    @SerializedName("FileID")
    @Expose
    public Integer fileID;
    @SerializedName("Name")
    @Expose
    public String name;

    //todo @SerializedName("Name")
    @Expose
    public String url = "https://android-bus-ticket-sale-software.com/en/assets/images/image01-2-671x397.png";

}