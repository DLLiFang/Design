package com.xanthus.design.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xanthus.design.DesignApp;
import com.xanthus.design.MainActivity;
import com.xanthus.design.R;
import com.xanthus.design.api.LSubscriber;
import com.xanthus.design.api.LApi;
import com.xanthus.design.bean.User;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.utils.LToast;
import com.xanthus.design.utils.SPHelper;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {


    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String token = SPHelper.getTokenToken(this);
        Log.e("Login", "token:" + token + ":");

        if (!TextUtils.isEmpty(token)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        compositeSubscription = new CompositeSubscription();
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);
        findViewById(R.id.login_register).setOnClickListener(this);
        mLoginFormView = findViewById(R.id.login_form);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeSubscription != null) compositeSubscription.unsubscribe();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("请输入用户名");
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mEmailView.setError("请输入密码");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Subscription subscribe = LApi.INSTANCE.login(email, password).subscribe(new LSubscriber<Wrapper<User>>() {
                @Override
                public void onNext(Wrapper<User> wrapper) {
                    User user = wrapper.getResult();
                    if (user != null) {
                        SPHelper.saveProfile(LoginActivity.this, user);
                        String s = user.getId() + ":" + user.getToken();
                        SPHelper.saveToken(LoginActivity.this, s);
                        LApi.INSTANCE.update();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else {
                        LToast.show(LoginActivity.this,wrapper.getMessage());
                    }
                }
            });
            compositeSubscription.add(subscribe);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_register:
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
        }
    }
}

