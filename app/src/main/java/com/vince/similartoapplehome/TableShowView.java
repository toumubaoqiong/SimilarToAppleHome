package com.vince.similartoapplehome;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class TableShowView extends View {

    Context c;
    WindowManager mWM;
    WindowManager.LayoutParams mWMParams;
    View win;
    private AnimationDrawable mAnimationDrawable;

    int tag = 0;
    int oldOffsetX;
    int oldOffsetY;

    public TableShowView(Context context) {
        // TODO Auto-generated constructor stub
        super(context);
        c = context;
    }

    public void show() {
        mWM = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        win = LayoutInflater.from(c).inflate(R.layout.ctrl_window, null);
        mAnimationDrawable = (AnimationDrawable) ((ImageView) win.findViewById(R.id.pet)).getBackground();
        mAnimationDrawable.start();
        win.setBackgroundColor(Color.TRANSPARENT);
        win.setOnTouchListener(new OnTouchListener() {
            float lastX, lastY;

            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();

                float x = event.getX();
                float y = event.getY();

                if (tag == 0) {
                    oldOffsetX = mWMParams.x;
                    oldOffsetY = mWMParams.y;
                }

                if (action == MotionEvent.ACTION_DOWN) {
                    lastX = x;
                    lastY = y;

                } else if (action == MotionEvent.ACTION_MOVE) {
                    mWMParams.x += (int) (x - lastX);
                    mWMParams.y += (int) (y - lastY);

                    tag = 1;
                    mWM.updateViewLayout(win, mWMParams);
                } else if (action == MotionEvent.ACTION_UP) {
                    int newOffsetX = mWMParams.x;
                    int newOffsetY = mWMParams.y;
                    if (oldOffsetX == newOffsetX && oldOffsetY == newOffsetY) {
                        Toast.makeText(c, "hello world", 1).show();
                    } else {
                        tag = 0;
                    }
                }
                return true;
            }
        });

        WindowManager wm = mWM;
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        mWMParams = wmParams;
        wmParams.type = 2003;
        wmParams.flags = 40;

        wmParams.width = 155;
        wmParams.height = 160;
        wmParams.format = -3;
        wm.addView(win, wmParams);
    }

    public void hide() {
        mWM.removeView(win);
    }
}
