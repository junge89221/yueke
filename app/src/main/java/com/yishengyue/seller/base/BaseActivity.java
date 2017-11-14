package com.yishengyue.seller.base;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.yishengyue.seller.util.AppManager;


public   class BaseActivity extends AppCompatActivity   {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onDestroy() {
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }
    public void moveDialog(final FrameLayout rootFrame, final RelativeLayout dialogRelative){
        rootFrame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootFrame.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootFrame.getRootView().getHeight();
                int heightDifference = screenHeight - (r.bottom - r.top);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) dialogRelative.getLayoutParams();
                if(heightDifference>300){
                    //键盘弹出
                    params.setMargins(0, -80, 0, 0);
                }else {
                    //键盘收齐
                    params.setMargins(0, 80, 0, 0);
                }
                dialogRelative.setLayoutParams(params);
            }
        });
    }
}
