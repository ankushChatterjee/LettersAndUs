package com.ankush.lettersandus;

/**
 * Created by nEW u on 9/14/2017.
 */

public class UserData {
    String userid;
    String notificationKey;
    UserData(){
        userid = "";
        notificationKey = "";
    }
    UserData(String u, String n){
        userid = u;
        notificationKey = n;
    }
    public String getNotificationKey() {
        return notificationKey;
    }

    public void setNotificationKey(String notificationKey) {
        this.notificationKey = notificationKey;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
