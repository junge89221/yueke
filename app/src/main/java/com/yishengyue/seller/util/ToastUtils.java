package com.yishengyue.seller.util;

import android.content.Context;
import android.widget.Toast;

/**
 * <pre>
 * author：张俊
 * date： 2017/11/3
 * desc：
 * <pre>
 */

public class ToastUtils {

    private static Toast sToast;
    public static Toast showToast(Context context,CharSequence text, int duration) {
         if (sToast == null) {
            sToast = Toast.makeText(context, text, duration);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
        }
       return  sToast;
    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
