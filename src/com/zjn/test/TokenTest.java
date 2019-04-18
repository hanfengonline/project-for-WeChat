package com.zjn.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.junit.Test;


import com.zjn.pojo.Token;
import com.zjn.util.CommonUtil;
import com.zjn.util.MyX509TrustManager;
import com.zjn.util.TokenUtil;

public class TokenTest {
	
	String appid="wxea80ed13d0287c01";
	String appsecret="96f788c1bcba19fd64bf06bd7754500f";
	@Test
	public void testGetToken1() throws Exception{
		
		//�޸�appID,secret
		String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxea80ed13d0287c01&secret=96f788c1bcba19fd64bf06bd7754500f";
		URL url = new URL(tokenUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
		
		//����SSLContext���󣬲�ʹ������ָ�������˹�������ʼ��
		TrustManager[] tm = {new MyX509TrustManager()};
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		//������SSLContext�����еõ�SSLSocketFactory����
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		
		httpUrlConn.setSSLSocketFactory(ssf);
		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		
		//��������ʽ(GET/POST)
		httpUrlConn.setRequestMethod("GET");
		
		//ȡ��������
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
		//��ȡ��Ӧ����
		StringBuffer buffer = new StringBuffer();
		String str = null;
		while ((str = bufferedReader.readLine()) !=null) {
			buffer.append(str);
		}
		
		bufferedReader.close();
		inputStreamReader.close();
		
		//�ͷ���Դ
		inputStream.close();
		httpUrlConn.disconnect();
		//������ؽ��
		System.out.println(buffer);
	}
	@Test
	public void testGetToken2() {
		Token token = CommonUtil.getToken("wxea80ed13d0287c01", "96f788c1bcba19fd64bf06bd7754500f");
		System.out.println("access_token:"+token.getAccessToken());
		System.out.println("expires_in:"+token.getExpiresIn());
		
	}
	@Test
	public void testGetToken3() {
		Map<String,Object>token = TokenUtil.getToken();
		System.out.println("Token3����");
		System.out.println(token.get("access_token"));
		System.out.println(token.get("expires_in"));
	}
	@Test
	public void testSaveToken4() {
		System.out.println("Token4����");
		Token token = CommonUtil.getToken("wxea80ed13d0287c01", "96f788c1bcba19fd64bf06bd7754500f");
		TokenUtil.SaveToken(token);
	}

}
