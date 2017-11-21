package com.yishengyue.seller.base;

/**
 * <pre>
 * author：张俊
 * date： 2017/11/3
 * desc：
 * <pre>
 */

public class Order {

    /**
     * addTime : 2017-11-03
     * productId : 1
     * orderId : ab5f3ab4b237435daa4b2dab1edb8065
     * orderSn : 41711033526410001369
     * receiveAddress : 四川省 成都市 青白江区 蟠龙镇龙虾乡飞龙村9组788号
     * receiverName : 曹世杰
     * productNum : 2
     * orderDetailId : 111ef9018f6442aa8edaef9a2c224aaf
     * productName : 小孩游泳劵1次
     * consumeVerifyCode : 110310007443
     * receiverPhone : 13899999999
     * productImage : http://omea6ya8l.bkt.clouddn.com/cc
     * productJingle : stest
     * productPrice : 15
     */

    private String addTime;
    private String productId;
    private String orderId;
    private String orderSn;
    private String receiveAddress;
    private String receiverName;
    private int productNum;
    private String orderDetailId;
    private String productName;
    private String consumeVerifyCode;
    private String receiverPhone;
    private String productImage;
    private String productJingle;
    private double productPrice;

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getConsumeVerifyCode() {
        return consumeVerifyCode;
    }

    public void setConsumeVerifyCode(String consumeVerifyCode) {
        this.consumeVerifyCode = consumeVerifyCode;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductJingle() {
        return productJingle;
    }

    public void setProductJingle(String productJingle) {
        this.productJingle = productJingle;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
