package cn.bry.yueke.base;


public class ApiResult<T> {

    /**
     * rspMessageBody : {}
     * rspMessageHead : {"returnCode":"BM0003","returnMessage":"请求消息格式不正确"}
     */

    public T rspMessageBody;
    public RspMessageHeadBean rspMessageHead;

    public T getRspMessageBody() {
        return rspMessageBody;
    }

    public void setRspMessageBody(T rspMessageBody) {
        this.rspMessageBody = rspMessageBody;
    }

    public RspMessageHeadBean getRspMessageHead() {
        return rspMessageHead;
    }

    public void setRspMessageHead(RspMessageHeadBean rspMessageHead) {
        this.rspMessageHead = rspMessageHead;
    }


    public static class RspMessageHeadBean {
        /**
         * returnCode : BM0003
         * returnMessage : 请求消息格式不正确
         */

        private String returnCode;
        private String returnMessage;
        private String rspTime;

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

        public String getRspTime() {
            return rspTime;
        }

        public void setRspTime(String rspTime) {
            this.rspTime = rspTime;
        }
    }


    public boolean isSuccess() {
        return "YUEK0000".equals(rspMessageHead.getReturnCode());
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "rspMessageHead=" + rspMessageHead +
                ", rspMessageBody=" + rspMessageBody +
                '}';
    }
}
