package com.example.apprunner.User.menu1_home.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.apprunner.User.menu7_check_reward.Fragment.CheckStatusRewardFragment;
import com.example.apprunner.User.menu5_uplode_stat.Fragment.EventProfileUplodeFragment;
import com.example.apprunner.User.menu2_profile_event.Fragment.EventUserViewStatisticsFragment;
import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Organizer.menu7_help_organizer.Fragment.HelpFragment;
import com.example.apprunner.User.menu6_profile_user.Fragment.ProfileUserFragment;
import com.example.apprunner.R;
import com.example.apprunner.User.menu1_home.Fragment.SecondFragment;
import com.example.apprunner.User.menu3_submit_payment.Freagment.SelectPaymentUserFragment;
import com.example.apprunner.User.menu4_check_payment.Fragment.StatusPaymentUserFragment;
import com.google.android.material.navigation.NavigationView;

public class  SecondActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String first_name,last_name,type;
    int id_user;

        public static final String url = "http://10.199.66.18:3000/";
//        public static final String url = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_user);
        navigationView = findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer_user,new SecondFragment());
        fragmentTransaction.commit();

        Bundle bundle = new Bundle();
        bundle.putInt("id_user", getIntent().getExtras().getInt("id_user"));
        bundle.putString("first_name", getIntent().getExtras().getString("first_name"));
        bundle.putString("last_name",getIntent().getExtras().getString("last_name"));
        bundle.putString("type",getIntent().getExtras().getString("type"));
        bundle.putString("email", getIntent().getExtras().getString("email"));
        bundle.putString("password",getIntent().getExtras().getString("password"));
        SecondFragment secondFragment = new SecondFragment();
        secondFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_user,secondFragment).commit();

        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        id_user = getIntent().getExtras().getInt("id_user");
        //Toast.makeText(SecondActivity.this, Integer.toString(id_user), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Bundle bundle = new Bundle();
        bundle.putString("first_name", first_name);
        bundle.putString("last_name",last_name);
        bundle.putString("type",type);
        bundle.putInt("id_user",id_user);
        if(item.getItemId() == R.id.home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new SecondFragment());
            fragmentTransaction.commit();
            SecondFragment secondFragment = new SecondFragment();
            secondFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_user,secondFragment).commit();
        }

        if(item.getItemId() == R.id.event) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new EventUserViewStatisticsFragment());
            fragmentTransaction.commit();
            EventUserViewStatisticsFragment eventUserViewStatisticsFragment = new EventUserViewStatisticsFragment();
            eventUserViewStatisticsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_user,eventUserViewStatisticsFragment).commit();
        }

        if(item.getItemId() == R.id.submit_payment) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new SelectPaymentUserFragment());
            fragmentTransaction.commit();
            SelectPaymentUserFragment selectPaymentUserFragment = new SelectPaymentUserFragment();
            selectPaymentUserFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_user,selectPaymentUserFragment).commit();
        }

        if(item.getItemId() == R.id.status_payment) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new StatusPaymentUserFragment());
            fragmentTransaction.commit();
            StatusPaymentUserFragment statusPaymentUserFragment = new StatusPaymentUserFragment();
            statusPaymentUserFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_user,statusPaymentUserFragment).commit();
        }

        if(item.getItemId() == R.id.uplodestatistics) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new EventProfileUplodeFragment());
            fragmentTransaction.commit();
            EventProfileUplodeFragment eventProfileUplodeFragment = new EventProfileUplodeFragment();
            eventProfileUplodeFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_user,eventProfileUplodeFragment).commit();
        }

        if(item.getItemId() == R.id.profile) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new ProfileUserFragment());
            fragmentTransaction.commit();
            ProfileUserFragment profileUserFragment = new ProfileUserFragment();
            profileUserFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_user,profileUserFragment).commit();
        }

        if(item.getItemId() == R.id.status_reward) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new CheckStatusRewardFragment());
            fragmentTransaction.commit();
            CheckStatusRewardFragment checkStatusRewardFragment = new CheckStatusRewardFragment();
            checkStatusRewardFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_user,checkStatusRewardFragment).commit();
        }

        if(item.getItemId() == R.id.logout) {
            Intent intent = new Intent(SecondActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        return true;
    }
}