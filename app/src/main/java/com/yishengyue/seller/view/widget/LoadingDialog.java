package com.yishengyue.seller.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yishengyue.seller.R;


/**
 * Created on 2017/3/23
 * Author：qzj
 */
public class LoadingDialog extends Dialog {

    Context context;
    View view;
    TextView loadingTV;

    String loadingText = "";

    public LoadingDialog(Context context) {
        super(context, R.style.TransDialog);
        this.context = context;
        view = View.inflate(context,R.layout.layout_pop_loading,null);
        loadingTV = (TextView) view.findViewById(R.id.pop_loading_txt);

        setCanceledOnTouchOutside(false);
        addContentView(view,new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public LoadingDialog setLoadingText(String text) {
        this.loadingText = text;
        return this;
    }

    private void setUIBeforeShow(){
        if (loadingText==null||"".equals(loadingText)){
            loadingTV.setVisibility(View.GONE);
        }else {
            loadingTV.setText(loadingText);
            loadingTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        setUIBeforeShow();
        super.show();
    }
}
