package com.example.apprunner.Organizer.menu1_home.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apprunner.Organizer.menu4_check_distance.Fragment.DistanceEventFragment;
import com.example.apprunner.Organizer.menu6_profile_organizer.fragment.ProfileOrganizerFragment;
import com.example.apprunner.Organizer.menu7_help_organizer.Fragment.HelpFragment;
import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Organizer.menu2_profile_event.Fragment.Event_profile_organizerFragment;
import com.example.apprunner.Organizer.menu1_home.Fragment.MainFragment;
import com.example.apprunner.R;
import com.example.apprunner.Organizer.menu3_check_payment.Fragment.Select_event_paymentFragment;
import com.example.apprunner.Organizer.menu5_update_reward.Fragment.StatusRewardFragment;
import com.example.apprunner.User.menu6_profile_user.Fragment.ProfileUserFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView tv_FName,tv_LName,tv_stuts;
    String first_name,last_name,type,email,password;
    int id_user;

        public static final String url = "http://10.199.66.18:3000/";
//        public static final String url = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_organizer);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_organizer);
        navigationView = findViewById(R.id.nav_view_organizer);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        // load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer_organizer,new MainFragment());
        fragmentTransaction.commit();

        Bundle bundle = new Bundle();
        bundle.putString("first_name", getIntent().getExtras().getString("first_name"));
        bundle.putString("last_name",getIntent().getExtras().getString("last_name"));
        bundle.putString("type",getIntent().getExtras().getString("type"));
        bundle.putString("email", getIntent().getExtras().getString("email"));
        bundle.putString("password",getIntent().getExtras().getString("password"));
        bundle.putInt("id_user",getIntent().getExtras().getInt("id_user"));
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,mainFragment).commit();

        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        id_user = getIntent().getExtras().getInt("id_user");
        //Toast.makeText(MainActivity.this,Integer.toString(id_user),Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Bundle bundle = new Bundle();
        bundle.putInt("id_user",id_user);
        bundle.putString("first_name", first_name);
        bundle.putString("last_name",last_name);
        bundle.putString("type",type);
        if(item.getItemId() == R.id.home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new MainFragment());
            fragmentTransaction.commit();
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,mainFragment).commit();
        }

        if(item.getItemId() == R.id.event) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new Event_profile_organizerFragment());
            fragmentTransaction.commit();
            Event_profile_organizerFragment event_profile_organizerFragment = new Event_profile_organizerFragment();
            event_profile_organizerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,event_profile_organizerFragment).commit();
        }

        if(item.getItemId() == R.id.status_event_payment) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new Select_event_paymentFragment());
            fragmentTransaction.commit();
            Select_event_paymentFragment select_event_paymentFragment = new Select_event_paymentFragment();
            select_event_paymentFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,select_event_paymentFragment).commit();
        }

        if(item.getItemId() == R.id.status_event_distance) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new DistanceEventFragment());
            fragmentTransaction.commit();
            DistanceEventFragment distanceEventFragment = new DistanceEventFragment();
            distanceEventFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer, distanceEventFragment).commit();
        }

        if(item.getItemId() == R.id.status_reward) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new StatusRewardFragment());
            fragmentTransaction.commit();
            StatusRewardFragment statusRewardFragment = new StatusRewardFragment();
            statusRewardFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,statusRewardFragment).commit();
        }

        if(item.getItemId() == R.id.profile) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new ProfileOrganizerFragment());
            fragmentTransaction.commit();
            ProfileOrganizerFragment profileOrganizerFragment = new ProfileOrganizerFragment();
            profileOrganizerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,profileOrganizerFragment).commit();
        }

        if(item.getItemId() == R.id.logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        return true;
    }


}

