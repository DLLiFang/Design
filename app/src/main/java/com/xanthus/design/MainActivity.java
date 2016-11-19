package com.xanthus.design;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.xanthus.design.api.LApi;
import com.xanthus.design.api.LSubscriber;
import com.xanthus.design.bean.User;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.ui.AttendanceActivity;
import com.xanthus.design.ui.BBSFragment;
import com.xanthus.design.ui.FileFragment;
import com.xanthus.design.ui.FollowedFragment;
import com.xanthus.design.ui.LoginActivity;
import com.xanthus.design.ui.ProfileActivity;
import com.xanthus.design.ui.ShakeActivity;
import com.xanthus.design.utils.LToast;
import com.xanthus.design.utils.SPHelper;

import java.util.Timer;
import java.util.TimerTask;

import cn.qqtheme.framework.picker.FilePicker;
import cn.qqtheme.framework.util.StorageUtils;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private FragmentManager fm;
    private Fragment cacheFragment;
    private ImageView avatar;
    private TextView account;
    private TextView nicknaem;
    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();
        compositeSubscription = new CompositeSubscription();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        avatar = ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView));
        account = ((TextView) navigationView.getHeaderView(0).findViewById(R.id.textView));
        nicknaem = ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nickname));
        avatar.setOnClickListener(this);
        account.setOnClickListener(this);
        nicknaem.setOnClickListener(this);


        cacheFragment = FileFragment.newInstance(null, null);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.main_fragment_container, cacheFragment, FileFragment.TAG);
        transaction.commit();
        bindData();
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
    protected void onDestroy() {
        super.onDestroy();
        if (compositeSubscription != null)
            compositeSubscription.unsubscribe();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitBy2Click();
        }
    }

    private static boolean isExit = false;

    private void exitBy2Click() {
        Timer timer;
        if (!isExit) {
            isExit = true;
            LToast.show(this, "再按一次退出程序");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            //MobclickAgent.onKillProcess(this);
            //System.exit(0);
            finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView:
            case R.id.textView:
            case R.id.nickname:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }

    private void bindData() {
        User profile = SPHelper.getProfile(this);
        String avatarURL = profile.getAvatar();
        if (TextUtils.isEmpty(avatarURL)) {
            avatar.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(this).load(avatarURL).into(this.avatar);
        }
        nicknaem.setText(profile.getNickname());
        account.setText(profile.getUsername());
    }

}
