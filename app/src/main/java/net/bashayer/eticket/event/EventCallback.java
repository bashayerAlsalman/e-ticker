package net.bashayer.eticket.event;

import net.bashayer.eticket.event.model.Event;

import java.util.List;

public interface EventCallback {
    void onEventsLoaded(List<Event> events);
}
