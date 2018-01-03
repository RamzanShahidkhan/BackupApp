package com.example.shahidkhan.backupapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.shahidkhan.backupapp.R;
import com.example.shahidkhan.backupapp.fragment.ContactBackupFragment;
import com.example.shahidkhan.backupapp.fragment.ContactRestoreFragment;
import com.example.shahidkhan.backupapp.fragment.Homefragment;
import com.example.shahidkhan.backupapp.fragment.SMSBackupFragment;
import com.example.shahidkhan.backupapp.fragment.SMSRestoreFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String current_user ="Ramzan";
    public  String username = "ramzan@gmail.com";
    //private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // firebaseAuth = FirebaseAuth.getInstance();
        //current_user = firebaseAuth.getInstance().getCurrentUser().getEmail().toString().trim();
        //username = firebaseAuth.getCurrentUser().getDisplayName().toString().trim();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_CONTACTS","android.permission.READ_SMS"}, 2);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView name =(TextView)navigationView.findViewById(R.id.textviewheadname);
        TextView email =(TextView)navigationView.findViewById(R.id.textViewheademail);
        //name.setText(username);
        //email.setText(current_user);
        //displaySelectedScreen(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // method for selection of screen
    private void displaySelectedScreen(int id)
    {
        Fragment fragment =null;
        switch (id)
        {
            case R.id.nav_home:
                fragment = new Homefragment();
                break;
            case R.id.nav_contact_backup:
                fragment = new ContactBackupFragment();
                break;
            case R.id.nav_sms_backup:
                fragment = new SMSBackupFragment();
                break;
            case R.id.nav_contact_restore:
                fragment = new ContactRestoreFragment();
                break;
            case R.id.nav_sms_restore:
                fragment = new SMSRestoreFragment();
                break;

            case R.id.nav_logout:
                startActivity(new Intent(this, Login.class));

        }
        if (fragment !=null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.content_main_area, fragment);
            ft.commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }
}
