package net.bashayer.eticket.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewEvent implements Serializable {

    @SerializedName("event_id")
    @Expose
    public Integer eventId;
    @SerializedName("is_single")
    @Expose
    public Boolean isSingle;
    @SerializedName("is_free")
    @Expose
    public Boolean isFree;
    @SerializedName("changedDate")
    @Expose
    public String changedDate;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("event_images")
    @Expose
    public List<EventImage> eventImages = new ArrayList<>();

    public double latitude = 24.714432;//todo
    public double longitude = 46.672001;
    public String eventName = "event name";

    @Override
    public String toString() {
        return "NewEvent{" +
                "eventId=" + eventId +
                ", isSingle=" + isSingle +
                ", isFree=" + isFree +
                ", changedDate='" + changedDate + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", eventImages=" + eventImages +
                '}';
    }
}
