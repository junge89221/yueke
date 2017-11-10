package com.yishengyue.seller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.yishengyue.seller.base.VerifyCodeBean;
import com.yishengyue.seller.util.RegexUtils;
import com.yishengyue.seller.util.ToastUtils;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

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
    private int mPageNo = 1;
    private VerifyCodeBean mVerifyCodeBean;
    private String okPhone;

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
        showPage(1);
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
                if(mPageNo==1){
                    if(TextUtils.isEmpty(mLoginPhone.getText())){
                        mHintText.setText( "请输入登录手机号");
                        return;
                    }
                    if (!RegexUtils.checkPhone(mLoginPhone.getText().toString().trim())) {
                        mHintText.setText("手机号码格式不正确");
                        mHintText.setTextColor(Color.parseColor("#F34268"));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mHintText.setText("手机号");
                                mHintText.setTextColor(Color.parseColor("#000000"));
                            }
                        }, 2000);
                        return;
                    }
                    CommApi.instance().getVerifyCode(mLoginPhone.getText().toString().trim()).subscribe(new SimpleSubscriber<VerifyCodeBean>(this, true) {
                        @Override
                        protected void onError(ApiException ex) {
                             ToastUtils.showToast(ForgetPasswordActivity.this, ex.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(VerifyCodeBean value) {
                            if(TextUtils.equals(value.getIsReg(),"N")){
                                mHintText.setText("该手机号还没有注册");
                                mHintText.setTextColor(Color.parseColor("#F34268"));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mHintText.setText("手机号");
                                        mHintText.setTextColor(Color.parseColor("#000000"));
                                    }
                                }, 2000);
                            }else {
                                mVerifyCodeBean = value;
                                okPhone = mLoginPhone.getText().toString().trim();
                                mLoginPhone.setText("");
                                showPage(2);
                            }
                         }
                    });
                }else if(mPageNo==2){
                    if (TextUtils.isEmpty(mLoginPhone.getText().toString().trim())) {
                        mHintText.setText("请输入验证码");
                        mHintText.setTextColor(Color.parseColor("#F34268"));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mHintText.setText("验证码");
                                mHintText.setTextColor(Color.parseColor("#000000"));
                            }
                        }, 2000);
                        return;
                    }
                    if (!mLoginPhone.getText().toString().trim().equals(mVerifyCodeBean.getVerifyCode())) {
                        mHintText.setText("验证码错误");
                        mHintText.setTextColor(Color.parseColor("#F34268"));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mHintText.setText("验证码");
                                mHintText.setTextColor(Color.parseColor("#000000"));
                            }
                        }, 2000);
                        return;
                    }
                    mLoginPhone.setText("");

                    showPage(3);
                }else if(mPageNo==3){
                    if (TextUtils.isEmpty(mLoginPhone.getText().toString().trim())) {
                        mHintText.setText("请输入新密码");
                        mHintText.setTextColor(Color.parseColor("#F34268"));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mHintText.setText("设置密码");
                                mHintText.setTextColor(Color.parseColor("#000000"));
                            }
                        }, 2000);
                        return;
                    }
                   CommApi.instance().forgetPassword(okPhone,mVerifyCodeBean.getVerifyCode(),mLoginPhone.getText().toString().trim()).subscribe(new SimpleSubscriber<String>(this,true) {
                       @Override
                       protected void onError(ApiException ex) {
                           ToastUtils.showToast(ForgetPasswordActivity.this,ex.getMsg(),Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onNext(String value) {
                           ToastUtils.showToast(ForgetPasswordActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                           finish();
                           startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
                       }
                   });
                }
                break;
            case R.id.activity_close:
                finish();
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    private void showPage(int PageNo ) {
        mPageNo = PageNo;
        mForgetPasswordPage.setText(PageNo+"/3");
        switch (PageNo){
            case 1:
                mHintText.setText("手机号");
                mLoginPhone.setHint("请输入你的手机号码");
                mLoginCommit.setText("获取验证码");
                mLoginPhone.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case 2:
                mHintText.setText("验证码");
                mLoginPhone.setHint("请输入短信验证码");
                mLoginCommit.setText("下一步");
                mLoginPhone.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case 3:
                mHintText.setText("设置密码");
                mLoginPhone.setHint("请设置新的登录密码");
                mLoginCommit.setText("完成");
                mLoginPhone.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
        }
    }


}
