package com.yyhz.rong.models.response;

import com.yyhz.rong.models.Result;
import com.yyhz.rong.util.GsonUtil;

public class ChatroomDemotionMsgResult extends Result{

    private String[] objectNames;

    public ChatroomDemotionMsgResult(Integer code, String msg, String[] objectNames) {
        super(code, msg);
        this.objectNames = objectNames;
    }

    public ChatroomDemotionMsgResult(String[] objectNames) {
        this.objectNames = objectNames;
    }

    public String[] getObjectNames() {
        return this.objectNames;
    }

    public void setObjectNames(String[] objectNames) {
        this.objectNames = objectNames;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this, ChatroomDemotionMsgResult.class);
    }
}
