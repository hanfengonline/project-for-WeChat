package com.zjn.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjn.pojo.Token;
import com.zjn.util.CommonUtil;
import com.zjn.util.TokenUtil;

/*
     * 类名: TokenThread
     * 描述: 定时获取微信access_token的线程
 */

public class TokenThread implements Runnable{
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	//第三方用户唯一凭证
	public static String appid = "";
	//第三方用户唯一凭证秘钥
	public static String appsecret = "";
	public static Token accessToken = null;
	
	public void run() {
		while(true) {
			try {
				accessToken = CommonUtil.getToken(appid, appsecret);
				if(null !=accessToken) {
					//调用存储到数据库
					TokenUtil.SaveToken(accessToken);
					//log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getAccessToken());
					//休眠7000
					Thread.sleep((accessToken.getExpiresIn() - 200)*1000);
					
				}else {
					//如果access_Token为null,60秒后在获取
					Thread.sleep(60*1000);
				}
			}catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				}catch (InterruptedException e1) {
					log.error("{}",e1);
				}
				log.error("{}", e);
			}
		}
	}

}
