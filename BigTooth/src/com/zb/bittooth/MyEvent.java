package com.zb.bittooth;
public class MyEvent {
	private String ITEMS;//是否有网
	private String  isShow;//是否显示置顶图片
	private String page;
	private String coutent;
	private String title;
	private String shareResult;
	public String getITEMS() {
		return ITEMS;
	}
	public void setITEMS(String iTEMS) {
		ITEMS = iTEMS;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getCoutent() {
		return coutent;
	}
	public void setCoutent(String coutent) {
		this.coutent = coutent;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShareResult() {
		return shareResult;
	}
	public void setShareResult(String shareResult) {
		this.shareResult = shareResult;
	}
	
}
