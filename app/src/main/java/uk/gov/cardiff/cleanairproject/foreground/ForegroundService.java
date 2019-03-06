package uk.gov.cardiff.cleanairproject.foreground;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import uk.gov.cardiff.cleanairproject.MainActivity;
import uk.gov.cardiff.cleanairproject.R;

public class ForegroundService extends Service {

    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";

    public static final String START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    public static final String PAUSE = "ACTION_PAUSE";

    public static final String PLAY = "ACTION_PLAY";

    public ForegroundService(){

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG_FOREGROUND_SERVICE, "My foreground service onCreate().");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null)
        {
            String action = intent.getAction();

            switch (action)
            {
                case START_FOREGROUND_SERVICE:
                    startForegroundService();
                    Toast.makeText(getApplicationContext(), "Foreground service is started.", Toast.LENGTH_LONG).show();
                    break;
                case STOP_FOREGROUND_SERVICE:
                    stopForegroundService();
                    Toast.makeText(getApplicationContext(), "Foreground service is stopped.", Toast.LENGTH_LONG).show();
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /* Used to build and start foreground service. */
    private void startForegroundService()
    {
        Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.");

        // Create notification default intent.
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create notification builder.
        NotificationManager notManager;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "notify_001");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        mBuilder.setContentTitle("Hey");
        mBuilder.setContentText("guys");
        mBuilder.setPriority(Notification.PRIORITY_MAX);

        notManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelID = "notify_001";
            NotificationChannel serviceChannel = new NotificationChannel(
                    channelID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            notManager.createNotificationChannel(serviceChannel);
            mBuilder.setChannelId(channelID);
        }

        // Start foreground service.
        startForeground(1, mBuilder.build());
    }

    private void stopForegroundService()
    {
        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

}
