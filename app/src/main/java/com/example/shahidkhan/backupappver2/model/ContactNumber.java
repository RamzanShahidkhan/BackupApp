package com.example.shahidkhan.backupappver2.model;

/**
 * Created by shahidkhan on 12/16/2017.
 */

public class ContactNumber {

  //  String id;
    private String contactname;
    private String contactnumber;

    public ContactNumber(){

    }

    public ContactNumber(String name,String number)
    {
        contactname = name;
        contactnumber = number;
       // this.id=id;
    }
/**
    public String getId() {
        return id;
    }
*/

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getContactnumber() {
        return contactnumber;
    }
}
