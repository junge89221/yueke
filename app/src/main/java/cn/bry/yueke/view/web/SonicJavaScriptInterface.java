package cn.bry.yueke.view.web;/*
 * Tencent is pleased to support the open source community by making VasSonic available.
 *
 * Copyright (C) 2017 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *
 */

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.sonic.sdk.SonicDiffDataCallback;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import cn.bry.yueke.LoginActivity;
import cn.bry.yueke.api.exception.ApiException;
import cn.bry.yueke.api.subscriber.SimpleSubscriber;
import cn.bry.yueke.base.Data;
import cn.bry.yueke.util.AppManager;
import cn.bry.yueke.util.DataCleanManager;
import cn.bry.yueke.util.Utils;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Sonic javaScript Interface (Android API Level >= 17)
 */

public class SonicJavaScriptInterface {

    private final SonicSessionClientImpl sessionClient;

    private final Intent intent;

    public static final String PARAM_CLICK_TIME = "clickTime";

    public static final String PARAM_LOAD_URL_TIME = "loadUrlTime";

    public SonicJavaScriptInterface(SonicSessionClientImpl sessionClient, Intent intent) {
        this.sessionClient = sessionClient;
        this.intent = intent;
    }

    @JavascriptInterface
    public void getDiffData() {
        // the callback function of demo page is hardcode as 'getDiffDataCallback'
        getDiffData2("getDiffDataCallback");
    }

    @JavascriptInterface
    public void getDiffData2(final String jsCallbackFunc) {
        if (null != sessionClient) {
            sessionClient.getDiffData(new SonicDiffDataCallback() {
                @Override
                public void callback(final String resultData) {
                    Runnable callbackRunnable = new Runnable() {
                        @Override
                        public void run() {
                            String jsCode = "javascript:" + jsCallbackFunc + "('" + toJsString(resultData) + "')";
                            sessionClient.getWebView().loadUrl(jsCode);
                        }
                    };
                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        callbackRunnable.run();
                    } else {
                        new Handler(Looper.getMainLooper()).post(callbackRunnable);
                    }
                }
            });
        }
    }

    @JavascriptInterface
    public String getPerformance() {
        long clickTime = intent.getLongExtra(PARAM_CLICK_TIME, -1);
        long loadUrlTime = intent.getLongExtra(PARAM_LOAD_URL_TIME, -1);
        try {
            JSONObject result = new JSONObject();
            result.put(PARAM_CLICK_TIME, clickTime);
            result.put(PARAM_LOAD_URL_TIME, loadUrlTime);
            return result.toString();
        } catch (Exception e) {

        }

        return "";
    }

    /*
    * * From RFC 4627, "All Unicode characters may be placed within the quotation marks except
    * for the characters that must be escaped: quotation mark,
    * reverse solidus, and the control characters (U+0000 through U+001F)."
    */
    private static String toJsString(String value) {
        if (value == null) {
            return "null";
        }
        StringBuilder out = new StringBuilder(1024);
        for (int i = 0, length = value.length(); i < length; i++) {
            char c = value.charAt(i);


            switch (c) {
                case '"':
                case '\\':
                case '/':
                    out.append('\\').append(c);
                    break;

                case '\t':
                    out.append("\\t");
                    break;

                case '\b':
                    out.append("\\b");
                    break;

                case '\n':
                    out.append("\\n");
                    break;

                case '\r':
                    out.append("\\r");
                    break;

                case '\f':
                    out.append("\\f");
                    break;

                default:
                    if (c <= 0x1F) {
                        out.append(String.format("\\u%04x", (int) c));
                    } else {
                        out.append(c);
                    }
                    break;
            }

        }
        return out.toString();
    }

    @JavascriptInterface
    public String getUser() {
        if (Data.getUser() != null) {
            return new Gson().toJson(Data.getUser());
        }
        return null;
    }



    @JavascriptInterface
    public void clearCache() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {

            @Override
            public void subscribe(ObservableEmitter<Boolean> subscriber) throws Exception {
                try {
                    DataCleanManager.cleanApplicationData(Utils.getContext());
                    subscriber.onNext(true);
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<Boolean>() {
                    @Override
                    protected void onError(ApiException ex) {
                        Toast.makeText(Utils.getContext(), "无法清除缓存", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Boolean s) {
                        if (s) {
                            Toast.makeText(Utils.getContext(), "清除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Utils.getContext(), "无法清除缓存", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @JavascriptInterface
    public String getCachesize() {
        JSONObject result = new JSONObject();
        try {
            result.put("cacheSize", DataCleanManager.getCacheSize(Utils.getContext()));
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @JavascriptInterface
    public void toLogin() {
        Utils.getContext().startActivity(new Intent(Utils.getContext(), LoginActivity.class));
        AppManager.getAppManager().finishNotSpecifiedActivity(LoginActivity.class);
    }

}