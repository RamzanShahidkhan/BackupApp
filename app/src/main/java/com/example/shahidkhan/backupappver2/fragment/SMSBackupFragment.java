package com.example.shahidkhan.backupappver2.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.shahidkhan.backupappver2.R;
import com.example.shahidkhan.backupappver2.model.ContactNumber;
import com.example.shahidkhan.backupappver2.model.MessagesList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SMSBackupFragment extends Fragment {
    private static final String TAG ="TAG";
    private ListView l1;

    public String current_user;
    public  String cur_user_email;
    public String userID;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private FirebaseDatabase databaseInstance;
    private DatabaseReference mRootReference;
    private DatabaseReference messagesReference;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("SMSBackup");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user =firebaseAuth.getCurrentUser();
        userID = user.getUid();
        Log.i(TAG, "userID - "+userID);
       //ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_SMS"}, 1);

        current_user = firebaseAuth.getInstance().getCurrentUser().getEmail().toString().trim();
        Log.i("TAG","user_email - "+current_user);
        cur_user_email =  current_user.replace(".",",");
        Log.i(TAG,"cur_useremial_convert "+ cur_user_email);


        progressDialog = new ProgressDialog(getActivity());
        databaseInstance = FirebaseDatabase.getInstance();
        mRootReference = databaseInstance.getReference();
        messagesReference = mRootReference.child(cur_user_email).child("messages");

        writeInbox();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_smsbackup, container, false);

        l1 =(ListView)view.findViewById(R.id.list_view_mainAct);
        return view;

    }
    public void writeInbox()
    {
        progressDialog.setMessage("backing up the data");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        Toast.makeText(getActivity(),"getInbox is called yar",Toast.LENGTH_SHORT).show();
        //reference
        //http://pulse7.net/android/read-sms-message-inbox-sent-draft-android/
        // Create Inbox box URI
        Uri inboxURI = Uri.parse("content://sms/inbox");

        // List required columns
        String[] reqCols = new String[] { "_id", "address", "body" };

        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = getActivity().getContentResolver();

        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor c = cr.query(inboxURI, reqCols, null, null, null);

        while (c.moveToNext())
        {
            String address = c.getString(c.getColumnIndex(Telephony.Sms.ADDRESS));
            String body = c.getString(c.getColumnIndex(Telephony.Sms.BODY));
            Log.i("getInbox","Address - "+address + " body - "+body);

            // unique key value
            String key_id = messagesReference.push().getKey();
            MessagesList messagesList = new MessagesList(address,body);

            messagesReference.child(key_id).setValue(messagesList);


        }
        progressDialog.dismiss();
    }

}
