package cn.bry.yueke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import cn.bry.yueke.util.Utils;
import cn.bry.yueke.view.widget.SplashView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initSplash();
    }

    private void initSplash() {
        SplashView.showSplashView(this, 3, R.mipmap.welcome_center_default_image, new SplashView.OnSplashViewActionListener() {

            @Override
            public void onSplashImageClick(String actionUrl, String actionType) {

            }

            @Override
            public void onSplashViewDismiss(boolean initiativeDismiss) {

                startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                finish();
            }

            @Override
            public void onsplashViewEnd() {

            }
        });

    }

}
