package com.mi4c.configedit.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Wonsilon on 2015/12/14 0014.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, bootservice.class);
        context.startService(service);
//        Log.v("TAG", "开机自动服务自动启动.....");
    }
}
