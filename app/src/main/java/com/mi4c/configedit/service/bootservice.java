package com.mi4c.configedit.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import com.mi4c.configedit.utils.Su_dos;

public class bootservice extends Service {
    public bootservice() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Su_dos su_dos = new Su_dos();

    @Override
    public void onCreate() {
        super.onCreate();
        su_dos.stop();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                su_dos.start();
                SharedPreferences sp = getSharedPreferences("cs_config", Activity.MODE_PRIVATE);
                su_dos.writeCpuGovernor(sp.getString("governors", "interactive"));
                handler.sendEmptyMessage(0);
            }
        }, 2500);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopSelf();
                }
            }, 1500);
        }
    };
}
