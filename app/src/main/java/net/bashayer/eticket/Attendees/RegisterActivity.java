package net.bashayer.eticket.Attendees;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.bashayer.eticket.R;
import net.bashayer.eticket.common.BaseActivity;
import net.bashayer.eticket.qr.models.EventTicket;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.addAttendee)
    FloatingActionButton addAttendee;
int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_ticket_details);

        ButterKnife.bind(this);
        addAttendee.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        count++;

        if(count >=5){
            addAttendee.hide();
        }
        //Adding special buttons:
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) findViewById(R.id.attendees);

        View custom = inflater.inflate(R.layout.item_attendee, parent, false);
        parent.addView(custom);


    }
}
