package com.zjn.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/*
* 类名: MyX509TrustManager </br>
* 包名： com.souvc.weixin.util
* 描述: 证书信任管理器（用于https请求）  </br>
* 开发人员：souvc  </br>
* 创建时间：  2015-12-1 </br>
* 发布版本：V1.0  </br>
 */

public class MyX509TrustManager implements X509TrustManager{
	
	//检查客户端证书
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException{
		
	}
	
	//检查服务器端证书
	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub
	}
	
	//返回收信任的x509证书数组
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}
}
