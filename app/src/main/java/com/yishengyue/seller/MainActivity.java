package com.yishengyue.seller;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;
import com.yishengyue.seller.util.PhotoPicker;
import com.yishengyue.seller.view.web.SonicJavaScriptInterface;
import com.yishengyue.seller.view.web.SonicRuntimeImpl;
import com.yishengyue.seller.view.web.SonicSessionClientImpl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private WebView webView;
    private ProgressBar progressbar;

    private SonicSession sonicSession;
    private ValueCallback webValueCallbackBefore5; // 5.0以下回调
    private ValueCallback<Uri[]> webValueCallbackLater5;// 5.0以上回调

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_main);

        progressbar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.web_view);
        initWebSettings(webView);
        loadIndexUrl(BuildConfig.WEB_INDEX);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoPicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoPicker.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
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
        webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent), "sonic");
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(url);
        }
    }

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

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
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

}