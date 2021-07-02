package com.raysonhenrique.firebaseapp.util;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1="ch_1";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }
    public void createNotificationChannels(){
        //Verificando se o celular tem API >=26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O);{
            //criar canais de notificações
            NotificationChannel channel = new NotificationChannel(CHANNEL_1,"Canal 1", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Este é o canal 1");
            //Registrar channel
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }
}
