package net.bashayer.eticket.tickets;


import net.bashayer.eticket.network.model.EventModel;
import net.bashayer.eticket.tickets.models.ticketModel;

import androidx.cardview.widget.CardView;

public interface TicketCallback {
    void onTicketClicked(ticketModel ticket , int number );
}
