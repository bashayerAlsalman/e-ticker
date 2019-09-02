package net.bashayer.eticket.network.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventImage implements Serializable {
    @SerializedName("FileID")
    @Expose
    public Integer fileID;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Size")
    @Expose
    public Integer size;
    @SerializedName("file_uuid")
    @Expose
    public String file_uiid;
}