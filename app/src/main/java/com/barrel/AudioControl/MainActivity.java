package com.barrel.AudioControl;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Intent intent;
    private static final int REQUEST_RECORD_AUDIO = 13;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = (TextView) findViewById(R.id.txtResult);
        txtResult.setText(
                "注意事项：\n"+
                "本插件使用的是百度在线语音识别，使用时请注意联网\n"+
                "\n" +
                "通用语音识别服务功能：\n" +
                "通过语音口令“设置拍照”和“设置录像”，启动按钮设置功能\n" +
                "跳出识别结果后：\n" +
                "“设置拍照”请按一下拍照按钮，让程序记录\n" +
                "“设置录像”请按一下录像按钮，再按一下来停止录像，复位状态\n" +
                "设置完成后，说出识别词“拍照”，插件将会自动点击拍照按钮\n" +
                "说出识别词“开始录像”将会开始录像\n" +
                "说出识别词“停止录像”将会停止录像\n" +
                "\n" +
                "预设语音识别服务功能：\n" +
                "预设语音识别服务，直接在后台程序设置好了相关按键\n" +
                "预设对应测试软件为奇点行车记录仪\n" +
                "说出识别词“拍照”，插件将会自动点击拍照按钮\n" +
                "说出识别词“开始录像”将会开始录像\n" +
                "说出识别词“停止录像”将会停止录像\n"
        );

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAccessibilitySettingsOn(MainActivity.this, MyService.class.getCanonicalName()))
                    //若是辅助功能没有开启，则跳转到辅助功能管理界面
                {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                }
                else
                    //若是辅助功能已经开启，则直接开启服务MyService
                {
                    intent = new Intent(MainActivity.this, MyService.class);
                    startService(intent);
                }
            }
        });

        findViewById(R.id.startspecial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAccessibilitySettingsOn(MainActivity.this, MySpecialService.class.getCanonicalName()))
                //若是辅助功能没有开启，则跳转到辅助功能管理界面
                {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                }
                else
                //若是辅助功能已经开启，则直接开启服务MyService
                {
                    intent = new Intent(MainActivity.this, MySpecialService.class);
                    startService(intent);
                }
            }
        });

        PackageManager pkgManager= getPackageManager();
        boolean audioSatePermission = pkgManager.checkPermission(Manifest.permission.RECORD_AUDIO, getPackageName()) == PackageManager.PERMISSION_GRANTED;
            //获取麦克风权限，6.0 版本以下直接获取麦克风权限，6.0 以上版本则动态申请权限
        if(Build.VERSION.SDK_INT>=23&&!audioSatePermission)
        {
            requestMicrophonePermission();
        }
    }


    /**
     * 检测辅助功能是否开启
     *
     * @param mContext
     * @return boolean
     */
    private boolean isAccessibilitySettingsOn(Context mContext, String serviceName) {
        int accessibilityEnabled = 0;
        // 对应的服务
        final String service = getPackageName() + "/" + serviceName;
        //Log.i(TAG, "service:" + service);
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }
        return false;
    }

    private void requestMicrophonePermission()
        //动态申请权限的方法
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        }
    }
}
