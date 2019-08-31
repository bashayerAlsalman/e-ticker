package net.bashayer.eticket.qr.eticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import net.bashayer.eticket.R;
import net.bashayer.eticket.qr.TicketCallback;
import net.bashayer.eticket.qr.models.AttendeeTicket;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ETicketAdapter extends RecyclerView.Adapter<ETicketAdapter.ViewHolder> {

    public List<AttendeeTicket> attendeeTickets;
    public Context context;
    public TicketCallback callback;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yy");

    public ETicketAdapter(Context context, TicketCallback callback, List<AttendeeTicket> attendeeTickets) {
        this.context = context;
        this.callback = callback;
        this.attendeeTickets = attendeeTickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ETicketAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(attendeeTickets.get(position));
    }

    @Override
    public int getItemCount() {
        return attendeeTickets.size();
    }

    public void updateEvents(List<AttendeeTicket> attendeeTickets) {
        this.attendeeTickets.clear();
        this.attendeeTickets.addAll(attendeeTickets);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.attendeeQRCodeImage)
        public AppCompatImageView qrCodeImage;

        @BindView(R.id.root)
        public CardView cardView;

        @BindView(R.id.eventCity)
        AppCompatTextView eventCity;

        @BindView(R.id.eventType)
        AppCompatTextView eventName;


        @BindView(R.id.attendeeTicket)
        AppCompatTextView attendeeName;

        @BindView(R.id.eventDate)
        AppCompatTextView eventDate;


        public void bind(AttendeeTicket attendeeTicket) {
            this.qrCodeImage.setImageBitmap(attendeeTicket.getQRCodeImage());
            this.eventCity.setText(attendeeTicket.getCity());
            this.attendeeName.setText(attendeeTicket.getEventAttendeeName());
            this.eventName.setText(attendeeTicket.getEventName());
            this.eventDate.setText(simpleDateFormat.format(attendeeTicket.getEventDate()));
            this.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onTicketClicked(view);
                }
            });
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
