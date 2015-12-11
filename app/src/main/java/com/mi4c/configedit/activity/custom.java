package com.mi4c.configedit.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.mi4c.configedit.config.Config;
import com.mi4c.configedit.utils.OutputConfigFile;
import com.mi4c.configedit.R;
import com.mi4c.configedit.utils.Su_dos;

import java.util.ArrayList;
import java.util.List;

public class custom extends AppCompatActivity implements View.OnClickListener {
    private Switch aSwitch1;
    private Switch aSwitch2;
    private Switch aSwitch3;
    private Switch aSwitch4;
    private Switch aSwitch5;
    private Switch aSwitch6;
    private Switch open1;
    private Switch open2;
    private Switch open3;
    private Switch open4;
    private Switch open5;
    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private TextView cpu_power;
    private TextView cpu_pref;
    private TextView gpu;
    private LinearLayout a53;
    private RelativeLayout a53_p;
    private LinearLayout a57;
    private RelativeLayout a57_p;
    private RelativeLayout gpu_p;
    private OutputConfigFile create_config;
    private SharedPreferences sp;
    private final String DATABASE = "cs_config";
    private final String BIG_BTN1 = "big_btn1";
    private final String BIG_BTN2 = "big_btn2";
    private final String BIG_BTN3 = "big_btn3";
    private final String BIG_BTN4 = "big_btn4";
    private final String BIG_BTN5 = "big_btn5";
    private final String CPU0 = "cpu0";
    private final String CPU1 = "cpu1";
    private final String CPU2 = "cpu2";
    private final String CPU3 = "cpu3";
    private final String CPU4 = "cpu4";
    private final String CPU5 = "cpu5";
    private final String SEEKBAR1 = "seekbar1";
    private final String SEEKBAR2 = "seekbar2";
    private final String SEEKBAR3 = "seekbar3";
    private final int cpu1 = 1820;
    private final int cpu2 = 1440;
    private final int gpu1 = 600;
    private int[] CPU_A53;
    private int[] CPU_A57;
    private int[] GPU;
    private int cpu_a53_freq;
    private int cpu_a57_freq;
    private boolean cpu_a53_cus = false;
    private boolean cpu_a57_cus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        sp = getSharedPreferences(DATABASE, Activity.MODE_PRIVATE);
        CPU_A53 = getResources().getIntArray(R.array.a53);
        CPU_A57 = getResources().getIntArray(R.array.a57);
        GPU = getResources().getIntArray(R.array.gpu);
        create_config = new OutputConfigFile(this);
        aSwitch1 = (Switch) findViewById(R.id.switch1);
        aSwitch2 = (Switch) findViewById(R.id.switch2);
        aSwitch3 = (Switch) findViewById(R.id.switch3);
        aSwitch4 = (Switch) findViewById(R.id.switch4);
        aSwitch5 = (Switch) findViewById(R.id.switch5);
        aSwitch6 = (Switch) findViewById(R.id.switch6);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        cpu_power = (TextView) findViewById(R.id.cpu_power);
        cpu_pref = (TextView) findViewById(R.id.cpu_pref);
        open1 = (Switch) findViewById(R.id.switch_main1);
        open2 = (Switch) findViewById(R.id.switch_main2);
        open3 = (Switch) findViewById(R.id.switch_main3);
        open4 = (Switch) findViewById(R.id.switch_main4);
        open5 = (Switch) findViewById(R.id.switch_main5);
        a53 = (LinearLayout) findViewById(R.id.ll_main1);
        a53_p = (RelativeLayout) findViewById(R.id.rl_main1);
        a57 = (LinearLayout) findViewById(R.id.ll_main2);
        a57_p = (RelativeLayout) findViewById(R.id.rl_main2);
        gpu_p = (RelativeLayout) findViewById(R.id.rl_main3);
        gpu = (TextView) findViewById(R.id.gpu_manager);
        mEvent();
        initUI();
    }

    public void saveUI() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(BIG_BTN1, open1.isChecked() ? 1 : 0);
        editor.putInt(BIG_BTN2, open2.isChecked() ? 1 : 0);
        editor.putInt(BIG_BTN3, open3.isChecked() ? 1 : 0);
        editor.putInt(BIG_BTN4, open4.isChecked() ? 1 : 0);
        editor.putInt(BIG_BTN5, open5.isChecked() ? 1 : 0);
        editor.putInt(CPU0, aSwitch1.isChecked() ? 1 : 0);
        editor.putInt(CPU1, aSwitch2.isChecked() ? 1 : 0);
        editor.putInt(CPU2, aSwitch3.isChecked() ? 1 : 0);
        editor.putInt(CPU3, aSwitch4.isChecked() ? 1 : 0);
        editor.putInt(CPU4, aSwitch5.isChecked() ? 1 : 0);
        editor.putInt(CPU5, aSwitch6.isChecked() ? 1 : 0);
        editor.putInt(SEEKBAR1, seekBar1.getProgress());
        editor.putInt(SEEKBAR2, seekBar2.getProgress());
        editor.putInt(SEEKBAR3, seekBar3.getProgress());
        editor.commit();
    }

    public void initUI() {
        if (sp.getInt(BIG_BTN1, 0) == 1) {
            open1.setChecked(true);
            if (sp.getInt(CPU0, 0) == 0) {
                aSwitch1.setChecked(false);
            }
            if (sp.getInt(CPU1, 0) == 0) {
                aSwitch2.setChecked(false);
            }
            if (sp.getInt(CPU2, 0) == 0) {
                aSwitch3.setChecked(false);
            }
            if (sp.getInt(CPU3, 0) == 0) {
                aSwitch4.setChecked(false);
            }
        }
        if (sp.getInt(BIG_BTN2, 0) == 1) {
            cpu_a53_cus = true;
            cpu_a53_freq = cpu1;
            open2.setChecked(true);
            seekBar1.setProgress(sp.getInt(SEEKBAR1, cpu1));
        }
        if (sp.getInt(BIG_BTN3, 0) == 1) {
            open3.setChecked(true);
            if (sp.getInt(CPU4, 0) == 0) {
                aSwitch5.setChecked(false);
            }
            if (sp.getInt(CPU5, 0) == 0) {
                aSwitch6.setChecked(false);
            }
        }
        if (sp.getInt(BIG_BTN4, 0) == 1) {
            cpu_a57_cus = true;
            cpu_a57_freq = cpu2;
            open4.setChecked(true);
            seekBar2.setProgress(sp.getInt(SEEKBAR2, cpu2));
        }
        if (sp.getInt(BIG_BTN5, 0) == 1) {
            open5.setChecked(true);
            seekBar3.setProgress(sp.getInt(SEEKBAR3, gpu1));
        }
    }

    private void mEvent() {
        seekBar1.setProgress(1056);
        seekBar2.setProgress(1436);
        seekBar3.setProgress(300);
        open1.setText(getResources().getString(R.string.t1));
        open2.setText(getResources().getString(R.string.t1));
        open3.setText(getResources().getString(R.string.t1));
        open4.setText(getResources().getString(R.string.t1));
        open5.setText(getResources().getString(R.string.t1));
        open1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    a53.setVisibility(View.VISIBLE);
                    open1.setText(getResources().getString(R.string.t2));
                } else {
                    a53.setVisibility(View.GONE);
                    open1.setText(getResources().getString(R.string.t1));
                }
            }
        });
        open2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    a53_p.setVisibility(View.VISIBLE);
                    open2.setText(getResources().getString(R.string.t2));
                } else {
                    open2.setText(getResources().getString(R.string.t1));
                    a53_p.setVisibility(View.GONE);
                }
            }
        });
        open3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    open3.setText(getResources().getString(R.string.t2));
                    a57.setVisibility(View.VISIBLE);
                } else {
                    open3.setText(getResources().getString(R.string.t1));
                    a57.setVisibility(View.GONE);
                }
            }
        });
        open4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    open4.setText(getResources().getString(R.string.t2));
                    a57_p.setVisibility(View.VISIBLE);
                } else {
                    open4.setText(getResources().getString(R.string.t1));
                    a57_p.setVisibility(View.GONE);
                }
            }
        });
        open5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    open5.setText(getResources().getString(R.string.t2));
                    gpu_p.setVisibility(View.VISIBLE);
                } else {
                    open5.setText(getResources().getString(R.string.t1));
                    gpu_p.setVisibility(View.GONE);
                }
            }
        });
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setSeekProgress(1, 0, i, cpu_power, null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekProgress = seekBar.getProgress();
                setSeekProgress(2, 0, seekProgress, null, seekBar);
            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setSeekProgress(1, 1, i, cpu_pref, null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekProgress = seekBar.getProgress();
                setSeekProgress(2, 1, seekProgress, null, seekBar);
            }
        });
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setSeekProgress(1, 2, i, gpu, null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekProgress = seekBar.getProgress();
                setSeekProgress(2, 2, seekProgress, null, seekBar);
            }
        });
    }

    private final String MHZ = "MHZ";
    List<int[]> list_array;

    private void setSeekProgress(int type, int index, int progress, TextView tv, SeekBar sb) {
        if (list_array == null) {
            list_array = new ArrayList<>();
            list_array.add(CPU_A53);
            list_array.add(CPU_A57);
            list_array.add(GPU);
        }
        int add = 0;
        if (index == 0) {
            add = 384;
        } else if (index == 1) {
            add = 384;
        } else if (index == 2) {
            add = 300;
        }
        for (int i = 0; i < list_array.get(index).length; i++) {
            int low = (list_array.get(index)[(i - 1) < 0 ? 0 : i - 1] + list_array.get(index)[i]) / 2;
            int high = (list_array.get(index)[i] + list_array.get(index)[(i + 1) >= list_array.get(index).length ? list_array.get(index).length - 1 : i + 1]) / 2;
            if (type == 1) {//设置刻度
                if (progress > low && progress < high) {
                    tv.setText(list_array.get(index)[i] + add + MHZ);
                }
            } else {//设置进度
                if (progress > low && progress < high) {
                    sb.setProgress(list_array.get(index)[i]);
                }
            }
        }
    }

    private final String WAIT = "请稍后...";
    private final String REBOOT = "修改完成，重启后生效，是否立刻重启？";
    private final String YES = "是";
    private final String NO = "否";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finished:
                saveUI();
                final ProgressDialog dialog = ProgressDialog.show(this, null, WAIT, true, false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(custom.this);  //先得到构造器
                        builder.setMessage(REBOOT); //设置内容
                        builder.setPositiveButton(YES, new DialogInterface.OnClickListener() { //设置确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Su_dos su_dos = new Su_dos();
                                su_dos.reboot();
                                dialog.dismiss(); //关闭dialog
                            }
                        });
                        builder.setNegativeButton(NO, new DialogInterface.OnClickListener() { //设置确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); //关闭dialog
                            }
                        });
                        //参数都设置完成了，创建并显示出来
                        builder.create().show();
                    }
                }, 1200);
                Config config_cpu_perf = open4.isChecked() ?
                        new Config("[SS-SKIN-XO-THERM-PERF]", "ss", "250", "xo_therm_buf", "cluster1", "2000", "1000", "0", ((seekBar2.getProgress() + 384) * 1000) + "") :
                        new Config("[SS-SKIN-XO-THERM-PERF]", "ss", "250", "xo_therm_buf", "cluster1", "43000", "37000", "0", "800000");
                Config config_cpu_power = open2.isChecked() ?
                        new Config("[SS-SKIN-XO-THERM-POWER]", "ss", "1000", "xo_therm_buf", "cluster0", "2000", "1000", "0", ((seekBar1.getProgress() + 384) * 1000) + "") :
                        new Config("[SS-SKIN-XO-THERM-POWER]", "ss", "1000", "xo_therm_buf", "cluster0", "48000", "40000", "0", "600000");
                Config config_gpu = open5.isChecked() ?
                        new Config("[GPU_management]", "monitor", "10000", "xo_therm_buf", "2000", "1000", "gpu", ((seekBar3.getProgress() + 300) * 1000000) + "") :
                        new Config("[GPU_management]", "monitor", "10000", "xo_therm_buf", "40000\t\t43000\t\t47000\t\t50000\t\t", "37000\t\t40000\t\t43000\t\t47000", "gpu\t\t\tgpu\t\t\tgpu\t\t\tgpu", "490000000\t450000000\t367000000\t300000000");
                Config config_battery = new Config("[MONITOR_QUIET_THERM_BATTERY]", "monitor", "1000", "xo_therm_buf", "38000\t\t40000\t\t42000\t\t44000", "35000\t\t38000\t\t40000\t\t42000", "battery\t\tbattery\t\tbattery\t\tbattery", "0\t\t1\t\t2\t\t3");
                Config config_cpu0_close;
                Config config_cpu1_close;
                Config config_cpu2_close;
                Config config_cpu3_close;
                if (open1.isChecked() && !aSwitch1.isChecked()) {
                    config_cpu0_close = new Config("[CPU0_HOTPLUG_MONITOR]", "monitor", "1000", "xo_therm_buf", "2000", "1000", "hotplug_0", "1");
                } else {
                    config_cpu0_close = new Config();
                }
                if (open1.isChecked() && !aSwitch2.isChecked()) {
                    config_cpu1_close = new Config("[CPU1_HOTPLUG_MONITOR]", "monitor", "1000", "xo_therm_buf", "2000", "1000", "hotplug_1", "1");
                } else {
                    config_cpu1_close = new Config();
                }
                if (open1.isChecked() && !aSwitch3.isChecked()) {
                    config_cpu2_close = new Config("[CPU2_HOTPLUG_MONITOR]", "monitor", "1000", "xo_therm_buf", "2000", "1000", "hotplug_2", "1");
                } else {
                    config_cpu2_close = new Config();
                }
                if (open1.isChecked() && !aSwitch4.isChecked()) {
                    config_cpu3_close = new Config("[CPU3_HOTPLUG_MONITOR]", "monitor", "1000", "xo_therm_buf", "2000", "1000", "hotplug_3", "1");
                } else {
                    config_cpu3_close = new Config();
                }
                Config config_cpu4_close = open3.isChecked() ? (aSwitch5.isChecked() ? new Config() :
                        new Config("[CPU4_HOTPLUG_MONITOR]", "monitor", "1000", "xo_therm_buf", "2000", "1000", "hotplug_4", "1")) :
                        new Config("[CPU4_HOTPLUG_MONITOR]", "monitor", "1000", "xo_therm_buf", "42000", "37000", "hotplug_4", "1");
                Config config_cpu5_close = open3.isChecked() ? (aSwitch6.isChecked() ? new Config() :
                        new Config("[CPU5_HOTPLUG_MONITOR]", "monitor", "1000", "xo_therm_buf", "2000", "1000", "hotplug_5", "1")) :
                        new Config("[CPU5_HOTPLUG_MONITOR]", "monitor", "1000", "xo_therm_buf", "45000", "40000", "hotplug_5", "1");
                Config config_ss1 = new Config("[SS-CPU0]", "ss", "65", "cpu0-1", "cluster0", "93000", "55000");
                Config config_ss2 = new Config("[SS-CPU2]", "ss", "65", "cpu2", "cluster0", "93000", "55000");
                Config config_ss3 = new Config("[SS-CPU3]", "ss", "65", "cpu3", "cluster0", "93000", "55000");
                Config config_ss4 = new Config("[SS-CPU4]", "ss", "30", "cpu4", "cluster1", "96000", "55000");
                Config config_ss5 = new Config("[SS-CPU5]", "ss", "30", "cpu5", "cluster1", "96000", "55000");
                Config config_ss_gpu = new Config("[SS-GPU]", "ss", "250", "gpu", "gpu", "85000", "55000");
                Config config_junction = new Config("[SS-POPMEM-JUNCTION]", "ss", "65", "pop_mem", "cluster1", "85000", "55000", "16", 1);
                create_config.initData(config_cpu_perf.output() +
                        config_cpu_power.output() +
                        config_gpu.output() +
                        config_battery.output() +
                        config_cpu0_close.output() +
                        config_cpu1_close.output() +
                        config_cpu2_close.output() +
                        config_cpu3_close.output() +
                        config_cpu4_close.output() +
                        config_cpu5_close.output() +
                        config_ss1.output() +
                        config_ss2.output() +
                        config_ss3.output() +
                        config_ss4.output() +
                        config_ss5.output() +
                        config_junction.output() +
                        config_ss_gpu.output());
                Su_dos su_dos = new Su_dos();
                su_dos.move();
                break;
        }
    }
}
