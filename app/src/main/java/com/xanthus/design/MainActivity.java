package com.xanthus.design;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xanthus.design.ui.AttendanceActivity;
import com.xanthus.design.ui.BBSFragment;
import com.xanthus.design.ui.FileFragment;
import com.xanthus.design.ui.FollowedFragment;
import com.xanthus.design.ui.LoginActivity;
import com.xanthus.design.ui.ShakeActivity;
import com.xanthus.design.utils.LToast;

import cn.qqtheme.framework.picker.FilePicker;
import cn.qqtheme.framework.util.StorageUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fm;
    private Fragment cacheFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        cacheFragment = FileFragment.newInstance(null, null);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.main_fragment_container, cacheFragment, FileFragment.TAG);
        transaction.commit();
    }

    private void changeFragment(String tag) {
        Fragment temp = fm.findFragmentByTag(tag);
        FragmentTransaction transaction = fm.beginTransaction();
        if (temp != null) {
            transaction.hide(cacheFragment);
            transaction.show(temp);
        } else {
            switch (tag) {
                case FileFragment.TAG:
                    temp = FileFragment.newInstance(null, null);
                    break;
                case BBSFragment.TAG:
                    temp = BBSFragment.newInstance();
                    break;
                case FollowedFragment.TAG:
                    temp = new FollowedFragment();
                    break;
                case "some fragment else":
                    // get a new one
                    break;
            }
            transaction.hide(cacheFragment);
            transaction.add(R.id.main_fragment_container, temp, tag);
        }
        transaction.commit();
        cacheFragment = temp;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_files:
                changeFragment(FileFragment.TAG);
                break;
            case R.id.nav_bbs:
                changeFragment(BBSFragment.TAG);
                break;
            case R.id.nav_follows:
                changeFragment(FollowedFragment.TAG);
                break;
            case R.id.nav_template:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.nav_qr_code:
                startActivity(new Intent(this, AttendanceActivity.class));
                break;
            case R.id.nav_shake:
                startActivity(new Intent(this, ShakeActivity.class));
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
