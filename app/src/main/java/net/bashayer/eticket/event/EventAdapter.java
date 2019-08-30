package net.bashayer.eticket.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.bashayer.eticket.R;
import net.bashayer.eticket.network.model.EventModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        holder.bind(events.get(position));
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
        @BindView(R.id.eventName)
        public AppCompatTextView name;
        @BindView(R.id.eventType)
        public AppCompatTextView type;
        @BindView(R.id.eventIcon)
        public AppCompatImageView icon;
        @BindView(R.id.root)
        ConstraintLayout root;

        public void bind(EventModel eventModel) { //todo change this
            this.name.setText(eventModel.name);
            Glide.with(context).load(eventModel.images.get(0)).into(icon);
            type.setText(eventModel.description);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onEventClicked(events.get(getAdapterPosition()));
                }
            });
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
