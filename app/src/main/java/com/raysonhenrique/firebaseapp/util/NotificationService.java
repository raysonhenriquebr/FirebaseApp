package com.raysonhenrique.firebaseapp.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raysonhenrique.firebaseapp.R;
import com.raysonhenrique.firebaseapp.model.User;

import static com.raysonhenrique.firebaseapp.util.App.CHANNEL_1;

public class NotificationService extends Service {
    private ValueEventListener listener;
    private

    public void showNotify(User user){

        Notification notification = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_1).setSmallIcon(R.drawable.ic_account_circle_black_24dp).setContentTitle("Alteração!").setContentText(user.getNome()).setPriority(Notification.PRIORITY_HIGH).build();
        NotificationManagerCompat nm;
        nm = NotificationManagerCompat.from(getApplicationContext());
        nm.notify(1,notification);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return super.onStartCommand(intent, flags,startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
