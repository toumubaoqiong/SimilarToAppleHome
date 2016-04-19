package com.vince.similartoapplehome;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class TableShowView_Apple extends View {

    Context c;
    WindowManager mWM;
    WindowManager.LayoutParams mWMParams;
    View win;
    private AnimationDrawable mAnimationDrawable;

    int tag = 0;
    int oldOffsetX;
    int oldOffsetY;
    float screenWidth;
    float screenHeight;
    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;

    public TableShowView_Apple(Context context) {
        // TODO Auto-generated constructor stub
        super(context);
        this.c = context;
        screenWidth = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        screenHeight = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
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

                    switch (getPos((int)event.getRawX(), (int)event.getRawY() - getStatusBarHeight())){
                        case 0://左
                            mWMParams.x = 0;
                            break;
                        case 1://右
                            mWMParams.x = (int)screenWidth;
                            break;
                        case 2://上
                            mWMParams.y = 0;
                            break;
                        case 3://下
                            mWMParams.y =  (int)screenHeight;
                            break;
                        default:
                            break;
                    }

                    if (oldOffsetX == newOffsetX && oldOffsetY == newOffsetY) {
                        Toast.makeText(c, "hello world", 1).show();
                    } else {
                        mWM.updateViewLayout(win, mWMParams);
                        tag = 0;
                    }
                }
                return true;
            }
        });

        WindowManager wm = mWM;
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        mWMParams = wmParams;

        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;//指定位置定为原点，暂时还不知道原因

        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.x = (int)screenWidth;
        wmParams.y = (int)(screenHeight/2 - getStatusBarHeight()/2);
        Log.i("TableShowView_Apple----------------------------->wmParams.y",wmParams.y + "");
        wm.addView(win, wmParams);
    }

    public void hide() {
        mWM.removeView(win);
    }


    //左 0 右 1 上 2 下 3
    public int getPos(int x, int y) {

        int leftDistance = x;
        int rightDistance = (int) (screenWidth - x);
        int topDistance = y;
        int bottomDistance = (int) (screenHeight - y);
        int pos = 0;
        int max;

        max = getMin(leftDistance, rightDistance, topDistance, bottomDistance);

        if(max == leftDistance){
            pos = 0;
        }else if(max == rightDistance){
            pos = 1;
        }else if(max == topDistance){
            pos = 2;
        }else{
            pos = 3;
        }

        return pos;
    }

    //获取最大值
    public int getMin(int leftDistance, int rightDistance, int topDistance, int bottomDistance) {

        int minlr;
        int mintb;

        minlr = leftDistance < rightDistance ? leftDistance : rightDistance;
        mintb = topDistance < bottomDistance ? topDistance : bottomDistance;
        return minlr < mintb ? minlr : mintb;
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}
