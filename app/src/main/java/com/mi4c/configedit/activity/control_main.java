package com.mi4c.configedit.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mi4c.configedit.R;
import com.mi4c.configedit.utils.OutputConfigFile;
import com.mi4c.configedit.utils.Su_dos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class control_main extends AppCompatActivity {
    String[] strings;
    OutputConfigFile create_config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(control_main.this, custom.class));
            }
        });
        strings = getResources().getStringArray(R.array.main);
        create_config = new OutputConfigFile(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(1);//列表：0横向 1纵向
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
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
            viewHolder.mText.setText(strings[i]);
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(control_main.this);  //先得到构造器
                    builder.setMessage("确认替换为“" + strings[i] + "”？"); //设置内容
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CopyAssets(getResources().getString(R.string.u), getResources().getString(R.string.n), i);
                            Su_dos su_dos = new Su_dos();
                            su_dos.move();
                            final ProgressDialog dialog2 = ProgressDialog.show(control_main.this, null, "请稍后...", true, false);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog2.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(control_main.this);  //先得到构造器
                                    builder.setMessage("修改完成，重启后生效，是否立刻重启？"); //设置内容
                                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() { //设置确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Su_dos su_dos = new Su_dos();
                                            su_dos.reboot();
                                            dialog.dismiss(); //关闭dialog
                                        }
                                    });
                                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() { //设置确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss(); //关闭dialog
                                        }
                                    });
                                    //参数都设置完成了，创建并显示出来
                                    builder.create().show();
                                }
                            }, 2000);
                        }
                    });
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); //关闭dialog
                        }
                    });
                    //参数都设置完成了，创建并显示出来
                    builder.create().show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return strings.length;
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

    private void CopyAssets(String dir, String fileName, int i) {
        File mWorkingPath = new File(dir);
        File delete = new File(dir + fileName);
        if (delete.exists()) {
            delete.delete();
        }
        if (!mWorkingPath.exists()) {
            if (!mWorkingPath.mkdirs()) {
                Log.e("--CopyAssets--", "cannot create directory.");
            }
        }
        try {
            InputStream in = this.getResources().getAssets().open(i + "/" + fileName);
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

}
