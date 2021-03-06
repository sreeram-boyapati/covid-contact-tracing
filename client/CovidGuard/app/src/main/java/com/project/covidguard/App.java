package com.project.covidguard;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.facebook.stetho.Stetho;
import com.project.covidguard.activities.DiagnoseActivity;
import com.project.covidguard.data.AppDatabase;
import com.project.covidguard.tasks.MatchMakerTask;

import org.conscrypt.Conscrypt;

import java.security.Security;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class App extends Application {

    public static final String CHANNEL_ID = "temporaryExposureKeyChannel";

    private static final String LOG_TAG = "CovidGuardApplication";

    // Executor Pool to execute network, thread and IO
    private AppExecutors mExecutors;

    //WorkManager
    private WorkManager mWorkManager;

    // Perform App DB
    private AppDatabase mDB;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        Log.d(LOG_TAG, "Initialising App Executors");
        mExecutors = AppExecutors.getInstance();

        Log.d(LOG_TAG, "Initialising SQLITE Database");
        mDB = AppDatabase.getDatabase(getApplicationContext());

        Log.d(LOG_TAG, "Initialize WorkManager");
        mWorkManager = WorkManager.getInstance(getApplicationContext());

        // Add conscrypt if the android version is less than SDK Level 29
        // TLS 1.3 is by default in Android Version Q
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            Security.insertProviderAt(Conscrypt.newProvider(), 1);
        }

        createNotificationChannel();


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Temporary Exposure Key Generation",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        // terminate the executors
        if (mExecutors != null) {
            Log.d(LOG_TAG, "Shutting down async executors");
            mExecutors.shutdownServices();
        }

        // close the database on app termination
        if (mDB != null && mDB.isOpen()) {
            Log.d(LOG_TAG, "Shutting down database");
            mDB.close();
        }
    }

    public AppExecutors getExecutors() {
        return mExecutors;
    }

    public AppDatabase getDB() {
        return mDB;
    }

    public WorkManager getWorkManager() {
        return mWorkManager;
    }

}
