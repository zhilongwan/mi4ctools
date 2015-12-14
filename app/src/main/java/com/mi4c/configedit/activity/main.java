package com.mi4c.configedit.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mi4c.configedit.R;
import com.mi4c.configedit.service.bootservice;
import com.mi4c.configedit.service.tiganjietingService;
import com.mi4c.configedit.utils.BuildProperties;
import com.mi4c.configedit.utils.Su_dos;

import net.youmi.android.AdManager;
import net.youmi.android.update.AppUpdateInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class main extends AppCompatActivity implements View.OnClickListener {
    LinearLayout RootLL;
    ScrollView MainLL;
    private final String VERSION = "version:";
    private AppUpdateInfo updateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RootLL = (LinearLayout) findViewById(R.id.rootll);
        MainLL = (ScrollView) findViewById(R.id.mainll);
        String pkName = this.getPackageName();
        try {
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            ((TextView) findViewById(R.id.version)).setText(VERSION + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = AdManager.getInstance(main.this).syncCheckAppUpdate();
                handler.sendMessage(msg);
            }
        }).start();
        Intent service = new Intent(this, tiganjietingService.class);
        startService(service);
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

    private final String ONLY_MIUI = "此功能仅支持MIUI系统";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getroot:
                RunAsRooter();
                break;
            case R.id.cpu_control:
                startActivity(new Intent(main.this, custom.class));
//                startActivity(new Intent(main.this, control_main.class));
                break;
            case R.id.ota_rec:
                if (isMIUI()) {
                    startActivity(new Intent(main.this, ota.class));
                } else {
                    Toast.makeText(main.this, ONLY_MIUI, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.reboot:
                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                builder.setMessage(getResources().getString(R.string.s2));
                builder.setCancelable(false);
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
            case R.id.recovery:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(main.this);
                builder2.setMessage(getResources().getString(R.string.s3));
                builder2.setCancelable(false);
                builder2.setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Su_dos su_dos = new Su_dos();
                        su_dos.recovery();
                    }
                });
                builder2.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder2.create().show();
                break;
            case R.id.systemapp:
                startActivity(new Intent(main.this, systemapp3.class));
                break;
            case R.id.mac:
                startActivity(new Intent(main.this, mac.class));
                break;
            case R.id.rec_mac:
                final Su_dos su_dos2 = new Su_dos();
                su_dos2.rm_mac();
                final ProgressDialog wait2 = ProgressDialog.show(this, null, WAIT, true, false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wait2.dismiss();
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(main.this);
                        builder3.setMessage(getResources().getString(isMIUI() ? R.string.s5_default : R.string.s4_default));
                        builder3.setCancelable(false);
                        builder3.setPositiveButton(getResources().getString(isMIUI() ? R.string.reboot : R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (isMIUI()) {
                                    su_dos2.reboot();
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
            case R.id.update:
                // 这里简单示例使用一个对话框来显示更新信息
                new AlertDialog.Builder(main.this)
                        .setTitle("发现新版本")
                        .setMessage(updateInfo.getUpdateTips()) // 这里是版本更新信息
                        .setNegativeButton("马上升级",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateInfo.getUrl()));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        Toast.makeText(main.this, "开始下载...", Toast.LENGTH_SHORT).show();
                                        // ps：这里示例点击“马上升级”按钮之后简单地调用系统浏览器进行新版本的下载，
                                        // 但强烈建议开发者实现自己的下载管理流程，这样可以获得更好的用户体验。
                                    }
                                })
                        .setPositiveButton("下次再说",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).create().show();
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateInfo = (AppUpdateInfo) msg.obj;
            switch (msg.what) {
                case 1:
                    if (updateInfo == null || updateInfo.getUrl() == null) {
                        // 如果 AppUpdateInfo 为 null 或它的 url 属性为 null，则可以判断为没有新版本。
                    } else {
                        findViewById(R.id.update).setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };
    private final String WAIT = "请稍候...";
    private final String SU = "su";
    private final String EXIT = "exit\n";

    //查看是否root
    private boolean isRoot() {
        try {
            Process process = Runtime.getRuntime().exec(SU);
            process.getOutputStream().write(EXIT.getBytes());
            process.getOutputStream().flush();
            int i = process.waitFor();
            if (0 == i) {
                process = Runtime.getRuntime().exec(SU);
                return true;
            }

        } catch (Exception e) {
            return false;
        }
        return false;

    }

    private void CopyAssets(String dir, String fileName) {
        File mWorkingPath = new File(dir);
        File delete = new File(dir + fileName);
        if (delete.exists()) {
            delete.delete();
        }
        if (!mWorkingPath.exists()) {
            if (!mWorkingPath.mkdirs()) {
            }
        }
        try {
            InputStream in = this.getResources().getAssets().open(fileName);
            System.err.println("");
            File outFile = new File(mWorkingPath, fileName);
            OutputStream out = new FileOutputStream(outFile);
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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
