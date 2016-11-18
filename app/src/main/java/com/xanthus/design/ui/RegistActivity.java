package com.xanthus.design.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.xanthus.design.R;
import com.xanthus.design.api.LSubscriber;
import com.xanthus.design.api.LApi;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.utils.LToast;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextAccount;
    private EditText mEditTextPwd;
    private EditText mEditTextPwdConfirm;
    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        compositeSubscription = new CompositeSubscription();
        mEditTextAccount = ((EditText) findViewById(R.id.register_account));
        mEditTextPwd = ((EditText) findViewById(R.id.register_password));
        mEditTextPwdConfirm = ((EditText) findViewById(R.id.register_password_confirm));
        findViewById(R.id.register_submit).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_submit:
                regist();

                break;
        }
    }

    private void regist() {
        String account = mEditTextAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            LToast.show(this, "Account can't be empty!");
            return;
        }
        String pwd = mEditTextPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            LToast.show(this, "Password can't be empty!");
            return;
        }
        String pwdConfirm = mEditTextPwdConfirm.getText().toString();
        if (!pwd.equals(pwdConfirm)) {
            LToast.show(this, "The passwords must be the same!");
            return;
        }
        Subscription subscribe = LApi.INSTANCE.regist(account, pwd).subscribe(new LSubscriber<Wrapper<Long>>() {
            @Override
            public void onNext(Wrapper<Long> longWrapper) {
                LToast.show(RegistActivity.this, "注册成功,请登录");
                finish();
            }
        });
        compositeSubscription.add(subscribe);

    }
}
