package com.zjn.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/*
* ����: MyX509TrustManager </br>
* ������ com.souvc.weixin.util
* ����: ֤�����ι�����������https����  </br>
* ������Ա��souvc  </br>
* ����ʱ�䣺  2015-12-1 </br>
* �����汾��V1.0  </br>
 */

public class MyX509TrustManager implements X509TrustManager{
	
	//���ͻ���֤��
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException{
		
	}
	
	//����������֤��
	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub
	}
	
	//���������ε�x509֤������
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}
}
