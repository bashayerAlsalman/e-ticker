package net.bashayer.eticket.tickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.bashayer.eticket.MainActivity;
import net.bashayer.eticket.R;
import net.bashayer.eticket.event.EventCallback;
import net.bashayer.eticket.tickets.models.ticketModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ticketAdapter extends RecyclerView.Adapter<ticketAdapter.ViewHolder> {

    public List<ticketModel> ticketModels;
    public Context context;
    public TicketCallback callback;

    public ticketAdapter(Context context, TicketCallback callback, List<ticketModel> ticketModelList) {
        this.context = context;
        this.callback = callback;
        this.ticketModels = ticketModelList;
    }

    @NonNull
    @Override
    public ticketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ticketAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_attendee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ticketAdapter.ViewHolder holder, int position) {
        holder.bind(ticketModels.get(position));
    }

    @Override
    public int getItemCount() {
        return ticketModels.size();
    }

    public void updateTickets(List<ticketModel> ticketModels) {
        this.ticketModels.clear();
        this.ticketModels.addAll(ticketModels);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ticketType)
        public AppCompatTextView ticketType;
        @BindView(R.id.ticketPrice)
        public AppCompatTextView ticketPrice;
        @BindView(R.id.numberPicker)
        public NumberPicker numberPicker;
        @BindView(R.id.ticketPriceValue)
        public AppCompatTextView ticketPriceValue;
        @BindView(R.id.ticketTypeValue)
        public AppCompatTextView ticketTypeValue;

        @BindView(R.id.ticketTotalValue)
        public AppCompatTextView ticketTotalValue;


        @BindView(R.id.root)
        CardView root;
        int maxTickets = 5;
        int minTickets = 1;
        int total = 0;
        double price ;



        public void bind(ticketModel ticketModel) {
            this.ticketTypeValue.setText(ticketModel.type);
            this.ticketPriceValue.setText((ticketModel.price + ""));
            price = ticketModel.price;
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onTicketClicked(ticketModels.get(getAdapterPosition()));
                }
            });
            if (maxTickets > ticketModel.seats)
                maxTickets = (int) ticketModel.seats;
            numberPicker.setMaxValue(maxTickets);
            numberPicker.setMinValue(minTickets);
            numberPicker.setOnValueChangedListener(onValueChangeListener);


        }

        NumberPicker.OnValueChangeListener onValueChangeListener =
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        Toast.makeText(context, "selected number " + numberPicker.getValue(), Toast.LENGTH_LONG);
                        total = numberPicker.getValue() * (int) price;
                        ticketTotalValue.setText(total+"");
                        System.out.println("total ---------- " + total);



                    }

//
                };
                    public ViewHolder(@NonNull View itemView) {
                        super(itemView);
                        ButterKnife.bind(this, itemView);
                    }
                }


    }