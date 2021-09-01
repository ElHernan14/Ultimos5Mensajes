package com.constante.ultimos5mensajes;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.Nullable;

public class Ultimos5Mensajes extends Service {

    @SuppressLint("Range")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread tarea = new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uriSms = Uri.parse("content://sms/inbox");
                ContentResolver cr = getContentResolver();
                while(true){
                    Cursor c = cr.query(uriSms, null,null,null,null);
                    String mensaje = null;
                    String numero = null;
                    if (c != null && c.getCount()>0){
                        int i = 1;
                        while(c.moveToNext() && i<6){
                            mensaje = c.getString(c.getColumnIndex(Telephony.Sms.BODY));
                            numero = c.getString(c.getColumnIndex(Telephony.Sms._ID));
                            Log.d("SMS", "Numero: "+numero+", Mensaje: "+mensaje);
                            i++;
                        }
                        try {
                            Thread.sleep(9000);

                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    c.close();
                }
            }
        });
        tarea.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
