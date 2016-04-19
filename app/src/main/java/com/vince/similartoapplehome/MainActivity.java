package com.vince.similartoapplehome;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 *description:仿苹果home按键动画
 *author:vince
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