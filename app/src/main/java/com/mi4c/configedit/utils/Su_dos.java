package com.mi4c.configedit.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wonsilon on 2015/11/25 0025.
 */
public class Su_dos {
    public void reboot() {
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("reboot\n");
            dos.flush();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获得当前CPU调控模式
     */
    public String getCpuCurGovernor() {
        String string = "";
        try {
            DataInputStream is = null;
            Process process = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor");
            is = new DataInputStream(process.getInputStream());
            string = is.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 获得CPU所有调控模式
     *
     * @return
     */
    public List<String> readCpuGovernors() {
        List<String> governors = new ArrayList<String>();
        DataInputStream is = null;
        try {
            Process process = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors");
            is = new DataInputStream(process.getInputStream());
            String line = is.readLine();

            String[] strs = line.split(" ");
            for (int i = 0; i < strs.length; i++)
                governors.add(strs[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return governors;
    }

    /**
     * 重写CPU调控模式
     *
     * @param governor
     * @return
     */
    public boolean writeCpuGovernor(String governor) {
        DataOutputStream os = null;
        byte[] buffer = new byte[256];
        String command = "echo " + governor + " > /sys/devices/system/cpu/cpu";
        String command2 = "/cpufreq/scaling_governor";
        try {
            Process process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            for (int i = 0; i < 6; i++) {
                os.writeBytes(command + i + command2 + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            Log.i("CPUSET", "exit value = " + process.exitValue());
        } catch (IOException e) {
            Log.i("CPUSET", "writeCpuGovernor: write CPU Governor(" + governor + ") failed!");
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void recovery() {
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("reboot recovery\n");
            dos.flush();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final String OTA_PATH = "/sdcard/Download/data/otad.log";

    public void ota() {
        File file = new File(OTA_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("mount -o remount /cache\n");
            dos.flush();
            dos.writeBytes("cp /sdcard/Download/data/otad.log /cache/otad\n");
            dos.flush();
            dos.writeBytes("chmod 644 /cache/otad/otad.log\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("stop thermal-engine\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void start() {
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("start thermal-engine\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void move() {
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("mount -o remount /system\n");
            dos.flush();
            dos.writeBytes("cp /sdcard/Download/data/thermal-engine-8992.conf /system/etc\n");
            dos.flush();
            dos.writeBytes("chmod 644 /system/etc/thermal-engine-8992.conf\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void mac() {
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("mount -o remount /persist\n");
            dos.flush();
            dos.writeBytes("cp /sdcard/Download/data/wlan_mac.bin /persist\n");
            dos.flush();
            dos.writeBytes("chmod 0555 /persist/wlan_mac.bin\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void rm_mac() {
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("mount -o remount /persist\n");
            dos.flush();
            dos.writeBytes("rm /persist/wlan_mac.bin\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void app_enable(boolean enable, String packagename) {
        DataOutputStream dos = null;
        String en = "";
        if (enable) {
            en = " enable ";
        } else {
            en = " disable ";
        }
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("pm" + en + packagename + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
