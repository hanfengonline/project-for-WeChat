package com.zjn.message.resp;

//响应消息的基类

public class BaseMessage {
	//接收方账号（收到的openID）
	private String ToUserName;
	//开发者微信号
	private String FromUserName;
	//消息创建时间（整型）
	private long CreateTime;
	//消息类型
	private String MsgType;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName)
	{
		ToUserName=toUserName;
	}
	
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName)
	{
		FromUserName=fromUserName;
	}
	
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime)
	{
		CreateTime=createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType)	{
		MsgType=msgType;
	}

}
