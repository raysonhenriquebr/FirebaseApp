package com.raysonhenrique.firebaseapp.fragment;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.raysonhenrique.firebaseapp.NavigationActivity;
import com.raysonhenrique.firebaseapp.R;
import com.raysonhenrique.firebaseapp.UpdateActivity;
import com.raysonhenrique.firebaseapp.util.App;
import com.raysonhenrique.firebaseapp.util.NotificationReceiver;

import static com.raysonhenrique.firebaseapp.util.App.CHANNEL_1;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    private NotificationManagerCompat notificationManager;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_notification, container, false);
        notificationManager = NotificationManagerCompat.from(getContext());
        EditText editTitle = layout.findViewById(R.id.frag_notification_title);
        EditText editMsg = layout.findViewById(R.id.frag_notification_msg);
        Button btnSend = layout.findViewById(R.id.frag_notification_btn_send);
        btnSend.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String msg = editMsg.getText().toString();
            Intent intent = new Intent(getContext(), UpdateActivity.class);
           /* PendingIntent contentIntent = PendingIntent.getActivity(getContext(),0, intent,0);*/
            PendingIntent contentIntent = new NavDeepLinkBuilder(getContext()).setComponentName(NavigationActivity.class).setGraph(R.navigation.nav_graph).setDestination(R.id.nav_menu_imagens).createPendingIntent();

            //Criar um broadcast receiver ->
            //Ele deve ser ativado EXPLICITAMENTE
            //Não deve durar mais de 10 seg
            Intent broadcastIntent = new Intent(getContext(), NotificationReceiver.class);
            intent.putExtra("toast",msg);


            PendingIntent actionIntent = PendingIntent.getBroadcast(getContext(),0,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            //Criar a notificação
            Notification notification = new NotificationCompat.Builder(getContext(),CHANNEL_1).setSmallIcon(R.drawable.ic_account_circle_black_24dp).setContentTitle(title).setContentText(msg).setPriority(Notification.PRIORITY_HIGH).setContentIntent(contentIntent).addAction(R.drawable.ic_account_circle_black_24dp, "Toast", actionIntent).build();
            notificationManager.notify(1,notification);
        });
        return layout;
    }
}
