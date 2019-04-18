package com.zjn.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zjn.service.CoreService;
import com.zjn.util.MessageUtil;
import com.zjn.util.SignUtil;

@WebServlet("/CoreServlet")
public class CoreServlet extends HttpServlet {

    private static final long serialVersionUID = 4323197796926899691L;

    /*
     * ȷ����������΢�ŷ�����
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // ΢�ż���ǩ��
        String signature = request.getParameter("signature");
        // ʱ���
        String timestamp = request.getParameter("timestamp");
        // �����
        String nonce = request.getParameter("nonce");
        // ����ַ���
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        
        // ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        
        out.close();
        out = null;
    }

    /*
     * ����΢�ŷ�������������Ϣ
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ��Ϣ�Ľ��ա�������Ӧ
        // ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // ���ú���ҵ���������Ϣ��������Ϣ
        String respXml = CoreService.processRequest(request);
        File f = new File("/var/lib/tomcat8/webapps/WeiXinApp6/test.txt");
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(respXml.getBytes());
        fos.flush();
        fos.close();
        // ��Ӧ��Ϣ
        PrintWriter out = response.getWriter();
        out.print(respXml);
        out.close();
    }

}
