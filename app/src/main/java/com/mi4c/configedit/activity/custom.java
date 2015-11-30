package com.mi4c.configedit.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class custom extends AppCompatActivity implements View.OnClickListener {
    private Button finished;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
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
        finished = (Button) findViewById(R.id.finished);
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
        aSwitch1.setChecked(true);
    }


    private void mEvent() {
        seekBar1.setProgress(840);
        seekBar2.setProgress(1020);
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
                if (i >= 720) {
                    cpu_power.setText("1440MHZ");
                } else if (i >= 400 && i < 720) {
                    cpu_power.setText("1248MHZ");
                } else if (i >= 130 && i < 400) {
                    cpu_power.setText("864MHZ");
                } else if (i < 130) {
                    cpu_power.setText("600MHZ");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekProgress = seekBar.getProgress();
                if (seekProgress >= 720) {
                    seekBar.setProgress(840);
                } else if (seekProgress >= 400 && seekProgress < 720) {
                    seekBar.setProgress(648);
                } else if (seekProgress >= 130 && seekProgress < 400) {
                    seekBar.setProgress(264);
                } else if (seekProgress < 130) {
                    seekBar.setProgress(0);
                }
            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 900) {
                    cpu_pref.setText("1820MHZ");
                } else if (i >= 700 && i < 900) {
                    cpu_pref.setText("1512MHZ");
                } else if (i >= 500 && i < 700) {
                    cpu_pref.setText("1384MHZ");
                } else if (i >= 300 && i < 500) {
                    cpu_pref.setText("1200MHZ");
                } else if (i >= 100 && i < 300) {
                    cpu_pref.setText("1000MHZ");
                } else if (i < 100) {
                    cpu_pref.setText("800MHZ");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekProgress = seekBar.getProgress();
                if (seekProgress > 900) {
                    seekBar.setProgress(1020);
                } else if (seekProgress >= 700 && seekProgress < 900) {
                    seekBar.setProgress(712);
                } else if (seekProgress >= 500 && seekProgress < 700) {
                    seekBar.setProgress(584);
                } else if (seekProgress >= 300 && seekProgress < 500) {
                    seekBar.setProgress(400);
                } else if (seekProgress >= 100 && seekProgress < 300) {
                    seekBar.setProgress(200);
                } else if (seekProgress < 100) {
                    seekBar.setProgress(0);
                }
            }
        });
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 250) {
                    gpu.setText("600MHZ");
                } else if (i >= 170 && i < 250) {
                    gpu.setText("490MHZ");
                } else if (i >= 108 && i < 170) {
                    gpu.setText("450MHZ");
                } else if (i >= 40 && i < 108) {
                    gpu.setText("367MHZ");
                } else if (i < 40) {
                    gpu.setText("300MHZ");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekProgress = seekBar.getProgress();
                if (seekProgress > 250) {
                    seekBar.setProgress(300);
                } else if (seekProgress >= 170 && seekProgress < 250) {
                    seekBar.setProgress(190);
                } else if (seekProgress >= 108 && seekProgress < 170) {
                    seekBar.setProgress(150);
                } else if (seekProgress >= 40 && seekProgress < 108) {
                    seekBar.setProgress(67);
                } else if (seekProgress < 40) {
                    seekBar.setProgress(0);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finished:
                final ProgressDialog dialog = ProgressDialog.show(this, null, "请稍后...", true, false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(custom.this);  //先得到构造器
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
                Config config_cpu_perf = open4.isChecked() ?
                        new Config("[SS-SKIN-XO-THERM-PERF]", "ss", "250", "xo_therm_buf", "cluster1", "2000", "1000", "0", ((seekBar2.getProgress() + 800) * 1000) + "") :
                        new Config("[SS-SKIN-XO-THERM-PERF]", "ss", "250", "xo_therm_buf", "cluster1", "43000", "37000", "0", "800000");
                Config config_cpu_power = open2.isChecked() ?
                        new Config("[SS-SKIN-XO-THERM-POWER]", "ss", "1000", "xo_therm_buf", "cluster0", "2000", "1000", "0", ((seekBar1.getProgress() + 600) * 1000) + "") :
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
