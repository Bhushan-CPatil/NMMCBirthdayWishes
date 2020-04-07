package com.ornettech.nmmcqcandbirthdaywishes.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.ornettech.nmmcqcandbirthdaywishes.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    Context context=this;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    //private SectionsPagerAdapter mSectionsPagerAdapter;
    private boolean doubleBackToExitPressedOnce = false;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();

//        startService(new Intent(getApplicationContext(), DownloadDataService.class));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new TabLayout.
                TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_sync)
        {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean checkAndRequestPermissions()
    {
        List<String> listPermissionsNeeded = new ArrayList<>();

        int permissionAskSMSEAccess = ContextCompat.
                checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int permissionAskWRITEFILEAccess = ContextCompat.
                checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionAskREADFILEAccess = ContextCompat.
                checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionAskPhoneCall = ContextCompat.
                checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionAskWRITEFILEAccess != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionAskREADFILEAccess != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionAskSMSEAccess != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (permissionAskPhoneCall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    /*public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new MonthlyFragment();
                case 1:
                    return new DailyFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "मासिक";
                case 1:
                    return "दैनिक";
                default:
                    return null;
            }
        }
    }*/

    @Override
    public void onBackPressed()
    {
        /*if ( doubleBackToExitPressedOnce )
        {*/
            super.onBackPressed();
           // return;
        /*}

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click Back" +
                " again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);*/
    }
}
