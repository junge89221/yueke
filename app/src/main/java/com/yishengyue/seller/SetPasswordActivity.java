package com.yishengyue.seller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.yishengyue.seller.util.RegexUtils;

public class SetPasswordActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
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

    private String phone;
    private String VerifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_set_password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        initData();
    }

    private void initData() {
        phone = getIntent().getStringExtra("phone");
        VerifyCode = getIntent().getStringExtra("VerifyCode");
    }

    private void initView() {
        mActivityClose = (ImageView) findViewById(R.id.activity_close);
        mActivityClose.setOnClickListener(this);
        mLoginPhone = (EditText) findViewById(R.id.login_phone);
        mLoginCode = (EditText) findViewById(R.id.login_code);
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
                break;
            case R.id.login_commit:
                if(!mLoginPhone.getText().toString().trim().equals(mLoginCode.getText().toString().trim())){
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                CommApi.instance().register(phone,mLoginPhone.getText().toString().trim(),VerifyCode).subscribe(new SimpleSubscriber<String>(this,true) {
                    @Override
                    protected void onError(ApiException ex) {
                        Toast.makeText(SetPasswordActivity.this, ex.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String value) {
                        Toast.makeText(SetPasswordActivity.this, "完成", Toast.LENGTH_SHORT).show();
                    }
                });
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
           mLoginCommit.setEnabled(!TextUtils.isEmpty(mLoginPhone.getText().toString().trim())&&!TextUtils.isEmpty(mLoginCode.getText().toString().trim()));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
