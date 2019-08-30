package net.bashayer.eticket.tickets.models;

import java.io.Serializable;

public class ticketModel implements Serializable {
  public   String type;
  public double price;
  public double seats;


    public ticketModel(String type, double price, double seats) {
        this.type = type;
        this.price = price;
        this.seats = seats;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSeats(double seats) {
        this.seats = seats;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public double getSeats() {
        return seats;
    }
}
