package com.example.postApi.enity;

import com.example.util.enityUtil.userEnity;

public class RstKeyVo extends userEnity {

    private String rstIds;
    private String rstKey;

    public String getRstIds() {
        return rstIds;
    }

    public void setRstIds(String rstIds) {
        this.rstIds = rstIds;
    }

    public String getRstKey() {
        return rstKey;
    }

    public void setRstKey(String rstKey) {
        this.rstKey = rstKey;
    }
}
