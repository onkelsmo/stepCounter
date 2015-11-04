package com.onkelsmo.stepcounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView textView;
    private EventHandler eventHandler = new EventHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView)findViewById(R.id.counter);
        findViewById(R.id.set_back).setOnClickListener(this);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StepCounterService.eventHandler = eventHandler;
        textView.setText(String.valueOf(StepCounterService.steps));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.set_back) {
            StepCounterService.steps = 0;
            textView.setText("0");
        }
        if (v.getId() == R.id.start) {
            StepCounterService.eventHandler = eventHandler;
            startService(new Intent(this, StepCounterService.class));
        }
        if (v.getId() == R.id.stop) {
            stopService(new Intent(this, StepCounterService.class));
        }
    }

    private class EventHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            textView.setText(String.valueOf(msg.what));
        }
    }
}
