package net.bashayer.eticket.event;


import net.bashayer.eticket.network.model.NewEventModel;

public interface EventCallback {
    void onEventClicked(NewEventModel event);
}
