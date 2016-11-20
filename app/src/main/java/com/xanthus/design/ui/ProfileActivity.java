package com.xanthus.design.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.xanthus.design.R;
import com.xanthus.design.api.LConstants;
import com.xanthus.design.api.LApi;
import com.xanthus.design.api.LSubscriber;
import com.xanthus.design.bean.FileBean;
import com.xanthus.design.bean.User;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.utils.LToast;
import com.xanthus.design.utils.SPHelper;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;

public class ProfileActivity extends BaseActivity implements View.OnClickListener, GalleryFinal.OnHanlderResultCallback {

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
                GalleryFinal.openGallerySingle(123,this);
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
            Glide.with(this).load(LConstants.FILE_PRE + avatarURL).into(mAvatar);
        }
        nickname.setText(profile.getNickname());
        username.setText(profile.getUsername());
        gender.setText(profile.getGender() == 2 ? "女" : "男");
    }

    @Override
    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
        if (reqeustCode == 123) {
            if (resultList != null && resultList.size() != 0) {
                String path = resultList.get(0).getPhotoPath();
                final File file = new File(path);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/otcet-stream"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
                String descriptionString = "This is a description.";
                RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);


                Subscription subscribe1 = LApi.INSTANCE.upload(description, body, 1).subscribe(new LSubscriber<Wrapper<FileBean>>() {
                    @Override
                    public void onNext(Wrapper<FileBean> userWrapper) {
                        FileBean result = userWrapper.getResult();
                        String name = result.getName();
                        User profile = SPHelper.getProfile(mContext);
                        profile.setAvatar(name);
                        SPHelper.saveProfile(mContext, profile);
                        bindData();

                    }
                });
                compositeSubscription.add(subscribe1);
            }
        }
    }

    @Override
    public void onHanlderFailure(int requestCode, String errorMsg) {
        LToast.show(this, errorMsg);
    }
}
