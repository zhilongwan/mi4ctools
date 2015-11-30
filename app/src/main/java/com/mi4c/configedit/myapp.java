package com.mi4c.configedit;

import android.app.Application;

import net.youmi.android.AdManager;

/**
 * Created by wangzl on 2015/11/28.
 */
public class myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AdManager.getInstance(this).init(getResources().getString(R.string.appid), getResources().getString(R.string.appSecret), false);
    }
}
