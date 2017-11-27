package com.yishengyue.seller.view.widget;


import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flyco.animation.BounceEnter.BounceEnter;
import com.flyco.dialog.widget.base.BaseDialog;
import com.yishengyue.seller.R;
import com.yishengyue.seller.api.CommApi;
import com.yishengyue.seller.api.exception.ApiException;
import com.yishengyue.seller.api.subscriber.SimpleSubscriber;
import com.yishengyue.seller.base.Data;
import com.yishengyue.seller.base.Order;
import com.yishengyue.seller.util.MoneyUtils;
import com.yishengyue.seller.util.ToastUtils;


/**
 * Created by zhangli on 2017/3/31
 */

public class ActivateDialog extends BaseDialog<ActivateDialog> {
    private Order mOrder;
    private ViewHolder mHolder;
    private Context mContext;
    public ActivateDialog(Context context, Order order, ActivateResultListener activateResultListener) {
        super(context);
        showAnim(new BounceEnter());
        this.mContext = context;
        this.mOrder = order;
        mActivateResultListener = activateResultListener;
    }

    @Override
    public View onCreateView() {
        widthScale(0.83f);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_activate, null);
        mHolder = new ViewHolder(view);
        return view;
    }

    /**
     * set Ui data or logic opreation before attatched window(在对话框显示之前,设置界面数据或者逻辑)
     */
    @Override
    public void setUiBeforShow() {
        if (mOrder != null) {
            Glide.with(mContext).load(mOrder.getProductImage()).into(mHolder.mProductImg);
            mHolder.mProductName.setText(mOrder.getProductName());
            mHolder.mProductDec.setText(mOrder.getProductJingle());
            mHolder.mProductPrice.setText(Html.fromHtml("价格：<font color='#00002C'>" + String.format("¥%s", MoneyUtils.getMoney(mOrder.getProductPrice())) + "</font>"));
            mHolder.mProductNumber.setText(Html.fromHtml("数量：<font color='#AAB4BE'>" + String.format("X%s", mOrder.getProductNum()) + "</font>"));
            mHolder.mProductOrderNo.setText(Html.fromHtml("订单编号：<font color='#000000'>" + mOrder.getOrderSn() + "</font>"));
            mHolder.mProductOrderTime.setText(Html.fromHtml("订单日期：<font color='#000000'>" + mOrder.getAddTime() + "</font>"));
            mHolder.mProductOrderAddress.setText(Html.fromHtml("收货地址：<font color='#000000'>" + mOrder.getReceiveAddress() + "</font>"));
           /* mHolder.mProductOrderBill.setText(Html.fromHtml("发票类型：<font color='#000000'>" + "不开发票" + "</font>"));
            mHolder.mProductOrderExpressFee.setText(Html.fromHtml("配送费用：<font color='#000000'>" + "快递配送免费" + "</font>"));*/
           if("2".equals(mOrder.getBusinessTypeCode())){
               mHolder.coupon_layout.setVisibility(View.GONE);
               mHolder.mProductPrice.setVisibility(View.GONE);
           }
         }
    }

    class ViewHolder {
        View view;
        ImageView mProductImg;
        TextView mProductName;
        TextView mProductDec;
        TextView mProductPrice;
        TextView mProductNumber;
        TextView mProductOrderNo;
        TextView mProductOrderTime;
        TextView mProductOrderAddress;
     /*   TextView mProductOrderBill;
        TextView mProductOrderExpressFee;*/
        TextView mDialogCancel;
        TextView mDialogCommit;
        LinearLayout coupon_layout;
        ViewHolder(View view) {
            this.view = view;
            this.mProductImg = (ImageView) view.findViewById(R.id.product_img);
            this.mProductName = (TextView) view.findViewById(R.id.product_name);
            this.mProductDec = (TextView) view.findViewById(R.id.product_dec);
            this.mProductPrice = (TextView) view.findViewById(R.id.product_price);
            this.mProductNumber = (TextView) view.findViewById(R.id.product_number);
            this.mProductOrderNo = (TextView) view.findViewById(R.id.product_order_no);
            this.mProductOrderTime = (TextView) view.findViewById(R.id.product_order_time);
            this.mProductOrderAddress = (TextView) view.findViewById(R.id.product_order_address);
           /* this.mProductOrderBill = (TextView) view.findViewById(R.id.product_order_bill);
            this.mProductOrderExpressFee = (TextView) view.findViewById(R.id.product_order_express_fee);*/
           this.coupon_layout = view.findViewById(R.id.coupon_layout);
            this.mDialogCancel = (TextView) view.findViewById(R.id.dialog_cancel);
            this.mDialogCommit = (TextView) view.findViewById(R.id.dialog_commit);
            this.mDialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            this.mDialogCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(mOrder!=null){
                       CommApi.instance().activateOrder(Data.getUser().getUserId(),mOrder.getConsumeVerifyCode(),mOrder.getOrderDetailId()).subscribe(new SimpleSubscriber<String>(mContext,true) {
                           @Override
                           protected void onError(ApiException ex) {
                               if(mActivateResultListener!=null)mActivateResultListener.onResult(false);
                            ToastUtils.showToast(mContext, ex.getMsg(), Toast.LENGTH_SHORT).show();
                           }
                           @Override
                           public void onNext(String value) {
                               if(mActivateResultListener!=null)mActivateResultListener.onResult(true);
                               ToastUtils.showToast(mContext, "激活成功", Toast.LENGTH_SHORT).show();
                               dismiss();
                               ((Activity)mContext).finish();
                           }
                       });
                   }




                }
            });
        }
    }

    public void setActivateResultListener(ActivateResultListener activateResultListener) {
        mActivateResultListener = activateResultListener;
    }

    private ActivateResultListener mActivateResultListener;
    public interface  ActivateResultListener{
        void onResult(boolean Success);
    }
}
