package com.mi4c.configedit.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mi4c.configedit.R;
import com.mi4c.configedit.utils.Su_dos;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;

public class ota extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ota);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ota:
                Su_dos su_dos2 = new Su_dos();
                su_dos2.ota();
                final ProgressDialog dialog = ProgressDialog.show(this, null, "系统正在被狂揍...", true, false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ota.this);  //先得到构造器
                        builder.setMessage("系统已经被搞定了，去重新检查一下更新吧~~"); //设置内容
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); //关闭dialog
                            }
                        });
                        //参数都设置完成了，创建并显示出来
                        builder.create().show();
                    }
                }, 2000);
                break;
        }
    }
}
