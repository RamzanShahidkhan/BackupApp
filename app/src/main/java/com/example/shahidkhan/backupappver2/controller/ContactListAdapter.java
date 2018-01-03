package com.example.shahidkhan.backupappver2.controller;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.shahidkhan.backupappver2.R;
import com.example.shahidkhan.backupappver2.model.ContactNumber;

import java.util.List;

/**
 * Created by shahidkhan on 12/15/2017.
 */

public class ContactListAdapter extends ArrayAdapter<ContactNumber> {
    private Activity context;
    private List<ContactNumber> contactList;
    public ContactListAdapter(Activity context, List<ContactNumber> contactList)
    {
        //super(context,android.R.layout.simple_expandable_list_item_2,contactList);
        super(context,0,contactList);
        this.context = context;
        this.contactList = contactList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewName = (TextView)listViewItem.findViewById(R.id.text_name);
        TextView textViewNumber = (TextView)listViewItem.findViewById(R.id.text_number);

        ContactNumber contactNumber = contactList.get(position);
        textViewName.setText(contactNumber.getContactname());
        textViewNumber.setText(contactNumber.getContactnumber());

        return listViewItem;
    }
}
