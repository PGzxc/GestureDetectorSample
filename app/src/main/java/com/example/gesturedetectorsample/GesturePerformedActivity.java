package com.example.gesturedetectorsample;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class GesturePerformedActivity extends Activity {

    private final File mStoreFile = new File(
            Environment.getExternalStorageDirectory(), "gestures");

    // 手势库
    GestureLibrary mGestureLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gesture_perform);
        // 手势画板
        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures_overlay);

        gestures.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);

        gestures.setFadeOffset(2000); // 多笔画每两次的间隔时间
        gestures.setGestureColor(Color.CYAN);// 画笔颜色
        gestures.setGestureStrokeWidth(6);// 画笔粗细值

        // 手势识别的监听器
        gestures.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay,
                                           Gesture gesture) {
                // 从手势库中查询匹配的内容，匹配的结果可能包括多个相似的结果，匹配度高的结果放在最前面
                ArrayList<Prediction> predictions = mGestureLib
                        .recognize(gesture);
                if (predictions.size() > 0) {
                    Prediction prediction = (Prediction) predictions.get(0);
                    // 匹配的手势
                    if (prediction.score > 1.0) { // 越匹配score的值越大，最大为10
                        Toast.makeText(GesturePerformedActivity.this,
                                prediction.name, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        if (mGestureLib == null) {
            mGestureLib = GestureLibraries.fromFile(mStoreFile);
            mGestureLib.load();
        }
    }

}
