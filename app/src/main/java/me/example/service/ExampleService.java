package me.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import me.example.IExample;

public class ExampleService extends Service {

    public static final String EXAMPLE_AIDL_ACTION = "example.me.action.aidl";

    public ExampleService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");

        return new ExampleImpl();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private class ExampleImpl extends IExample.Stub {
        @Override
        public int compute(int a, int b) throws RemoteException {
            return a + b;
        }
    }
}
