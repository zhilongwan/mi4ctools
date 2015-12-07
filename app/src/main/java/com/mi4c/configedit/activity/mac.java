package com.mi4c.configedit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mi4c.configedit.R;
import com.mi4c.configedit.utils.BuildProperties;
import com.mi4c.configedit.utils.OutputConfigFile;
import com.mi4c.configedit.utils.RandomMacAddress;
import com.mi4c.configedit.utils.Su_dos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mac extends AppCompatActivity implements View.OnClickListener {
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private EditText et5;
    private EditText et6;
    List<EditText> list_et = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac);
        this.setFinishOnTouchOutside(false);
        ((TextView) findViewById(R.id.now_mac)).setText("当前MAC地址：" + getLocalMacAddress());
        et1 = (EditText) findViewById(R.id.tv1);
        et2 = (EditText) findViewById(R.id.tv2);
        et3 = (EditText) findViewById(R.id.tv3);
        et4 = (EditText) findViewById(R.id.tv4);
        et5 = (EditText) findViewById(R.id.tv5);
        et6 = (EditText) findViewById(R.id.tv6);
        list_et.add(et1);
        list_et.add(et2);
        list_et.add(et3);
        list_et.add(et4);
        list_et.add(et5);
        list_et.add(et6);
        for (int i = 0; i < 6; i++) {
            final int finalI = i;
            list_et.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (finalI < 5) {
                        if (s.toString().length() == 2) {
                            list_et.get(finalI + 1).requestFocus();
                        }
                    }
                }
            });
        }
    }

    private String getLocalMacAddress() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }
    private void randomMac(){
        String mac = RandomMacAddress.getMacAddrWithFormat(":");
        String[] mac_array = mac.split(":");
        for(int i=0;i<6;i++){
            list_et.get(i).setText(mac_array[i]);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.random:
                randomMac();
                break;
            case R.id.finished:
                String mac="";
                for(int i=0;i<6;i++){
                    mac+=list_et.get(i).getText().toString();
                }
                OutputConfigFile create_config = new OutputConfigFile(this);
                create_config.initDataMac(getResources().getString(R.string.mac0) + mac);
//                CopyAssets(getResources().getString(R.string.u), getResources().getString(R.string.n2));
                final Su_dos su_dos = new Su_dos();
                su_dos.mac();
                final ProgressDialog wait = ProgressDialog.show(this, null, WAIT, true, false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wait.dismiss();
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(mac.this);
                        builder3.setMessage(getResources().getString(isMIUI() ? R.string.s5 : R.string.s4));
                        builder3.setCancelable(false);
                        builder3.setPositiveButton(getResources().getString(isMIUI() ? (R.string.reboot) : (R.string.ok)), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (isMIUI()) {
                                    su_dos.reboot();
                                }
                            }
                        });
                        if (isMIUI()) {
                            builder3.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }
                        builder3.create().show();
                    }
                }, 1200);
                break;
        }
    }

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    private final String WAIT = "请稍候...";
}
