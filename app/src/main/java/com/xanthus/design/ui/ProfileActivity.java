package com.xanthus.design.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.xanthus.design.MainActivity;
import com.xanthus.design.R;
import com.xanthus.design.api.LApi;
import com.xanthus.design.api.LSubscriber;
import com.xanthus.design.bean.User;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.utils.LToast;
import com.xanthus.design.utils.SPHelper;

import rx.Subscription;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private TextView nickname;
    private ImageView mAvatar;
    private TextView gender;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nickname = ((TextView) findViewById(R.id.profile_nickname));
        mAvatar = ((ImageView) findViewById(R.id.profile_avatar));
        gender = ((TextView) findViewById(R.id.profile_gender));
        username = ((TextView) findViewById(R.id.profile_username));
        mAvatar.setOnClickListener(this);
        gender.setOnClickListener(this);
        nickname.setOnClickListener(this);
        bindData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_nickname:
                new MaterialDialog.Builder(this)
                        .input("昵称", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                if (input.length() == 0) {
                                    LToast.show(ProfileActivity.this, "请输入昵称");
                                    return;
                                }
                                setUserProfile(input.toString());
                            }
                        }).build().show();

                break;
            case R.id.profile_avatar:
                break;
            case R.id.profile_gender:
                new MaterialDialog.Builder(this).items("男", "女").itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        changeGender(position);
                    }
                }).build().show();
                break;

        }
    }

    private void changeGender(int position) {
        LApi.INSTANCE.modifyGender(position + 1).subscribe(new LSubscriber<Wrapper<User>>() {
            @Override
            public void onNext(Wrapper<User> wrapper) {
                if (wrapper.getResult() != null) {
                    wrapper.getResult();
                    SPHelper.saveProfile(ProfileActivity.this, wrapper.getResult());
                    LToast.show(mContext, "修改成功");
                    bindData();
                } else {
                    LToast.show(mContext, wrapper.getMessage());
                    if (wrapper.getResultCode() == 2) {
                        SPHelper.saveToken(ProfileActivity.this, "");
                    }
                }
            }
        });
    }

    private void setUserProfile(String nickname) {
        Subscription subscribe = LApi.INSTANCE.modifyNickname(nickname).subscribe(new LSubscriber<Wrapper<User>>() {
            @Override
            public void onNext(Wrapper<User> userWrapper) {
                if (userWrapper.getResult() != null) {
                    userWrapper.getResult();
                    SPHelper.saveProfile(ProfileActivity.this, userWrapper.getResult());
                    LToast.show(mContext, "修改成功");
                    bindData();
                } else {
                    LToast.show(mContext, userWrapper.getMessage());
                    if (userWrapper.getResultCode() == 2) {
                        SPHelper.saveToken(ProfileActivity.this, "");
                    }
                }
            }
        });
        compositeSubscription.add(subscribe);
    }

    private void bindData() {
        User profile = SPHelper.getProfile(this);
        String avatarURL = profile.getAvatar();
        if (TextUtils.isEmpty(avatarURL)) {
            mAvatar.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(this).load(avatarURL).into(mAvatar);
        }
        nickname.setText(profile.getNickname());
        username.setText(profile.getUsername());
        gender.setText(profile.getGender() == 2 ? "女" : "男");
    }
}
