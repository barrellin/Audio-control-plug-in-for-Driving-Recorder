package com.barrel.AudioControl;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.IRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener;
import com.baidu.speech.EventListener;
import com.baidu.speech.asr.SpeechConstant;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyService extends AccessibilityService
{

    private static final String TAG = "AudioControl";

    @Override
    protected void onServiceConnected()
    {
        super.onServiceConnected();
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        //配置监听的事件类型为界面变化 点击事件 和窗口内容变化

        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        if (Build.VERSION.SDK_INT >= 18)
            //SDK18 以上可以直接使用ffindAccessibilityNodeInfosByViewId
        {
            config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        }

        setServiceInfo(config);
        //设置AccessibilityService 的相关设置

        Handler handler = new Handler();

        IRecogListener listener = new MessageStatusRecogListener(handler);
        //百度语音识别SDK MyRecognizer 所需要的相关传参

        myRecognizer = new MyRecognizer(this, new EventListener()
        {
            @Override
            public void onEvent(String s, String s1, byte[] bytes, int i, int i1) {

                //该方法是异步语音识别的回调
                //System.out.println("===================s: " + s);
                //System.out.println("===================result: " + s1);
                //通过观察输出，可以发现
                //s 是回调类型 partial  finish  exit
                //这个s1 就是回调的json串
                //读取出一个s 类型为partial 的s1 :{"results_recognition":["开始识别"],"origin_result":{"corpus_no":6671252030454840276,"err_no":0,"result":{"word":["开始识别"]},"sn":"2a2a8440-ac82-4598-b9d8-48c7ee5504f5","voice_energy":39887},"error":0,"best_result":"开始识别","result_type":"final_result"}
                //因此，只需要对s1 的json 串进行解析，读取出results_recognition 便可以获得一个识别的结果

                if(s.equals("asr.partial"))
                {
                    //System.out.println("========================== in partial");
                    String json = s1;
                    //解析json串
                    Gson gson = new Gson();
                    AsrPartial result = gson.fromJson(json, AsrPartial.class);
                    //使用Gson 包来解析json 串
                    if(result != null && result.results_recognition != null && result.results_recognition.length != 0)
                    {
                        System.out.println(result.results_recognition[0]);
                        //输出一次观察结果是否无误
                    }
                    Myresult = result.results_recognition[0];
                    //用Myresult 存入语音识别的结果，用于判断备用

                    processAfterGetResult(Myresult);
                    //解析json 之后的操作逻辑，对语音识别结果进行处理，并且使用AccessibilityService来点击屏幕
                }
            }
        });
    }

    AccessibilityNodeInfo NodeInfoCurrent = null;
    //此参数用于中转AccessibilityService 所需要的节点信息

    AccessibilityNodeInfo NodePhoto = null;
    //记录拍照按钮的节点信息

    boolean PhotoFlag = false;
    //用于记录是否激活设置拍照按钮的功能

    AccessibilityNodeInfo NodeVideo = null;
    //记录录像按钮的节点信息

    boolean VideoFlag = false;
    //用于记录是否激活设置录像按钮的功能

    boolean RecordFlag = false;
    //此参数用于录像按钮模拟点击的逻辑判断

    protected void processAfterGetResult(String Myresult)
    {
        //if(NodeInfoCurrent == null)return;
        //如果当前还没有节点信息，则不进行点击逻辑处理

        if(Myresult == null || Myresult.equals("默认"))return;
        //若Myresult 为空值，或者为“默认值”，则也不进行点击逻辑处理

        Log.i(TAG, "结果是：“"+ Myresult +"”" );
        //Log 输出一下识别结果，同时在调试时起到执行到这里的标记的作用

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        //Toast 相关使用的初始化操作

        if (Myresult.equals("开始录像") && RecordFlag == false && NodeVideo!=null)
            //这个RecordFlag 用来判断是否为录音状态，来选择是“开始”还是“停止”
        {
            CharSequence text = "识别结果是："+Myresult;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //用Toast 在屏幕中弹出识别结果，用于鉴别识别是否已经成功

            Log.i(TAG, "time for video");
            //log 输出，说明程序已经执行到这一步骤

            NodeVideo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            //核心功能，使用AccessibilityService 来进行模拟点击

            RecordFlag = true;
            //更改flag，实现开始/停止录像的逻辑
        }

        if (Myresult.equals("停止录像") && RecordFlag == true && NodeVideo!=null)
            //这个RecordFlag 用来判断是否为录音状态，来选择是“开始”还是“停止”
        {
            CharSequence text = "识别结果是：" +Myresult;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //用Toast 在屏幕中弹出识别结果，用于鉴别识别是否已经成功

             Log.i(TAG, "time for video");
            //log 输出，说明程序已经执行到这一步骤

            NodeVideo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            //核心功能，使用AccessibilityService 来进行模拟点击

            RecordFlag = false;
            //更改flag，实现开始/停止录像的逻辑
        }

        if (Myresult.equals("拍照") && NodePhoto!=null)
        {
            CharSequence text = "识别结果是：" +Myresult;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //用Toast 在屏幕中弹出识别结果，用于鉴别识别是否已经成功

            Log.i(TAG, "time for photo");
            //log 输出，说明程序已经执行到这一步骤

            NodePhoto.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            //核心功能，使用AccessibilityService 来进行模拟点击
        }

        if (Myresult.equals("设置拍照"))
        {
            CharSequence text = "识别结果是：" +Myresult;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //用Toast 在屏幕中弹出识别结果，用于鉴别识别是否已经成功
            PhotoFlag = true;
        }

        if (Myresult.equals("设置录像"))
        {
            CharSequence text = "识别结果是：" +Myresult;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //用Toast 在屏幕中弹出识别结果，用于鉴别识别是否已经成功
            VideoFlag = true;
        }

    }

    int i = 0;
    //此参数用于记录AccessibilityService 设置的监听的事件的发生次数

    public String Myresult = "默认";
    //此参数为语音识别的结果，在语音识别的回调里面进行赋值，在每次发生AccessibilityService 事件的时候初始化为“默认”

    protected MyRecognizer myRecognizer;
    //百度语音识别的MyReconizer

    /**
     * 开始录音。
     */
    protected void start()
    {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        //调起百度语音识别引擎所需要的参数设置
        params.put(SpeechConstant.ACCEPT_AUDIO_DATA, false);
        //是否保存音频
        params.put(SpeechConstant.DISABLE_PUNCTUATION, false);
        //是否禁用标点符号，在选择输入法模型的前提下生效【不禁用的话，说完一段话，就自带标点符号】
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        //暂时不知道什么意思，应该是语音音量相关的设置
        params.put(SpeechConstant.PID, 1536);
        //此PID 对应普通话search 搜索模型，默认，适用于短句，无逗号，可以有语义
        //params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0);
        // 长语音，搭配input输入法模型
        myRecognizer.start(params);
        //调起语音识别引擎
    }

    /**
     * 开始录音后，手动停止录音。SDK会识别在此过程中的录音。
     */
    private void stop()
    {
        myRecognizer.stop();
        //停止语音识别引擎，并执行识别
    }

    /**
     * 开始录音后，取消这次录音。SDK会取消本次识别，回到原始状态。
     */
    private void cancel()
    {
        myRecognizer.cancel();
        //停止语音识别引擎，并取消识别
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event)
    {
        Log.i(TAG, "times:"+ i );
        //输出监听的事件的发生次数，也是该服务的发生次数

        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED && PhotoFlag == true)
        //当前事件为点击事件时，一般为语音识别结束后的模拟点击
        {
            NodePhoto = event.getSource();
            //节点获取为当前事件的触发源，即工作界面的位置

            Log.i(TAG, "因为点击事件而重置");
            //Log 输出重置识别结果的事件名

            Myresult = "默认";
            //重置语音识别的结果为“默认”

            PhotoFlag = false;
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED && VideoFlag == true)
        //当前事件为点击事件时，一般为语音识别结束后的模拟点击
        {
            NodeVideo = event.getSource();
            //节点获取为当前事件的触发源，即工作界面的位置

            Log.i(TAG, "因为点击事件而重置");
            //Log 输出重置识别结果的事件名

            Myresult = "默认";
            //重置语音识别的结果为“默认”

            VideoFlag  = false;
        }

        start();
        //调起百度语音识别引擎，该引擎工作与AccessibilityService 是异步的

        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
            //当前事件为窗口界面切换时
        {
            ComponentName componentName = new ComponentName(event.getPackageName().toString(), event.getClassName().toString());
            Log.i(TAG, "当前Activity为：" + componentName.flattenToShortString());
            //if (componentName.flattenToShortString().equals("com.smartcar.carrecorder/.sc_activity.SCCarRecorderAct")){ }

            Log.i(TAG, "因为窗口切换而重置");
            //Log 输出重置识别结果的事件名

            Myresult = "默认";
            //重置语音识别的结果为“默认”
        }

        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)
            //当前事件为窗口像素发生变化时，为了时刻让后台语音识别引擎就绪，用该事件来持续刷新，达到不用点击按钮来持续触发语音识别引擎工作的效果
        {
            ComponentName componentName = new ComponentName(event.getPackageName().toString(), event.getClassName().toString());
            Log.i(TAG, "当前Activity为：" + componentName.flattenToShortString());
            //if (componentName.flattenToShortString().equals("com.smartcar.carrecorder/.sc_activity.SCCarRecorderAct")){ }

            Log.i(TAG, "因为窗口变化而重置");
            //Log 输出重置识别结果的事件名

            Myresult = "默认";
            //重置语音识别的结果为“默认”
        }
        i++;
    }

    private ActivityInfo tryGetActivity(ComponentName componentName)
    {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onInterrupt()
    {
    }

}