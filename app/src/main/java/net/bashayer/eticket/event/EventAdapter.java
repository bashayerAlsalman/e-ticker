package net.bashayer.eticket.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.bashayer.eticket.R;
import net.bashayer.eticket.network.model.NewEventModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> implements Filterable {

    public static String url = "https://elmHackathon.s3.amazonaws.com/";

    public List<NewEventModel> events = new ArrayList<>();
    public List<NewEventModel> filteredEvents = new ArrayList<>();
    public Context context;
    public EventCallback callback;

    public EventAdapter(Context context, EventCallback callback, List<NewEventModel> eventList) {
        this.context = context;
        this.callback = callback;
        this.events.addAll(eventList);
        this.filteredEvents.addAll(eventList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(filteredEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredEvents.size();
    }

    public void updateEvents(List<NewEventModel> events) {

        this.events.clear();
        this.events.addAll(events);

        this.filteredEvents.clear();
        this.filteredEvents.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredEvents.addAll(events);
                } else {
                    List<NewEventModel> filteredList = new ArrayList<>();
                    for (NewEventModel event : events) {
                        //filteredEvents.addAll(events);
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        //
                        if (event.name.toLowerCase().contains(charString.toLowerCase()) || event.city.toLowerCase().contains(charString.toLowerCase()) || event.description.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(event);
                        }
                    }
                    filteredEvents = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredEvents;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //filteredEvents.addAll((ArrayList<EventModel>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.eventName)
        public AppCompatTextView name;
        @BindView(R.id.eventType)
        public AppCompatTextView type;
        @BindView(R.id.eventCity)
        public AppCompatTextView city;
        @BindView(R.id.eventIcon)
        public AppCompatImageView icon;
        @BindView(R.id.root)
        ConstraintLayout root;
        @BindView(R.id.isFreeIcon)
        AppCompatImageView isFreeIcon;

        public void bind(NewEventModel eventModel) {
            this.name.setText(eventModel.name);
            if (eventModel.eventImages != null && eventModel.eventImages.size() >= 1) {
                Glide.with(context).load(url + eventModel.eventImages.get(0).file_uiid).into(icon);
            }
            type.setText(eventModel.description);
            city.setText(eventModel.city);
            if (!eventModel.isFree) {
                isFreeIcon.setVisibility(View.INVISIBLE);
            }
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
