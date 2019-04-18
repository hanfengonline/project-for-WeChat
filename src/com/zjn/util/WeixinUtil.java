package com.zjn.util;

import com.zjn.menu.Menu;
import com.zjn.pojo.AccessToken;
import com.zjn.pojo.WeixinUserInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* ����: WeixinUtil </br>
* ������ com.souvc.weixin.util
* ����: ����ƽ̨ͨ�ýӿڹ����� </br>
 */
public class WeixinUtil {
    
    //private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
    
    // ��ȡaccess_token�Ľӿڵ�ַ��GET�� ��200����/�죩
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    // �˵�������POST�� ��100����/�죩
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /*
     * �����˵�
     * 
     * @param menu �˵�ʵ��
     * @param accessToken ��Ч��access_token
     * @return 0��ʾ�ɹ�������ֵ��ʾʧ��
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;
        // ƴװ�����˵���url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // ���˵�����ת����json�ַ���
        String jsonMenu = JSONObject.fromObject(menu).toString();
        // ���ýӿڴ����˵�
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                //log.error("�����˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                System.out.println("�����˵�ʧ��"+" "+jsonObject.getInt("errcode")+" "+jsonObject.getString("errmsg"));
            }
        }

        return result;
    }
    
    
    /*
     * ��ȡaccess_token
     * 
     * @param appid ƾ֤
     * @param appsecret ��Կ
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;

        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // �������ɹ�
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // ��ȡtokenʧ��
                //log.error("��ȡtokenʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                System.out.println("��ȡʧ��"+" "+jsonObject.getInt("errcode")+" "+jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }
    
    
    /*
     * ����:  ����https���󲢻�ȡ���
     * @param requestUrl �����ַ
     * @param requestMethod ����ʽ��GET��POST��
     * @param outputStr �ύ������
     * @return JSONObject(ͨ��JSONObject.get(key)�ķ�ʽ��ȡjson���������ֵ)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // ������SSLContext�����еõ�SSLSocketFactory����
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            
            // ��������ʽ��GET/POST��
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // ����������Ҫ�ύʱ
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // ע������ʽ����ֹ��������
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // �����ص�������ת�����ַ���
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // �ͷ���Դ
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            //log.error("Weixin server connection timed out.");
            System.out.println("΢�����ӳ�ʱ");
        } catch (Exception e) {
            //log.error("https request error:{}", e);
            System.out.println("https�������");
        }
        return jsonObject;
    }
    
    /*
     * ��ȡ�û���Ϣ
     * 
     * @param accessToken �ӿڷ���ƾ֤
     * @param openId �û���ʶ
     * @return WeixinUserInfo
     */
    public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
        WeixinUserInfo weixinUserInfo = null;
        // ƴ�������ַ
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // ��ȡ�û���Ϣ
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        System.out.println(jsonObject.toString());
        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeixinUserInfo();
                
                System.out.println("get�û���Ϣ");
                // �û��ı�ʶ
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                System.out.println(weixinUserInfo.getOpenId());
                // ��ע״̬��1�ǹ�ע��0��δ��ע����δ��עʱ��ȡ����������Ϣ
                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // �û���עʱ��
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // �ǳ�
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // �û����Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // �û����ڹ���
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // �û�����ʡ��
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // �û����ڳ���
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // �û������ԣ���������Ϊzh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // �û�ͷ��
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    //log.error("�û�{}��ȡ����ע", weixinUserInfo.getOpenId());
                    
                    System.out.println("�û�{}��ȡ����ע"+weixinUserInfo.getOpenId());
                } else {
                    int errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    //log.error("��ȡ�û���Ϣʧ�� errcode:{} errmsg:{}", errorCode, errorCode);
                    System.out.println("��ȡ�û���Ϣʧ�� "+errorCode+errorCode);
                }
            }
        }
        return weixinUserInfo;
    }
}