package com.yishengyue.seller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
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
    private PwdInputLayout mInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_code);
        initView();
    }

    private void initView() {
        mActivityClose = (ImageView) findViewById(R.id.activity_close);
        mActivityClose.setOnClickListener(this);
        mInputLayout = (PwdInputLayout) findViewById(R.id.input_layout);
        mInputLayout.setInputCompleteListener(new PwdInputLayout.InputCompleteListener() {
            @Override
            public void inputComplete(String result) {
                CommApi.instance().getOrderDetail(Data.getUser().getUserId(),result).subscribe(new SimpleSubscriber<Order>(InputCodeActivity.this,true) {
                    @Override
                    protected void onError(ApiException ex) {
                         ToastUtils.showToast(InputCodeActivity.this, ex.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(Order value) {
                        new ActivateDialog(InputCodeActivity.this,value,InputCodeActivity.this).show();
                    }
                });

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_close:
                finish();
                break;
        }
    }

    @Override
    public void onResult(boolean Success) {
        mInputLayout.clear();
    }
}
