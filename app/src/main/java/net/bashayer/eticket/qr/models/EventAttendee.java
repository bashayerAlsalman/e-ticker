package net.bashayer.eticket.qr.models;

import java.util.Date;

public class EventAttendee {

    public String eventId;
    public String eventAttendeeName;
    public String eventName;
    public String eventDescription;
    public String city;
    public Date eventDate;

    public EventAttendee(String eventName, String eventAttendeeName) {
        this.eventName = eventName;
        this.eventAttendeeName = eventAttendeeName;
    }

    public EventAttendee(String eventId, String eventAttendeeName, String eventName, String eventDescription, String city, Date eventDate) {
        this.eventId = eventId;
        this.eventAttendeeName = eventAttendeeName;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.city = city;
        this.eventDate = eventDate;
    }
}