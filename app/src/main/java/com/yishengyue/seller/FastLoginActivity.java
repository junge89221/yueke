package com.yishengyue.seller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

public class FastLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mActivityClose;
    /**
     * 请输入你的手机号码
     */
    private EditText mLoginPhone;
    /**
     * 请输入验证码
     */
    private EditText mLoginCode;
    /**
     * 获取
     */
    private TextView mGetCode;
    /**
     * 下一步
     */
    private TextView mLoginCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_fast_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
    }

    private void initView() {
        mActivityClose = (ImageView) findViewById(R.id.activity_close);
        mActivityClose.setOnClickListener(this);
        mLoginPhone = (EditText) findViewById(R.id.login_phone);
        mLoginCode = (EditText) findViewById(R.id.login_code);
        mGetCode = (TextView) findViewById(R.id.get_code);
        mGetCode.setOnClickListener(this);
        mLoginCommit = (TextView) findViewById(R.id.login_commit);
        mLoginCommit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_close:
                finish();
                break;
            case R.id.get_code:
                break;
            case R.id.login_commit:

                break;
            case R.id.login_fast:
                break;
        }
    }
}
