package com.vince.similartoapplehome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 *description:类似360桌面小球动画实现
 *author:vince
 *creator at:2016/4/19
 */
public class MainActivity extends Activity {  

    private TableShowView_Apple mTableShowView;
    @Override
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        mTableShowView = new TableShowView_Apple(this);
    }
    public void onClick(View v){

        if(v.getId() == R.id.btn_show){
            mTableShowView.show();
        }else if(v.getId() == R.id.btn_hide){
            mTableShowView.hide();
        }
    }
}  