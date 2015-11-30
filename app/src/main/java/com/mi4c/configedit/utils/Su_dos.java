package com.mi4c.configedit.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public void ota() {
        File file = new File("/sdcard/Download/data/otad.log");
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
