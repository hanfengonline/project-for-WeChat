package com.zjn.pojo;

/*
* 类名: AccessToken </br>
* 包名： com.souvc.weixin.pojo
* 描述: 微信通用接口凭证  </br>
 */

public class AccessToken {
	// 获取到的凭证
    private String token;
    // 凭证有效时间，单位：秒
    private int expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
