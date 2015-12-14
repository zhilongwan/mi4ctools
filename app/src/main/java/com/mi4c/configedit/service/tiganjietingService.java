package com.mi4c.configedit.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import java.io.IOException;
import java.lang.reflect.Method;

public class tiganjietingService extends Service {

    public tiganjietingService() {
    }

    /**
     * 按钮-监听电话
     *
     * @param v
     */
    public void createPhoneListener() {
        TelephonyManager telephony = (TelephonyManager) getSystemService(
                Context.TELEPHONY_SERVICE);
        telephony.listen(new OnePhoneStateListener(),
                PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * 电话状态监听.
     *
     * @author stephen
     */
    class OnePhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            //Log.i(TAG, "[Listener]电话号码:" + incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i("来电", "[Listener]等待接电话:" + incomingNumber);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            auto();
                        }
                    }, 1500);
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    //Log.i(TAG, "[Listener]电话挂断:" + incomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // Log.i(TAG, "[Listener]通话中:" + incomingNumber);
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    public void auto() {
        try {
            Runtime.getRuntime().exec("input keyevent " + Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
        } catch (IOException e) { // Runtime.exec(String) had an I/O problem, try to fall back
            String enforcedPerm = "android.permission.CALL_PRIVILEGED";
            Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK));
            Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
            sendOrderedBroadcast(btnDown, enforcedPerm);
            sendOrderedBroadcast(btnUp, enforcedPerm);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createPhoneListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
