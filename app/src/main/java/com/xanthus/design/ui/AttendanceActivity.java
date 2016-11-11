package com.xanthus.design.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.xanthus.design.R;
import com.xanthus.design.utils.LToast;

/**
 * Generate a QrCode for the teacher, students scan it then.
 */
public class AttendanceActivity extends AppCompatActivity implements View.OnClickListener {

    private View generateCode;
    private View scanCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        generateCode = findViewById(R.id.attendance_generate_code);
        scanCode = findViewById(R.id.attendance_scan_code);
        generateCode.setOnClickListener(this);
        scanCode.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attendance_generate_code:
                startActivity(new Intent(this, QrCodeActivity.class));
                break;
            case R.id.attendance_scan_code:
                new IntentIntegrator(this)
                        .setOrientationLocked(true)
                        .initiateScan();
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                LToast.show(this, "Cancelled");
            } else {
                LToast.show(this, "Scanned: " + result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
