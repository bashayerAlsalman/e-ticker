package net.bashayer.eticket.tickets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import net.bashayer.eticket.R;
import net.bashayer.eticket.qr.GenerateQrCodeActivity;
import net.bashayer.eticket.tickets.models.BookedTickets;
import net.bashayer.eticket.tickets.models.ticketModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ticketAdapter extends RecyclerView.Adapter<ticketAdapter.ViewHolder> {

    public List<ticketModel> ticketModels;
    public Context context;
    public TicketCallback callback;
    public ArrayList<ticketModel> selected = new ArrayList<>();

    int maxTickets = 5;
    int minTickets = 0;
    int total = 0;
    double price;
    String type;
    public int totalVIP = 0;
    public int totalP = 0;


    @BindView((R.id.addTickets))
    MaterialButton addTickets;

    public ticketAdapter(Context context, List<ticketModel> ticketModelList) {
        this.context = context;
        // this.callback = callback;
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

    public void updateTickets(List<ticketModel> ticketModels, MaterialButton addTickets) {
        this.ticketModels.clear();
        this.ticketModels.addAll(ticketModels);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.ticketType)
//        public AppCompatTextView ticketType;
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

        public void bind(ticketModel ticketModel) {
            this.ticketTypeValue.setText(ticketModel.type);
            this.ticketPriceValue.setText((ticketModel.price + ""));
            price = ticketModel.price;
            type = ticketModel.type;

            System.out.println("max" + maxTickets);

            if (ticketModel.seats > 0) {
                if (maxTickets > ticketModel.seats)
                    maxTickets = (int) ticketModel.seats;
                numberPicker.setMaxValue(maxTickets);
            }

            numberPicker.setMinValue(minTickets);
            numberPicker.setOnValueChangedListener(onValueChangeListener);


        }

        NumberPicker.OnValueChangeListener onValueChangeListener =
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        Toast.makeText(context, "selected number " + numberPicker.getValue(), Toast.LENGTH_LONG);
                        total = numberPicker.getValue() * (int) price;
                        ticketTotalValue.setText(total + "");
                        if (type.equals("VIP"))
                            totalVIP = numberPicker.getValue();
                        if (type.equals("Platinum"))
                            totalP = numberPicker.getValue();
                    }


                };

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}