package com.yishengyue.seller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.yishengyue.seller.api.CommApi;
import com.yishengyue.seller.api.exception.ApiException;
import com.yishengyue.seller.api.subscriber.SimpleSubscriber;
import com.yishengyue.seller.base.BaseActivity;
import com.yishengyue.seller.base.Data;
import com.yishengyue.seller.base.Order;
import com.yishengyue.seller.util.ToastUtils;
import com.yishengyue.seller.view.widget.ActivateDialog;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate, View.OnClickListener, ActivateDialog.ActivateResultListener {

    private ZBarView mQRCodeView;
    /**
     * 手动输入验证码
     */
    private TextView mInputCode;
    /**
     * 手电筒
     */
    private TextView mOpenFlash;
    private ImageView mActivityClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_scan);
        initView();
    }

    private void initView() {
        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);
        mInputCode = (TextView) findViewById(R.id.input_code);
        mInputCode.setOnClickListener(this);
        mOpenFlash = (TextView) findViewById(R.id.open_flash);
        mOpenFlash.setOnClickListener(this);
        mActivityClose = (ImageView) findViewById(R.id.activity_close);
        mActivityClose.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.closeFlashlight();
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
         vibrate();
        String consumeVerifyCode = Uri.parse(result).getQueryParameter("consumeVerifyCode");
        if (TextUtils.isEmpty(consumeVerifyCode)||consumeVerifyCode.length() != 12) {
            ToastUtils.showToast(this, "错误的二维码", Toast.LENGTH_SHORT).show();
            mQRCodeView.startSpot();
            return;
        }
        CommApi.instance().getOrderDetail(Data.getUser().getUserId(), result).subscribe(new SimpleSubscriber<Order>(this, true) {
            @Override
            protected void onError(ApiException ex) {
                mQRCodeView.startSpot();
                ToastUtils.showToast(ScanActivity.this, ex.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Order value) {
                new ActivateDialog(ScanActivity.this, value, ScanActivity.this).show();
            }
        });

    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    /**
     * 处理打开相机出错
     */
    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("=======", "打开相机出错");
    }

    private boolean flashIsOpen = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_code:
                startActivity(new Intent(this,InputCodeActivity.class));
                break;
            case R.id.open_flash:
                if (flashIsOpen) {
                    mQRCodeView.closeFlashlight();
                } else {
                    mQRCodeView.openFlashlight();
                }
                flashIsOpen = !flashIsOpen;
                break;
            case R.id.activity_close:
                finish();
                break;
        }
    }

    @Override
    public void onResult(boolean Success) {
        mQRCodeView.startSpot();
    }
}
