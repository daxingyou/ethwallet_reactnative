package com.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xumin on 2018/1/16.
 */

public class ReactNativeMessageHandler extends ReactContextBaseJavaModule {
    public static final String START_QRCODE_SCAN="scan_qrcod";
    public static final String TAG="MessageHandler";
    private final int REQUEST_CODE=100;
    private ReactApplicationContext mContext;
    private Promise mPromise;
    public ReactNativeMessageHandler(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext=reactContext;
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    private final ActivityEventListener mActivityEventListener=new BaseActivityEventListener(){
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(activity, requestCode, resultCode, data);不需要super
            Log.i(TAG, "requestCode:" + requestCode+"     resultCode="+resultCode);
            if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK) {
                String result = data.getExtras().getString("result");//得到新Activity关闭后返回的数据
                Log.i(TAG, "result:" + result);
                mPromise.resolve(result);
            }else {
                Log.i(TAG, "resultfunkdjflkajd:" );
            }
        }
    };

    @Override
    public String getName() {
        return "ReactNativeMessageHandler";
    }

    @ReactMethod
    public void handleMessage(String msg, Promise promise){
        mPromise=promise;
        if(START_QRCODE_SCAN.equals(msg)){
            Intent intent=new Intent(mContext,QrcodeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivityForResult(intent,REQUEST_CODE,new Bundle());
        }
    }
}
