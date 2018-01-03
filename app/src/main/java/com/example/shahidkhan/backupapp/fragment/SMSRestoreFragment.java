package com.example.shahidkhan.backupapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shahidkhan.backupapp.R;
import com.example.shahidkhan.backupapp.controller.MessagesListAdapter;
import com.example.shahidkhan.backupapp.model.MessagesList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SMSRestoreFragment extends Fragment {
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
        getActivity().setTitle("SMSRetrieve");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user =firebaseAuth.getCurrentUser();
        userID = user.getUid();
        Log.i(TAG, "userID - "+userID);

        current_user = firebaseAuth.getInstance().getCurrentUser().getEmail().toString().trim();
        Log.i("TAG","user_email - "+current_user);
        cur_user_email =  current_user.replace(".",",");
        Log.i(TAG,"cur_useremial_convert "+ cur_user_email);
        progressDialog = new ProgressDialog(getActivity());

        databaseInstance = FirebaseDatabase.getInstance();
        mRootReference = databaseInstance.getReference();
        messagesReference = mRootReference.child(cur_user_email).child("messages");

        retrieveInbox();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_smsrestore, container, false);
        l1 =(ListView)view.findViewById(R.id.list_view_mainAct);

        return view;
    }
    public void retrieveInbox()
    {
        progressDialog.setMessage("Loading the data ....");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

       /* Toast.makeText(getActivity(),"RINBOX is call",Toast.LENGTH_SHORT).show();
        if (isInternetAvailable())
        {
            Toast.makeText(getActivity(),"Network is available",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(),"Network is not available",Toast.LENGTH_LONG).show();
        }*/

        messagesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG," onDataChange.....call");

                List<MessagesList> messagesList = new ArrayList<MessagesList>();
                for (DataSnapshot  data : dataSnapshot.getChildren()){
                    messagesList.add(data.getValue(MessagesList.class));
                    Log.i("msg",messagesList.toString());
                }

                MessagesListAdapter messagesListAdapter = new MessagesListAdapter(getActivity(), messagesList);
               progressDialog.dismiss();
                l1.setAdapter(messagesListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancel","cancel DataSnap error");
                progressDialog.dismiss();
            }
        });

    }
    public boolean isInternetAvailable()
    {
        Context c =getActivity();
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
    }
}
