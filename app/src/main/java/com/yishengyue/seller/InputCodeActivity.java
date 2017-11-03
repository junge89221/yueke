package com.yishengyue.seller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yishengyue.seller.base.BaseActivity;
import com.yishengyue.seller.view.widget.PwdInputLayout;

public class InputCodeActivity extends BaseActivity implements View.OnClickListener {

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
            public void inputComplete(String string) {
                
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
}
