package com.mi4c.configedit.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mi4c.configedit.R;
import com.mi4c.configedit.utils.Su_dos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class systemapp extends AppCompatActivity {
    private PackageManager pm;
    List<AppInfo> appList_enable = new ArrayList<AppInfo>();
    List<AppInfo> appList_disable = new ArrayList<AppInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systemapp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                queryFilterAppInfo();
            }
        }).start();
    }

    // 根据查询条件，查询特定的ApplicationInfo
    private void queryFilterAppInfo() {
        pm = this.getPackageManager();
        // 查询所有已经安装的应用程序
        List<ApplicationInfo> listAppcations = pm
                .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listAppcations,
                new ApplicationInfo.DisplayNameComparator(pm));// 排序
        appList_enable.clear();
        appList_disable.clear();
        for (ApplicationInfo app : listAppcations) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                if (app.enabled) {
                    appList_enable.add(getAppInfo(app));
                } else {
                    appList_disable.add(getAppInfo(app));
                }
            }
        }
        handler.sendEmptyMessage(1);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager manager = new LinearLayoutManager(systemapp.this);
                    manager.setOrientation(1);//列表：0横向 1纵向
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setLayoutManager(manager);
                    MyAdapter adapter = new MyAdapter();
                    recyclerView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };

    // 构造一个AppInfo对象 ，并赋值
    private AppInfo getAppInfo(ApplicationInfo app) {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppLabel((String) app.loadLabel(pm));
        appInfo.setAppIcon(app.loadIcon(pm));
        appInfo.setPkgName(app.packageName);
        return appInfo;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        public MyAdapter() {
            super();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            View view = View.inflate(viewGroup.getContext(), R.layout.adapter_course, null);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }


        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.mText.setText(appList_disable.get(i).getAppLabel());
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Su_dos su_dos = new Su_dos();
                    su_dos.app_enable(true, appList_disable.get(i).getPkgName());
                }
            });
        }

        @Override
        public int getItemCount() {
            return appList_disable.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mText;
            public View view;

            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                mText = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }
}
