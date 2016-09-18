package me.example;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import me.example.service.ExampleService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int A = 3;
    private static final int B = 6;
    private static final String PLUS = A + " + " + B + " = ";

    private TextView textView;
    private IExample iExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.tv);

        textView.setText(PLUS + "unbind");

        findViewById(R.id.bind).setOnClickListener(this);
        findViewById(R.id.unbind).setOnClickListener(this);
        findViewById(R.id.compute).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iExample != null) {
            unbindService(connection);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind:
                bindExampleService();
                textView.setText(PLUS + "bind");
                break;
            case R.id.unbind:
                if (iExample != null) {
                    unbindService(connection);
                }
                iExample = null;
                textView.setText(PLUS + "unbind");
                break;
            case R.id.compute:
                doCompute();
                break;
            default:
                break;
        }
    }

    private void bindExampleService() {
        Intent intent = new Intent(ExampleService.EXAMPLE_AIDL_ACTION);
        intent.setPackage(getPackageName());
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private void doCompute() {
        if (iExample == null) {
            return;
        }

        try {
            final int result = iExample.compute(A, B);
            textView.setText(A + " + " + B + " = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iExample = IExample.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
