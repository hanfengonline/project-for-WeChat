package com.zjn.message.resp;

public class Music {
	private String Title;
	private String Description;
	private String MusicUrl;
	private String HQMusicUrl;//高质量音乐链接
	private String ThumbMediaId;//缩略图Id
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title=title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description=description;
	}
	public String getMusicUrl() {
		return MusicUrl;
	}
	public void setMusicUrl(String musicUrl) {
		MusicUrl=musicUrl;
	}
	public String getHQMusicUrl() {
		return HQMusicUrl;
	}
	public void setHQMusicUrl(String musicUrl) {
		HQMusicUrl=musicUrl;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId=thumbMediaId;
	}
}
