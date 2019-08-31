package net.bashayer.eticket.tickets.models;

import java.io.Serializable;

public class BookedTickets implements Serializable {

    String id;
    String type;
    String attendeName;
    String attendeeEmail;
    String attendeeMobile;
    double price;

    public BookedTickets(String id, String type, String attendeName, String attendeeEmail, String attendeeMobile) {
        this.id = id;
        this.type = type;
        this.attendeName = attendeName;
        this.attendeeEmail = attendeeEmail;
        this.attendeeMobile = attendeeMobile;
    }

    public BookedTickets(String id, String type) {
        this.id = id;
        this.type = type;
        this.attendeName = attendeName;
        this.attendeeEmail = attendeeEmail;
        this.attendeeMobile = attendeeMobile;
    }

    public BookedTickets(String id, String type, String attendeName, String attendeeEmail, double price) {
        this.id = id;
        this.type = type;
        this.attendeName = attendeName;
        this.attendeeEmail = attendeeEmail;
        this.price = price;
    }

    public BookedTickets(String id, String type, double price) {
        this.id = id;
        this.type = type;
        this.attendeName = attendeName;
        this.attendeeEmail = attendeeEmail;
        this.price = price;
    }


    public BookedTickets() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttendeName() {
        return attendeName;
    }

    public void setAttendeName(String attendeName) {
        this.attendeName = attendeName;
    }

    public String getAttendeeEmail() {
        return attendeeEmail;
    }

    public void setAttendeeEmail(String attendeeEmail) {
        this.attendeeEmail = attendeeEmail;
    }

    public String getAttendeeMobile() {
        return attendeeMobile;
    }

    public void setAttendeeMobile(String attendeeMobile) {
        this.attendeeMobile = attendeeMobile;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookedTickets{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", attendeName='" + attendeName + '\'' +
                ", attendeeEmail='" + attendeeEmail + '\'' +
                ", attendeeMobile='" + attendeeMobile + '\'' +
                '}';
    }
}
