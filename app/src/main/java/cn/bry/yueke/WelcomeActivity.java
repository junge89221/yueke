package cn.bry.yueke;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;



import cn.bry.yueke.base.BaseActivity;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 登录
     */
    private TextView mTextView8;
    /**
     * 注册
     */
    private TextView mTextView7;
    private static final String CAMERAPERMISSION = Manifest.permission.CAMERA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        initView();
    }

    private void initView() {
        mTextView8 = (TextView) findViewById(R.id.textView8);
        mTextView8.setOnClickListener(this);
        mTextView7 = (TextView) findViewById(R.id.textView7);
        mTextView7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView8: //登录
                startActivity(new Intent(this, LoginActivity.class));
//                startActivity(new Intent(this, InputCodeActivity.class));
//                new ActivateDialog(this).show();
//                startActivity(new Intent(this,MainActivity.class));
                 break;
            case R.id.textView7://注册
                 startActivity(new Intent(this, RegisterActivity.class));
//                AndPermission.with(this)
//                        .requestCode(100)
//                        .permission(Permission.CAMERA)
//                        .callback(new PermissionListener() {
//                            @Override
//                            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
//                                Log.e("==========", "onSucceed===" + requestCode + "===grantPermissions===" + grantPermissions);
//                                startActivity(new Intent(WelcomeActivity.this, ScanActivity.class));
//                            }
//
//                            @Override
//                            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
//                                Log.e("==========", "onFailed===" + requestCode + "===deniedPermissions===" + deniedPermissions);
//                            }
//                        })
//                        .start();


                break;
        }
    }


}
