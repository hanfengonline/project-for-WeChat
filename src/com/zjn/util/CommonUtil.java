package com.zjn.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjn.pojo.Token;

/*
 * 类名：CommonUtil
 * 描述：通用工具类
 */
public class CommonUtil {
	
	//private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	//凭证获取（GET）
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/*
	 * 发送http请求
	 * requestUrl 请求地址
	 * requestMethod 请求方式（GET,POST）
	 * outputStr 提交的数据
	 * JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 * 
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
            //System.out.println(buffer.toString()+"——这里错了");
        } catch (ConnectException ce) {
            //log.error("连接超时：{}", ce);
        	System.out.println("链接超时");
        } catch (Exception e) {
            //log.error("https请求异常：{}", e);
        	System.out.println("https请求异常");
        }
        return jsonObject;
    }

	
	
	/*
	 * 获取接口访问凭证
	 */
	public  static Token getToken(String appid,String appsecret) {
		Token token = null;
		//System.out.println(appid);
		//System.out.println(appsecret);
		String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		//System.out.println(requestUrl);
		//requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxea80ed13d0287c01&secret=96f788c1bcba19fd64bf06bd7754500f";
		JSONObject jsonObject = httpsRequest(requestUrl,"GET",null);
		//System.out.println(jsonObject.toString());
		if(null!=jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			}catch (JSONException e) {
				token =null;
				System.out.println("获取失败");
				System.out.println(token_url);
				e.printStackTrace();
				//获取token失败
				//log.error("获取token失败 errcode:{} errmsg:{}",jsonObject.getInt("errcode"),jsonObject.getString("errmsg"));
			}
		}
		return token;
	}
	
	/*
     * URL编码（utf-8）
     * 
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /*
     * 根据内容类型判断文件扩展名
     * 
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType))
            fileExt = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileExt = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileExt = ".amr";
        else if ("video/mp4".equals(contentType))
            fileExt = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileExt = ".mp4";
        return fileExt;
    }

}
