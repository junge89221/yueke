package cn.bry.yueke;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.gyf.barlibrary.ImmersionBar;


import cn.bry.yueke.api.CommApi;
import cn.bry.yueke.api.exception.ApiException;
import cn.bry.yueke.api.subscriber.SimpleSubscriber;
import cn.bry.yueke.base.BaseActivity;
import cn.bry.yueke.base.Data;
import cn.bry.yueke.base.User;
import cn.bry.yueke.base.VerifyCodeBean;
import cn.bry.yueke.base.loginResp;
import cn.bry.yueke.util.AppManager;
import cn.bry.yueke.util.Constant;
import cn.bry.yueke.util.RegexUtils;
import cn.bry.yueke.util.ToastUtils;
import cn.bry.yueke.util.Utils;
import cn.bry.yueke.view.widget.SmoothCheckBox;


import java.util.Locale;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, TextWatcher, SmoothCheckBox.OnCheckedChangeListener {

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
     * 下一步
     */
    private TextView mLoginCommit;
    /**
     * 已有账号，去登录
     */
    private TextView mLoginFast;
    private VerifyCodeBean mVerifyCodeBean;
    private String okPhone;
    /**
     * 手机号
     */
    private TextView mTextView4;
    /**
     * 验证码
     */
    private TextView mTextView5;
    FrameLayout rootFrame;
    RelativeLayout dialogRelative;
    private SmoothCheckBox mSmoothBox;

    private boolean isVIP = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();

    }

    private void initView() {
        mActivityClose = (ImageView) findViewById(R.id.activity_close);
        mActivityClose.setOnClickListener(this);
        mLoginPhone = (EditText) findViewById(R.id.login_phone);
        mLoginCode = (EditText) findViewById(R.id.login_code);
        mSmoothBox = (SmoothCheckBox) findViewById(R.id.smoothBox);

        mLoginCommit = (TextView) findViewById(R.id.login_commit);
        mLoginCommit.setOnClickListener(this);
        mLoginFast = (TextView) findViewById(R.id.login_fast);
        mLoginFast.setOnClickListener(this);
        mLoginFast.setText(Html.fromHtml("已有账号，去<font color='#3B55E6'><u>登录</u></font>"));
        mLoginPhone.addTextChangedListener(this);
        mLoginCode.addTextChangedListener(this);
        mTextView4 = (TextView) findViewById(R.id.textView4);
        mTextView5 = (TextView) findViewById(R.id.textView5);
        rootFrame = findViewById(R.id.root);
        dialogRelative = findViewById(R.id.dialog_relative);
        moveDialog(rootFrame, dialogRelative);
        mSmoothBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_close:
                finish();
                break;

            case R.id.login_commit:

                CommApi.instance().register(mLoginPhone.getText().toString().trim(), mLoginCode.getText().toString().trim()).subscribe(new SimpleSubscriber<loginResp>(this, true) {
                    @Override
                    protected void onError(ApiException ex) {
                        if(TextUtils.isEmpty(ex.getMsg()))return;
                        ToastUtils.showToast(RegisterActivity.this, ex.getMsg(), Toast.LENGTH_SHORT).show();
                     }
                    @Override
                    public void onNext(loginResp value) {
                        ToastUtils.showToast(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                });


                break;
            case R.id.login_fast:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mLoginCommit.setEnabled(!TextUtils.isEmpty(mLoginPhone.getText().toString().trim()) && !TextUtils.isEmpty(mLoginCode.getText().toString().trim()));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
        isVIP = isChecked;
    }
}
