package net.bashayer.eticket.tickets.models;

import java.io.Serializable;

public class ticketModel implements Serializable {
  public   String type;
  public double price;
  public double seats;
  public double selectedQuantity;


    public ticketModel(String type, double price, double seats) {
        this.type = type;
        this.price = price;
        this.seats = seats;
    }


    public ticketModel(String type, double price) {
        this.type = type;
        this.price = price;

    }
    public ticketModel() {
    }

    public void setSelectedQuantity(double selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public double getSelectedQuantity() {
        return selectedQuantity;
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
