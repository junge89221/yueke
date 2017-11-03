package com.yishengyue.seller.view.widget;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.BounceEnter.BounceEnter;
import com.flyco.dialog.widget.base.BaseDialog;
import com.yishengyue.seller.R;


/**
 * Created by zhangli on 2017/3/31
 */

public class ActivateDialog extends BaseDialog<ActivateDialog> {


    private ViewHolder mHolder;

    public ActivateDialog(Context context) {
        super(context);
        showAnim(new BounceEnter());
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
        TextView mProductOrderBill;
        TextView mProductOrderExpressFee;
        TextView mDialogCancel;
        TextView mDialogCommit;

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
            this.mProductOrderBill = (TextView) view.findViewById(R.id.product_order_bill);
            this.mProductOrderExpressFee = (TextView) view.findViewById(R.id.product_order_express_fee);
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

                }
            });
        }
    }
}
