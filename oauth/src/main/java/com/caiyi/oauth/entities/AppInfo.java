package com.caiyi.oauth.entities;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppInfo {
    /**
     * App id
     */
    private String appId;
    /**
     * API 秘钥
     */
    private String key;

    /**
     * app有效标志
     */
    private String yxbz;

    public String toJSONString() {
        JSONObject res = new JSONObject();
        res.put("appId", this.appId);
        res.put("key", this.key);
        res.put("yxbz", this.yxbz);
        return res.toJSONString();
    }
}