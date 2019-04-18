package com.zjn.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjn.pojo.Token;
import com.zjn.util.CommonUtil;
import com.zjn.util.TokenUtil;

/*
     * ����: TokenThread
     * ����: ��ʱ��ȡ΢��access_token���߳�
 */

public class TokenThread implements Runnable{
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	//�������û�Ψһƾ֤
	public static String appid = "";
	//�������û�Ψһƾ֤��Կ
	public static String appsecret = "";
	public static Token accessToken = null;
	
	public void run() {
		while(true) {
			try {
				accessToken = CommonUtil.getToken(appid, appsecret);
				if(null !=accessToken) {
					//���ô洢�����ݿ�
					TokenUtil.SaveToken(accessToken);
					//log.info("��ȡaccess_token�ɹ�����Чʱ��{}�� token:{}", accessToken.getExpiresIn(), accessToken.getAccessToken());
					//����7000
					Thread.sleep((accessToken.getExpiresIn() - 200)*1000);
					
				}else {
					//���access_TokenΪnull,60����ڻ�ȡ
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
