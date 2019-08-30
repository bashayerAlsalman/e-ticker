package net.bashayer.eticket.tickets;


import net.bashayer.eticket.network.model.EventModel;
import net.bashayer.eticket.tickets.models.ticketModel;

public interface TicketCallback {
    void onTicketClicked(ticketModel ticket);
}
