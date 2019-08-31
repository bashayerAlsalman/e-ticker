package net.bashayer.eticket.tickets.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Booking implements Serializable {

Integer eventId;

ArrayList<BookedTickets> tickets;

    public Booking(ArrayList<BookedTickets> tickets) {
        this.tickets = tickets;
    }

    public Booking(Integer eventId, ArrayList<BookedTickets> tickets) {
        this.eventId = eventId;
        this.tickets = tickets;
    }

    public Booking() {
    }

    public Integer getEventId() {
        return eventId;
    }

    public ArrayList<BookedTickets> getTickets() {
        return tickets;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public void setTickets(ArrayList<BookedTickets> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "eventId=" + eventId +
                ", tickets=" + tickets +
                '}';
    }
}
