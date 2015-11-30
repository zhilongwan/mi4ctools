package com.mi4c.configedit.config;

/**
 * Created by Wonsilon on 2015/11/25 0025.
 */
public class Config {
    private String mName;
    private String algo_type;
    private String sampling;
    private String sensor;
    private String device;
    private String set_point;
    private String set_point_clr;
    private String time_constant;
    private String device_max_limit;
    private String thresholds;
    private String thresholds_clr;
    private String actions;
    private String action_info;

    public Config() {
    }

    public Config(String mName, String algo_type, String sampling, String sensor, String device, String set_point, String set_point_clr, String time_constant, String device_max_limit) {
        this.mName = mName;
        this.algo_type = algo_type;
        this.sampling = sampling;
        this.sensor = sensor;
        this.device = device;
        this.set_point = set_point;
        this.set_point_clr = set_point_clr;
        this.time_constant = time_constant;
        this.device_max_limit = device_max_limit;
    }

    public Config(String mName, String algo_type, String sampling, String sensor, String thresholds, String thresholds_clr, String actions, String action_info) {
        this.mName = mName;
        this.algo_type = algo_type;
        this.sampling = sampling;
        this.sensor = sensor;
        this.thresholds = thresholds;
        this.thresholds_clr = thresholds_clr;
        this.actions = actions;
        this.action_info = action_info;
    }

    public Config(String mName, String algo_type, String sampling, String sensor, String device, String set_point, String set_point_clr) {
        this.mName = mName;
        this.algo_type = algo_type;
        this.sampling = sampling;
        this.sensor = sensor;
        this.device = device;
        this.set_point = set_point;
        this.set_point_clr = set_point_clr;
    }

    public Config(String mName, String algo_type, String sampling, String sensor, String device, String set_point, String set_point_clr, String time_constant, int i) {
        this.mName = mName;
        this.algo_type = algo_type;
        this.sampling = sampling;
        this.sensor = sensor;
        this.device = device;
        this.set_point = set_point;
        this.set_point_clr = set_point_clr;
        this.time_constant = time_constant;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getAlgo_type() {
        return algo_type;
    }

    public void setAlgo_type(String algo_type) {
        this.algo_type = algo_type;
    }

    public String getSampling() {
        return sampling;
    }

    public void setSampling(String sampling) {
        this.sampling = sampling;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getSet_point() {
        return set_point;
    }

    public void setSet_point(String set_point) {
        this.set_point = set_point;
    }

    public String getSet_point_clr() {
        return set_point_clr;
    }

    public void setSet_point_clr(String set_point_clr) {
        this.set_point_clr = set_point_clr;
    }

    public String getTime_constant() {
        return time_constant;
    }

    public void setTime_constant(String time_constant) {
        this.time_constant = time_constant;
    }

    public String getDevice_max_limit() {
        return device_max_limit;
    }

    public void setDevice_max_limit(String device_max_limit) {
        this.device_max_limit = device_max_limit;
    }

    //    private String thresholds;
//    private String thresholds_clr;
//    private String actions;
//    private String action_info;
    public String output() {
        String string = "";
        string = (mName == null ? "" : mName + "\n") +
                (algo_type == null ? "" : "algo_type" + "\t\t\t" + algo_type + "\n") +
                (sampling == null ? "" : "sampling" + "\t\t\t" + sampling + "\n") +
                (sensor == null ? "" : "sensor" + "\t\t\t" + sensor + "\n") +
                (device == null ? "" : "device" + "\t\t\t" + device + "\n") +
                (set_point == null ? "" : "set_point" + "\t\t\t" + set_point + "\n") +
                (set_point_clr == null ? "" : "set_point_clr" + "\t\t\t" + set_point_clr + "\n") +
                (time_constant == null ? "" : "time_constant" + "\t\t\t" + time_constant + "\n") +
                (device_max_limit == null ? "" : "device_max_limit" + "\t\t\t" + device_max_limit + "\n") +
                (thresholds == null ? "" : "thresholds" + "\t\t\t" + thresholds + "\n") +
                (thresholds_clr == null ? "" : "thresholds_clr" + "\t\t\t" + thresholds_clr + "\n") +
                (actions == null ? "" : "actions" + "\t\t\t" + actions + "\n") +
                (action_info == null ? "" : "action_info" + "\t\t\t" + action_info + "\n");
        if (!"".equals(string)) {
            string = string + "\n";
        }
        return string;
    }
}
