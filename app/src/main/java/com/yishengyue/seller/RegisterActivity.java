package com.yishengyue.seller;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
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
import com.yishengyue.seller.base.VerifyCodeBean;
import com.yishengyue.seller.util.RegexUtils;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

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
    /**
     * 已有账号，去登录
     */
    private TextView mLoginFast;
    private CountDown countDown;
    private VerifyCodeBean mVerifyCodeBean;
    private String okPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        countDown = new CountDown(60*1000,1000);

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
        mLoginFast = (TextView) findViewById(R.id.login_fast);
        mLoginFast.setOnClickListener(this);
        mLoginFast.setText(Html.fromHtml("已有账号，去<font color='#3B55E6'><u>登录</u></font>"));
        mLoginPhone.addTextChangedListener(this);
        mLoginCode.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_close:
                finish();
                break;
            case R.id.get_code:
                if(!RegexUtils.checkPhone(mLoginPhone.getText().toString().trim())){
                    Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                    return;
                }
                mGetCode.setEnabled(false);
                CommApi.instance().getVerifyCode(mLoginPhone.getText().toString().trim()).subscribe(new SimpleSubscriber<VerifyCodeBean>(this,true) {
                    @Override
                    protected void onError(ApiException ex) {
                        mGetCode.setEnabled(true);
                        Toast.makeText(RegisterActivity.this, ex.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(VerifyCodeBean value) {
                        mVerifyCodeBean = value;
                        okPhone = mLoginPhone.getText().toString().trim();
                        countDown.start();
                    }
                });
                break;
            case R.id.login_commit:
                if(!BuildConfig.DEBUG) {
                   /* if (mVerifyCodeBean == null) {
                        Toast.makeText(this, "请获取验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }*/
                if(!mLoginPhone.getText().toString().trim().equals(okPhone)){
                    Toast.makeText(this, "手机和验证码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                }
                Intent intent = new Intent(this, SetPasswordActivity.class);
                intent.putExtra("phone",mLoginPhone.getText().toString().trim());
                intent.putExtra("VerifyCode",mLoginCode.getText().toString().trim());
                startActivity(intent);
                break;
            case R.id.login_fast:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mLoginCommit.setEnabled(RegexUtils.checkPhone(mLoginPhone.getText().toString().trim())&& !TextUtils.isEmpty(mLoginCode.getText().toString().trim()));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    private class CountDown extends CountDownTimer {

        CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mGetCode.setText(String.format(Locale.getDefault(),"%dS",millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            mGetCode.setText("获取");
            mGetCode.setEnabled(true);
        }
    }
}
