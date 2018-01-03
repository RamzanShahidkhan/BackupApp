package com.example.shahidkhan.backupappver2.model;

/**
 * Created by shahidkhan on 12/16/2017.
 */

public class MessagesList  {
//    public String id;
    public String sms_address;
    public String sms_body;

//    public void setSms_address(String sms_address) {
//        this.sms_address = sms_address;
//    }
//
//    public void setSms_body(String sms_body) {
//        this.sms_body = sms_body;
//    }

    public MessagesList(){

    }
//
    public MessagesList(String sms_address, String sms_body)
    {

        this.sms_address = sms_address;
        this.sms_body = sms_body;
    }
//    public String getId() {
//        return id;
//    }
    public String getSms_address() {
        return sms_address;
    }

    public String getSms_body() {
        return sms_body;
    }
}
