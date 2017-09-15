package com.android.rmtd.appservice;

import android.util.Log;

/**
 * Created by duyanfeng on 2017/9/13.
 */
public class ResultUtils {

    /**
     * 智能家居传递结果
     * @param result  返回控制结果
     * @return  返回控制结果
     */
    public static int controlResult(int result){

        Log.i("tag","result:"+result);

        return 100;
    }

    /**
     * 智能家居传递结果
     * @param state  返回控制结果
     * @return  返回控制结果
     */
    public static int equipmentState(int state){

        Log.i("tag","state:"+state);

        return 200;
    }
}
