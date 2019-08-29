package net.bashayer.eticket.event;


import net.bashayer.eticket.network.model.EventModel;

public interface EventCallback {
    void onEventClicked(EventModel event);
}
