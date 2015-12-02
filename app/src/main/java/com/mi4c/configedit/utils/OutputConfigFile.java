package com.mi4c.configedit.utils;

import android.content.Context;
import android.util.Log;

import com.mi4c.configedit.R;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by Wonsilon on 2015/11/24 0024.
 */
public class OutputConfigFile {
    private Context mContext;

    public OutputConfigFile() {
    }

    public OutputConfigFile(Context context) {
        mContext = context;
    }

    public void initData(String config) {
        String filePath = mContext.getResources().getString(R.string.u);
        String fileName = mContext.getResources().getString(R.string.n);
        writeTxtToFile(config, filePath, fileName);
    }
    public void initDataMac(String config) {
        String filePath = mContext.getResources().getString(R.string.u);
        String fileName = mContext.getResources().getString(R.string.mac_n);
        writeTxtToFile(config, filePath, fileName);
    }
    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } else {
                file.delete();
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
        }
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
        }
    }
}
