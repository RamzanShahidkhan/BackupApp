package com.example.shahidkhan.backupappver2.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shahidkhan.backupappver2.R;
import com.example.shahidkhan.backupappver2.controller.ContactListAdapter;
import com.example.shahidkhan.backupappver2.model.ContactNumber;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ContactRestoreFragment extends Fragment {
    private static final String TAG ="TAG";
    private ListView l1;

    public String current_user;
    public  String cur_user_email;
    public String userID;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private FirebaseDatabase databaseInstance;

    private DatabaseReference mRootReference;

    private DatabaseReference contactReference;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("ContactRetrieve");
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
        contactReference = mRootReference.child(cur_user_email).child("contact");


        retrieveContact();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_restore, container, false);
        l1 =(ListView)view.findViewById(R.id.list_view_mainAct);
        return view;
    }
    // retrieve contact from firebase
    public void retrieveContact()
    {
        Log.i("retrCon"," RCOn is called");
        progressDialog.setMessage("Loading data from firebase...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        Toast.makeText(getActivity(),"RContact is call",Toast.LENGTH_SHORT).show();
        contactReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ContactNumber> messagesList = new ArrayList<>();
                for (DataSnapshot  data : dataSnapshot.getChildren()){

                    messagesList.add(data.getValue(ContactNumber.class));
                }
//                ArrayAdapter<String>  arrayAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,mUsername);
                ContactListAdapter messagesListAdapter = new ContactListAdapter(getActivity(), messagesList);
//                messagesListAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                l1.setAdapter(messagesListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG","cancel DataSnap error");
                progressDialog.dismiss();
            }
        });
    }
}
