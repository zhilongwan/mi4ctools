package com.mi4c.configedit.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.mi4c.configedit.R;
import com.mi4c.configedit.utils.Su_dos;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class systemapp3 extends AppCompatActivity {
    private PackageManager pm;
    private static List<AppInfo> List_enable = new ArrayList<AppInfo>();
    private static List<AppInfo> List_disable = new ArrayList<AppInfo>();
    private static List<Boolean> isChecklist = new ArrayList<>();
    private ProgressDialog dialog;
    private List<Integer> remove_index = new ArrayList<>();
    private static List<String> rm_applist = new ArrayList<>();
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systemapp3);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                queryFilterAppInfo();
            }
        }).start();
        dialog = ProgressDialog.show(this, null, "正在讀取系統程序列表...", true, false);
    }

    // 根据查询条件，查询特定的ApplicationInfo
    private void queryFilterAppInfo() {
        pm = this.getPackageManager();
        // 查询所有已经安装的应用程序
        List<ApplicationInfo> listAppcations = pm
                .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listAppcations,
                new ApplicationInfo.DisplayNameComparator(pm));// 排序
        List_enable.clear();
        List_disable.clear();
        for (ApplicationInfo app : listAppcations) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                if (app.enabled) {
                    List_enable.add(getAppInfo(app));
                } else {
                    List_disable.add(getAppInfo(app));
                }
            }
        }
        handler.sendEmptyMessage(1);
    }

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private MyAdapter adapter1;
    private MyAdapter adapter2;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    dialog.dismiss();
                    List<View> view_list = new ArrayList<>();
                    recyclerView1 = new RecyclerView(systemapp3.this);
                    recyclerView2 = new RecyclerView(systemapp3.this);
                    recyclerView1.setHasFixedSize(true);
                    LinearLayoutManager manager = new LinearLayoutManager(systemapp3.this);
                    manager.setOrientation(1);//列表：0横向 1纵向
                    LinearLayoutManager manager2 = new LinearLayoutManager(systemapp3.this);
                    manager2.setOrientation(1);//列表：0横向 1纵向
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                    recyclerView1.setLayoutManager(manager);
                    adapter1 = new MyAdapter(List_enable, 1);
                    recyclerView1.setAdapter(adapter1);
                    recyclerView2.setItemAnimator(new DefaultItemAnimator());
                    recyclerView2.setLayoutManager(manager2);
                    adapter2 = new MyAdapter(List_disable, 2);
                    recyclerView2.setAdapter(adapter2);
                    view_list.add(recyclerView1);
                    view_list.add(recyclerView2);
                    MyPagerAdapter adapter2 = new MyPagerAdapter(view_list);
                    mViewPager = (ViewPager) findViewById(R.id.container);
                    mViewPager.setAdapter(adapter2);
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(mViewPager);
                    AlertDialog.Builder builder = new AlertDialog.Builder(systemapp3.this);
                    builder.setTitle("警告");
                    builder.setMessage("本功能可以冻结所有系统程序，所以如果你不确定冻结的应用是否会影响系统的正常使用，请慎用！");
                    builder.setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
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

    /**
     * A placeholder fragment containing a simple view.
     */

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        List<AppInfo> appList = new ArrayList<AppInfo>();
        int type = -1;

        public MyAdapter(List<AppInfo> list, int type) {
            super();
            this.type = type;
            appList = list;
        }

        public void deleteData(int position) {
            appList.remove(position);
            notifyItemRemoved(position);
        }

        public void addData(AppInfo info) {
            appList.add(info);
            notifyItemInserted(appList.size() - 1);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            View view = View.inflate(viewGroup.getContext(), R.layout.adapter_systemapp, null);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.mText.setText(appList.get(i).getAppLabel());
            viewHolder.icon.setImageDrawable(appList.get(i).getAppIcon());
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(systemapp3.this);
                    builder.setIcon(appList.get(i).getAppIcon());
                    builder.setTitle(type == 1 ? "冻结确认" : "解冻确认");
                    builder.setMessage((type == 1 ? "确认冻结" : "确认解冻") + "“" + appList.get(i).getAppLabel() + "”？" + (type == 1 ? "冻结后应用将从桌面消失且无法启动，可以在右侧已冻结列表恢复。" : "解冻后应用将回到桌面，可以在左侧列表重新冻结。"));
                    builder.setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Su_dos su_dos = new Su_dos();
                            su_dos.app_enable(type != 1, appList.get(i).getPkgName());
                            if (type == 1) {
                                adapter2.addData(appList.get(i));
                            } else {
                                adapter1.addData(appList.get(i));
                            }
                            deleteData(i);

                        }
                    });
                    builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return appList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mText;
            public View view;
            public ImageView icon;
            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                mText = (TextView) itemView.findViewById(R.id.text);
                icon= (ImageView) itemView.findViewById(R.id.icon);
            }
        }
    }

    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "系統程序" : "已冻结程序";
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }
}
