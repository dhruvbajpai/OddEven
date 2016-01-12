package com.dhruv.oddeven.Utils;

/**
 * Created by Dhruv on 01-Jan-16.
 */
public class History_List {

    public String from_id;
    public String from_name;
    public String from_gcm_regid;
    public String to_id;
    public String to_name;
    public String to_gcm_regid;
    public String time;

    public History_List(String from_id, String from_name, String from_gcm_regid, String to_id, String to_name, String to_gcm_regid,String time) {
        this.from_id = from_id;
        this.from_name = from_name;
        this.from_gcm_regid = from_gcm_regid;
        this.to_id = to_id;
        this.to_name = to_name;
        this.to_gcm_regid = to_gcm_regid;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getFrom_gcm_regid() {
        return from_gcm_regid;
    }

    public void setFrom_gcm_regid(String from_gcm_regid) {
        this.from_gcm_regid = from_gcm_regid;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public String getTo_gcm_regid() {
        return to_gcm_regid;
    }

    public void setTo_gcm_regid(String to_gcm_regid) {
        this.to_gcm_regid = to_gcm_regid;
    }
}
