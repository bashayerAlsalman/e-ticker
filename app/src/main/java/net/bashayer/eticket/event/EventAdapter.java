package net.bashayer.eticket.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.bashayer.eticket.R;
import net.bashayer.eticket.network.model.EventModel;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    public List<EventModel> events;
    public Context context;
    public EventCallback callback;

    public EventAdapter(Context context, EventCallback callback, List<EventModel> eventList) {
        this.context = context;
        this.callback = callback;
        this.events = eventList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //todo
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateEvents(List<EventModel> events) {
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
