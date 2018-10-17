package cn.bry.yueke;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.flyco.animation.BounceEnter.BounceEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;


import cn.bry.yueke.base.BaseActivity;
import cn.bry.yueke.util.AppManager;
import cn.bry.yueke.util.PhotoPicker;
import cn.bry.yueke.util.ToastUtils;
import cn.bry.yueke.view.web.SonicJavaScriptInterface;
import cn.bry.yueke.view.web.SonicRuntimeImpl;
import cn.bry.yueke.view.web.SonicSessionClientImpl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressbar;

    private SonicSession sonicSession;
    private ValueCallback webValueCallbackBefore5; // 5.0以下回调
    private ValueCallback<Uri[]> webValueCallbackLater5;// 5.0以上回调
    private boolean needBackToHomePage = false; // 是否需要跳转到首页
    private boolean needClearBackClickedTimes = false; // 是否需要清除返回按键点击次数
    private int backClickedTimes = 0;
    private String htmlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_main);
        htmlString = getIntent().getStringExtra("htmlString");
        progressbar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.web_view);
        initWebSettings(webView);
        loadIndexUrl(htmlString);
        initExitDialog();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoPicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoPicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            backClickedTimes++;
            if (!needClearBackClickedTimes) {
                needClearBackClickedTimes = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        needClearBackClickedTimes = false;
                        backClickedTimes = 0;
                    }
                }, 6000);
            }
            if (backClickedTimes >= 4) {
                needBackToHomePage = true;
                loadIndexUrl(htmlString);
            } else {
                webView.goBack();
            }
        } else {
            if (!isQuit) {
                isQuit = true;
                ToastUtils.showToast(this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
                TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
                ToastUtils.cancelToast();
                AppManager.getAppManager().AppExit(MainActivity.this);
            }

        }
    }

    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        PhotoPicker.clear();
        super.onDestroy();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + "; yishengyue");
    }

    private void loadIndexUrl(String url) {
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }
        SonicSessionClientImpl sonicSessionClient = null;
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setConnectionIntercepter(new SonicSessionConnectionInterceptor() {
            @Override
            public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                return new OfflinePkgSessionConnection(MainActivity.this, session, intent);
            }
        });
        sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
        }
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        Intent intent = new Intent();
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent), "JsToNative");
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(url);
        }
    }

    boolean isItemClicked = false;
    private void showPhotoSelectDialog() {
        final String[] stringItems = {"拍照", "相册"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.title("选择图片")
                .itemTextSize(20)
                .itemTextColor(0xFF3C3C3C)
                .titleTextSize_SP(13)
                .titleTextColor(0xFF8F8E94)
                .cornerRadius(12)
                .show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(!isItemClicked)onPhotoSelectedCallback(null);
                isItemClicked = false;

            }
        });
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                isItemClicked = true;
                if (position == 0) {
                    PhotoPicker.takePhoto(MainActivity.this, new PhotoPicker.PhotoPickerCallBack() {
                        @Override
                        public void callBack(String photoPath) {
                            onPhotoSelectedCallback(photoPath);
                        }

                        @Override
                        public void onCancel() {
                            onPhotoSelectedCallback(null);
                        }
                    });
                } else if (position == 1) {
                    PhotoPicker.pickPhoto(MainActivity.this, new PhotoPicker.PhotoPickerCallBack() {
                        @Override
                        public void callBack(String photoPath) {
                            onPhotoSelectedCallback(photoPath);
                        }

                        @Override
                        public void onCancel() {
                            onPhotoSelectedCallback(null);
                        }
                    });
                }
                dialog.dismiss();
            }
        });
    }

    private void onPhotoSelectedCallback(String photoPath) {
        Uri[] uris;
        if (TextUtils.isEmpty(photoPath)) {
            uris = new Uri[]{Uri.parse("")};
        } else {
            uris = new Uri[]{Uri.fromFile(new File(photoPath))};
        }
        if (webValueCallbackBefore5 != null) {
            webValueCallbackBefore5.onReceiveValue(uris[0]);
        }
        if (webValueCallbackLater5 != null) {
            webValueCallbackLater5.onReceiveValue(uris);
        }
        webValueCallbackBefore5 = null;
        webValueCallbackLater5 = null;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressbar.setVisibility(View.GONE);
            if (sonicSession != null) {
                sonicSession.getSessionClient().pageFinish(url);
            }
        }

        @TargetApi(21)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return shouldInterceptRequest(view, request.getUrl().toString());
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (sonicSession != null) {
                return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
            }
            return null;
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            if (needBackToHomePage) {
                needBackToHomePage = false;
                webView.clearHistory();//清除历史记录
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("tel")) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(View.GONE);
            } else {
                if (progressbar.getVisibility() == View.GONE)
                    progressbar.setVisibility(View.VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            webValueCallbackLater5 = filePathCallback;
            showPhotoSelectDialog();
            return true;
        }

        public void openFileChooser(ValueCallback uploadMsg) {
            webValueCallbackBefore5 = uploadMsg;
            showPhotoSelectDialog();
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            webValueCallbackBefore5 = uploadMsg;
            showPhotoSelectDialog();
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType, String capture) {
            webValueCallbackBefore5 = uploadMsg;
            showPhotoSelectDialog();
        }
    }

    private class OfflinePkgSessionConnection extends SonicSessionConnection {

        private final Context context;

        OfflinePkgSessionConnection(Context context, SonicSession session, Intent intent) {
            super(session, intent);
            this.context = context;
        }

        @Override
        protected int internalConnect() {
            if (null != context) {
                try {
                    InputStream offlineHtmlInputStream = context.getAssets().open("sonic-demo-index.html");
                    responseStream = new BufferedInputStream(offlineHtmlInputStream);
                    return SonicConstants.ERROR_CODE_SUCCESS;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN;
        }

        @Override
        protected BufferedInputStream internalGetResponseStream() {
            return responseStream;
        }

        @Override
        public void disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getResponseCode() {
            return 200;
        }

        @Override
        public Map<String, List<String>> getResponseHeaderFields() {
            return new HashMap<>(0);
        }

        @Override
        public String getResponseHeaderField(String key) {
            return "";
        }
    }

    private Boolean isQuit = false;
    private Timer timer = new Timer();
    private NormalDialog mNormalDialog;

    private void initExitDialog() {
        mNormalDialog = new NormalDialog(this);
        mNormalDialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#FFFFFF"))//
                .cornerRadius(10)//
                .content("确定退出？")
                .contentGravity(Gravity.CENTER)//
                .contentTextColor(Color.parseColor("#000000"))//
                .dividerColor(Color.parseColor("#D8D8D8"))//
                .btnTextSize(15.5f, 15.5f)//
                .btnTextColor(Color.parseColor("#3C6DF8"), Color.parseColor("#3C6DF8"))//
                .btnPressColor(Color.parseColor("#903C6DF8"))//
                .widthScale(0.85f)//
                .showAnim(new BounceEnter())
                .dismissAnim(null).setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                mNormalDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                // 退出程序并杀进程
                mNormalDialog.dismiss();
                AppManager.getAppManager().AppExit(MainActivity.this);
            }
        });

    }

}