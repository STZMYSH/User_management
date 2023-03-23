package com.example.zhuce_xxff;

import android.view.View;

public class ViewClickVibrate implements View.OnClickListener {
    private final int VIBRATE_TIME = 60;
    @Override
    public void onClick(View v) {
        VibrateHelp.vSimple(v.getContext(), VIBRATE_TIME);
    }
}