package com.yishengyue.seller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 请输入你的手机号码
     */
    private EditText mLoginPhone;
    /**
     * 获取验证码
     */
    private TextView mLoginCommit;
    /**
     * 1/3
     */
    private ImageView activity_close;
    /**
     * 手机号
     */
    private TextView mHintText;
    private TextView mForgetPasswordPage;
    private int PageNo = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_forget_password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        initData();
    }

    private void initData() {
        showPage(PageNo);
    }

    private void initView() {
        mLoginPhone = (EditText) findViewById(R.id.login_phone);
        mLoginCommit = (TextView) findViewById(R.id.login_commit);
        activity_close = (ImageView) findViewById(R.id.activity_close);
        mLoginCommit.setOnClickListener(this);
        activity_close.setOnClickListener(this);
        mHintText = (TextView) findViewById(R.id.hint_text);
        mForgetPasswordPage = (TextView) findViewById(R.id.forget_password_page);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_commit:
                PageNo++;
                showPage(PageNo);
                break;
            case R.id.activity_close:
                finish();
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    private void showPage(int PageNo) {
        mForgetPasswordPage.setText(PageNo+"/3");
        switch (PageNo){
            case 1:
                mHintText.setText("手机号");
                mLoginPhone.setHint("请输入你的手机号码");
                mLoginCommit.setText("获取验证码");
                break;
            case 2:
                mHintText.setText("验证码");
                mLoginPhone.setHint("请输入短信验证码");
                mLoginCommit.setText("下一步");
                break;
            case 3:
                mHintText.setText("设置密码");
                mLoginPhone.setHint("请设置新的登录密码");
                mLoginCommit.setText("完成");
                break;
        }
    }
}
