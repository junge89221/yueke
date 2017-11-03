package com.yishengyue.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.yishengyue.seller.api.CommApi;
import com.yishengyue.seller.api.exception.ApiException;
import com.yishengyue.seller.api.subscriber.SimpleSubscriber;
import com.yishengyue.seller.base.BaseActivity;
import com.yishengyue.seller.base.Data;
import com.yishengyue.seller.base.User;
import com.yishengyue.seller.util.AppManager;
import com.yishengyue.seller.util.RegexUtils;
import com.yishengyue.seller.util.ToastUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher {


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
        mLoginPhone.addTextChangedListener(this);
        mLoginPassword.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_close:
                finish();
                break;
            case R.id.login_commit:
                CommApi.instance().login(mLoginPhone.getText().toString().trim(),mLoginPassword.getText().toString().trim()).subscribe(new SimpleSubscriber<User>(this,true) {
                    @Override
                    protected void onError(ApiException ex) {
                        ToastUtils.showToast(LoginActivity.this, ex.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(User value) {
                        Data.setUser(value);
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        AppManager.getAppManager().finishNotSpecifiedActivity(MainActivity.class);
                    }
                });
                break;
            case R.id.login_forget_password:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.login_fast:
                startActivity(new Intent(this,FastLoginActivity.class));
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mLoginCommit.setEnabled(RegexUtils.checkPhone(mLoginPhone.getText().toString().trim()) && !TextUtils.isEmpty(mLoginPassword.getText().toString().trim()));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
