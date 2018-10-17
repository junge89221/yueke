package cn.bry.yueke;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
import cn.bry.yueke.base.loginResp;
import cn.bry.yueke.util.AppManager;
import cn.bry.yueke.util.ToastUtils;

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


    TextView hintPhone;
    FrameLayout rootFrame;
    RelativeLayout dialogRelative;

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

        mLoginPhone.addTextChangedListener(this);
        mLoginPassword.addTextChangedListener(this);
        hintPhone = findViewById(R.id.textView4);
        rootFrame = findViewById(R.id.root);
        dialogRelative = findViewById(R.id.dialog_relative);
        moveDialog(rootFrame, dialogRelative);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_close:
                finish();
                break;
            case R.id.login_commit:
                CommApi.instance().login(mLoginPhone.getText().toString().trim(), mLoginPassword.getText().toString().trim()).subscribe(new SimpleSubscriber<loginResp>(this, true) {
                    @Override
                    protected void onError(ApiException ex) {
                        ToastUtils.showToast(LoginActivity.this, ex.getMsg(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(loginResp value) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("htmlString",BuildConfig.WEB_INDEX+value.getUserId());
                        startActivity(intent);
                        AppManager.getAppManager().finishNotSpecifiedActivity(MainActivity.class);
                    }
                });
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mLoginCommit.setEnabled(!TextUtils.isEmpty(mLoginPhone.getText().toString().trim()) && !TextUtils.isEmpty(mLoginPassword.getText().toString().trim()));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
