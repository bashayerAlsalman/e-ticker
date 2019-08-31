package net.bashayer.eticket.notification;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;

import net.bashayer.eticket.network.model.NewEventModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static net.bashayer.eticket.event.EventListActivity.MY_PREFS_NAME;

public class NotificationService extends JobService {
    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    public static final int NOTIFICATION_ID = 101;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
//todo get event
        String event = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).getString("event", "");
        // NewEventModel attendee = Gson().fromJson(event, EventAttendee.class);
        Gson g = new Gson();
        NewEventModel newEvent = g.fromJson(event, NewEventModel.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date currentDateFormattd = sdf.parse(new Date().toString());
            Date eventDate = sdf.parse(newEvent.eventDate);


            if (currentDateFormattd.before(eventDate)) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
                builder.setContentTitle("E-ticketing");
                builder.setContentText("you have an incoming event");
//                builder.setSmallIcon(R.drawabl);
//                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
                Notification notification = builder.build();
                //This is what will will issue the notification i.e.notification will be visible
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                notificationManagerCompat.notify(NOTIFICATION_ID, notification);

                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));

                builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);


            } else if (currentDateFormattd.after(eventDate)) {
                //todo rate

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
