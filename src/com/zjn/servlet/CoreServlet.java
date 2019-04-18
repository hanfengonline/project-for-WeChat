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
     * 确认请求来自微信服务器
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        
        out.close();
        out = null;
    }

    /*
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 消息的接收、处理、响应
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 调用核心业务类接收消息、处理消息
        String respXml = CoreService.processRequest(request);
        File f = new File("/var/lib/tomcat8/webapps/WeiXinApp6/test.txt");
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(respXml.getBytes());
        fos.flush();
        fos.close();
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respXml);
        out.close();
    }

}
