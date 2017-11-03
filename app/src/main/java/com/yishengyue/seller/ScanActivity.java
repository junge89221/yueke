package com.yishengyue.seller;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.yishengyue.seller.base.BaseActivity;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate, View.OnClickListener {

    private ZBarView mQRCodeView;
    /**
     * 手动输入验证码
     */
    private TextView mInputCode;
    /**
     * 手电筒
     */
    private TextView mOpenFlash;

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
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.startSpot();
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

    private boolean flashIsOpen =false;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_code:
                break;
            case R.id.open_flash:
                 if(flashIsOpen){
                     mQRCodeView.closeFlashlight();
                 }else {
                     mQRCodeView.openFlashlight();
                 }
                flashIsOpen = !flashIsOpen;
                break;
        }
    }
}
