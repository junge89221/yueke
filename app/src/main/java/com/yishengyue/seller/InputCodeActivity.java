package com.yishengyue.seller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yishengyue.seller.api.CommApi;
import com.yishengyue.seller.api.exception.ApiException;
import com.yishengyue.seller.api.subscriber.SimpleSubscriber;
import com.yishengyue.seller.base.BaseActivity;
import com.yishengyue.seller.base.Data;
import com.yishengyue.seller.base.Order;
import com.yishengyue.seller.util.ToastUtils;
import com.yishengyue.seller.view.widget.ActivateDialog;
import com.yishengyue.seller.view.widget.PwdInputLayout;

public class InputCodeActivity extends BaseActivity implements View.OnClickListener, ActivateDialog.ActivateResultListener {

    private ImageView mActivityClose;
    private EditText code_edit;
    private TextView commit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_code);
        initView();
    }

    private void initView() {
        mActivityClose = (ImageView) findViewById(R.id.activity_close);
        mActivityClose.setOnClickListener(this);
        code_edit = (EditText) findViewById(R.id.code_edit);
        commit = findViewById(R.id.commit);
        commit.setOnClickListener(this);
        code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable)){
                    commit.setEnabled(false);
                }else {
                    commit.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_close:
                finish();
                break;
            case R.id.commit:
                CommApi.instance().getOrderDetail(Data.getUser().getUserId(),code_edit.getText().toString()).subscribe(new SimpleSubscriber<Order>(InputCodeActivity.this,true) {
                    @Override
                    protected void onError(ApiException ex) {
                        ToastUtils.showToast(InputCodeActivity.this, ex.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(Order value) {
                        setResult(RESULT_OK);
                        new ActivateDialog(InputCodeActivity.this,value, InputCodeActivity.this).show();

                    }
                });
                break;
        }
    }

    @Override
    public void onResult(boolean Success) {
        code_edit.setText("");
    }
}
