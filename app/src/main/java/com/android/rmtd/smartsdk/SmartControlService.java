package com.android.rmtd.smartsdk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.android.rmtd.appservice.ResultUtils;

/**
 * Created by melo on 2017/9/13.
 */
public class SmartControlService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SmartControlBinder();
    }


    public class SmartControlBinder extends ISmartControlBinder.Stub {

        IControlTaskCallback cb;

        @Override
        public int controlResult(int result) throws RemoteException {

            ResultUtils.controlResult(result);
            return result;
        }

        @Override
        public int equipmentState(int state) throws RemoteException {
            ResultUtils.controlResult(state);
            return state;
        }

        @Override
        public void sendControlMsg(String robotId, String controlCode) throws RemoteException {
            controlEquipment(robotId, controlCode);
        }

        @Override
        public void registerCallback(IControlTaskCallback cb) throws RemoteException {
            if (cb != null) {
                mCallbacks.register(cb);
                this.cb = cb;
            }
        }

        @Override
        public void unregisterCallback(IControlTaskCallback cb) throws RemoteException {
            if (cb != null) {
                mCallbacks.unregister(cb);
            }
        }

        //发送指令
        void controlEquipment(String robotId, String controlCode) throws RemoteException {
            if (cb != null) {
                cb.controlMsg(robotId, controlCode);
            }
        }


        void callback(String robotId, String controlCode) {
            final int N = mCallbacks.beginBroadcast();
            for (int i = 0; i < N; i++) {
                try {
                    mCallbacks.getBroadcastItem(i).controlMsg(robotId, controlCode);
                } catch (RemoteException e) {
                    // The RemoteCallbackList will take care of removing
                    // the dead object for us.
                }
            }
            mCallbacks.finishBroadcast();
        }


        final RemoteCallbackList<IControlTaskCallback> mCallbacks = new RemoteCallbackList<IControlTaskCallback>();

    }

}
