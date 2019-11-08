package com.example.lightsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    boolean start;
    Button buttonStartStop;
    float maxValue;

    private SensorManager sensorManager;
    private Sensor light;
    TextView textLightReading;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        start = true;

        TextView textLight = findViewById(R.id.textLight);
        textLightReading = findViewById(R.id.textLight);
        buttonStartStop = findViewById(R.id.button);
        seekBar = findViewById(R.id.seekBar);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        maxValue = light.getMaximumRange();

        seekBar.setMax((int) maxValue);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        Log.i("Here", "onCreate");
        if (light != null) {
            Log.i("Here", String.valueOf(light.getMaximumRange()));
        } else {
            Log.i("Here", "Null");
            textLightReading.setText("No Light Sensor.");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        Log.i("Here", "We go");
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        Log.i("Value", String.valueOf(maxValue));
        Log.i("value--", String.valueOf(event.values));
//        float lightValue = event.values[0];
//        // Do something with this sensor data.
//        float percentage = event.values[0] * 100 / maxValue;
        if (!start) {
            seekBar.setProgress((int) event.values[0]);
            textLightReading.setText("Sensor Reading: " + String.valueOf(event.values[0]));
        } else {
            textLightReading.setText("");
        }

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    public void onStartStop(View view) {
        if (start == true) {
            buttonStartStop.setText("Stop");
            start = false;
        } else {
            buttonStartStop.setText("Start");
            start = true;
        }
    }
}
