package com.example.shahidkhan.backupapp.controller;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shahidkhan.backupapp.R;
import com.example.shahidkhan.backupapp.model.MessagesList;

import java.util.List;

/**
 * Created by shahidkhan on 12/16/2017.
 */

public class MessagesListAdapter extends ArrayAdapter<MessagesList> {
    Activity context;
    private List<MessagesList> messagesList;
    public MessagesListAdapter(Activity context,List<MessagesList> messagesList) {
        super(context, 0,messagesList);
        this.context = context;
        this.messagesList = messagesList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewName = (TextView)listViewItem.findViewById(R.id.text_name);
        TextView textViewNumber = (TextView)listViewItem.findViewById(R.id.text_number);

       // MessagesList messages = MessagesList
        MessagesList messages = messagesList.get(position);
        textViewName.setText(messages.getSms_address());
        textViewNumber.setText(messages.getSms_body());

        return listViewItem;
    }
}
