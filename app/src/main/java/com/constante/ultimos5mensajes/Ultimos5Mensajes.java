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
        Uri uriSms = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(uriSms, null,null,null,null);
        String mensaje = null;
        String numero = null;
        while(true){
            try {
                Thread.sleep(9000);
                if (c != null && c.getCount()>0){
                    StringBuilder sb = new StringBuilder();
                    while(c.getCount()<6){
                        mensaje = c.getString(c.getColumnIndex(Telephony.Sms.BODY));
                        numero = c.getString(c.getColumnIndex(Telephony.Sms._ID));
                        Log.d("SMS", "Numero: "+numero+", Mensaje: "+mensaje);
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
