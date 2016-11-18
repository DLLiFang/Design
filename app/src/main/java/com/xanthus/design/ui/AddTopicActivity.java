package com.xanthus.design.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xanthus.design.R;
import com.xanthus.design.api.LApi;
import com.xanthus.design.api.LSubscriber;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.utils.LToast;

public class AddTopicActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdiText;
    private MaterialDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        mEdiText = ((EditText) findViewById(R.id.add_topic_et));
        findViewById(R.id.add_topic_submit).setOnClickListener(this);
        mDialog = new MaterialDialog.Builder(this).progress(true, 100, false).progressIndeterminateStyle(true).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_topic_submit:
                submit();

                break;
        }
    }

    private void submit() {
        String content = mEdiText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            LToast.show(this, "Can't be empty!");
            return;
        }
        mDialog.show();
        LApi.INSTANCE.addTopic(content).subscribe(new LSubscriber<Wrapper<Long>>() {
            @Override
            public void onNext(Wrapper<Long> longWrapper) {
                mDialog.dismiss();
                if (longWrapper.getResultCode() == 0) {
                    LToast.show(AddTopicActivity.this, "发布成功");
                    finish();
                } else {
                    LToast.show(AddTopicActivity.this, longWrapper.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mDialog.dismiss();
            }
        });
    }
}
