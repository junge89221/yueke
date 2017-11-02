package com.yishengyue.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView mActivityClose;
    /**
     * 请输入你的手机号码
     */
    private EditText mLoginPhone;
    /**
     * 请输入登录密码
     */
    private EditText mLoginPassword;
    /**
     * 登录
     */
    private TextView mLoginCommit;
    /**
     * 忘记密码
     */
    private TextView mLoginForgetPassword;
    /**
     * 验证码快捷登录
     */
    private TextView mLoginFast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_login);
        initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private void initView() {
        mActivityClose = (ImageView) findViewById(R.id.activity_close);
        mActivityClose.setOnClickListener(this);
        mLoginPhone = (EditText) findViewById(R.id.login_phone);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLoginCommit = (TextView) findViewById(R.id.login_commit);
        mLoginCommit.setOnClickListener(this);
        mLoginForgetPassword = (TextView) findViewById(R.id.login_forget_password);
        mLoginForgetPassword.setOnClickListener(this);
        mLoginFast = (TextView) findViewById(R.id.login_fast);
        mLoginFast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_close:
                finish();
                break;
            case R.id.login_commit:

                break;
            case R.id.login_forget_password:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.login_fast:
                startActivity(new Intent(this,FastLoginActivity.class));
                break;
        }
    }
}
