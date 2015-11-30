package com.mi4c.configedit.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mi4c.configedit.R;
import com.mi4c.configedit.utils.Su_dos;

import java.io.IOException;
import java.util.List;

public class main extends AppCompatActivity implements View.OnClickListener {
    LinearLayout RootLL;
    LinearLayout MainLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RootLL = (LinearLayout) findViewById(R.id.rootll);
        MainLL = (LinearLayout) findViewById(R.id.mainll);
        String pkName = this.getPackageName();
        try {
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            ((TextView)findViewById(R.id.version)).setText("version:"+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cpu_control:
                startActivity(new Intent(main.this, control_main.class));
                break;
            case R.id.ota_rec:
                startActivity(new Intent(main.this, ota.class));
                break;
            case R.id.reboot:
                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setMessage(getResources().getString(R.string.s2));
                builder.setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Su_dos su_dos = new Su_dos();
                        su_dos.reboot();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.systemapp:
                startActivity(new Intent(main.this, systemapp3.class));
                break;
        }
    }

    //查看是否root
    private boolean isRoot() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            process.getOutputStream().write("exit\n".getBytes());
            process.getOutputStream().flush();
            int i = process.waitFor();
            if (0 == i) {
                process = Runtime.getRuntime().exec("su");
                return true;
            }

        } catch (Exception e) {
            return false;
        }
        return false;

    }

    /**
     * 通过packageName已安装的APP
     */
    public void openApp(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            packageName = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUi();
    }

    private void initUi() {
        if (isRoot()) {
            RootLL.setVisibility(View.GONE);
            MainLL.setVisibility(View.VISIBLE);
        } else {
            MainLL.setVisibility(View.GONE);
            RootLL.setVisibility(View.VISIBLE);
        }
    }

    //获取ROOT权限
    private void RunAsRooter() {
        try {
            Toast.makeText(main.this, getResources().getString(R.string.s1), Toast.LENGTH_LONG).show();
            openApp(main.this, getResources().getString(R.string.pack));
            Process process = Runtime.getRuntime().exec("su");
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
