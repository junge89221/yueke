package cn.bry.yueke.base;

/**
 * 类描述：
 * 创建人： 张俊
 * 创建时间： 2018/10/17
 * 版权： 成都智慧一生约科技有限公司
 */

public class CommRequest2 {
    private ReqMessageHead2 reqMessageHead;
    private Object reqMessageBody;

    public ReqMessageHead2 getReqMessageHead() {
        return this.reqMessageHead;
    }

    public void setReqMessageHead(ReqMessageHead2 reqMessageHead) {
        this.reqMessageHead = reqMessageHead;
    }

    public Object getReqMessageBody() {
        return this.reqMessageBody;
    }

    public void setReqMessageBody(Object reqMessageBody) {
        this.reqMessageBody = reqMessageBody;
    }

    public CommRequest2(ReqMessageHead2 reqMessageHead, Object reqMessageBody) {
        this.reqMessageHead = reqMessageHead;
        this.reqMessageBody = reqMessageBody;
    }
}
