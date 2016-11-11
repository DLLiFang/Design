package com.xanthus.design.ui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xanthus.design.R;

public class ShakeActivity extends AppCompatActivity {
    private SensorManager sm;
    private SensorL listener;

    private boolean isRefresh = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        listener = new SensorL();
        sm.registerListener(listener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void showDialog() {
        Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        new MaterialDialog.Builder(this)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        isRefresh = false;
                    }
                }).build().show();
    }

    @Override
    protected void onPause() {
        sm.unregisterListener(listener);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sm.registerListener(listener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    private class SensorL implements SensorEventListener {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (isRefresh)
                    return;
                float newX = Math.abs(event.values[SensorManager.DATA_X]);
                float newY = Math.abs(event.values[SensorManager.DATA_Y]);
                float newZ = Math.abs(event.values[SensorManager.DATA_Z]);
                // X
                if (newX >= 18) {
                    isRefresh = true;
                    showDialog();
                    return;
                }
                // Y
                if (newY >= 20) {
                    isRefresh = true;
                    showDialog();
                    return;
                }
                // Z
                if (newZ >= 20) {
                    isRefresh = true;
                    showDialog();
                    return;
                }
            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                // Log.e("TYPE_MAGNETIC_FIELD", ""+event.sensor.toString());
            }
            if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                // Log.e("TYPE_PRESSURE", ""+event.sensor.toString());
            }
        }
    }

}
