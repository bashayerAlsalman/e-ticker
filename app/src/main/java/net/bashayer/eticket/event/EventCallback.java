package net.bashayer.eticket.event;


import net.bashayer.eticket.network.model.NewEvent;

public interface EventCallback {
    void onEventClicked(NewEvent event);
}
