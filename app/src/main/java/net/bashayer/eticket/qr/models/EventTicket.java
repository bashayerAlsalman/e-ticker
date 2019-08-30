package net.bashayer.eticket.qr.models;

public class EventTicket {

   public String type;
   public double price;
   public double noSeats;

    public EventTicket(String type, double price, double noSeats) {
        this.type = type;
        this.price = price;
        this.noSeats = noSeats;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public double getNoSeats() {
        return noSeats;
    }


    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNoSeats(double noSeats) {
        this.noSeats = noSeats;
    }
}
