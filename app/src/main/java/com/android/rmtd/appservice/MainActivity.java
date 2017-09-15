package com.android.rmtd.appservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.rmtd.smartsdk.ISmartControlBinder;

public class MainActivity extends AppCompatActivity {
    Button mButton;
    ISmartControlBinder mControlBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent service = new Intent();
        service.setAction("action.smartcontrolservice");
        service.setPackage("com.android.rmtd.appservice");

        bindService(service, mSmartControlConn, Context.BIND_AUTO_CREATE);

        mButton = (Button) findViewById(R.id.btn_control);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mControlBinder==null){
                        Intent service = new Intent();
                        service.setAction("action.smartcontrolservice");
                        service.setPackage("com.android.rmtd.smartsdk");
                        bindService(service, mSmartControlConn, Context.BIND_AUTO_CREATE);
                        return;
                    }
                    mControlBinder.sendControlMsg("robot1","打开空调");

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }




    private ServiceConnection mSmartControlConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder!=null){
                mControlBinder = ISmartControlBinder.Stub.asInterface(iBinder);

            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mControlBinder = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mSmartControlConn);
    }

}
