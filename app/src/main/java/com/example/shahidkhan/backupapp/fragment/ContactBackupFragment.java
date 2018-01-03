package com.example.shahidkhan.backupapp.fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shahidkhan.backupapp.R;
import com.example.shahidkhan.backupapp.model.ContactNumber;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ContactBackupFragment extends Fragment {
    private static final String TAG ="TAG";
    private ListView l1;

    public String current_user;
    public  String cur_user_email;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private FirebaseDatabase databaseInstance;
    //= FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference;

    private DatabaseReference contactReference;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("ContactBackup");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        current_user = firebaseAuth.getInstance().getCurrentUser().getEmail().toString().trim();
        Log.i("TAG","user_email - "+current_user);
        cur_user_email =  current_user.replace(".",",");
        Log.i(TAG,"cur_useremial_convert "+ cur_user_email);


        progressDialog = new ProgressDialog(getActivity());
        databaseInstance = FirebaseDatabase.getInstance();
        mRootReference = databaseInstance.getReference();
        contactReference = mRootReference.child(cur_user_email).child("contact");
        writeContact();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact_backup, container, false);

        l1 =(ListView)view.findViewById(R.id.list_view_mainAct);

        return view;
    }
    public void writeContact() {
        //reference
        //https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.Phone.html
        //https://stackoverflow.com/questions/7204035/how-to-access-sms-and-contacts-data
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Toast.makeText(getActivity(),"getContact is called yar",Toast.LENGTH_SHORT).show();
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        progressDialog.setMessage("uploading contact on Firebase");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            //mRootReference.child(user_email_key);
            String key_id = contactReference.push().getKey();
            Log.i("getContact", "Name - " + name + " num - " + number);
            ContactNumber contactNumber = new ContactNumber(name,number);
            contactReference.child(key_id).setValue(contactNumber);

        }
        progressDialog.dismiss();

    }
}
