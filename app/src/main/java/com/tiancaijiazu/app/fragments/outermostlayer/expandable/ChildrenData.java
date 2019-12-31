package com.tiancaijiazu.app.fragments.outermostlayer.expandable;

/**
 * 二级列表数据泪
 */
public class ChildrenData {
	private int contentsId;
	private String title;
	private String duration;
	private int chapterId;
	private int courseId;
	private int type;
	private String picUri;
	private String mediaUri;
	private int isFree;
	private String description;
	private boolean isChoose;

	public ChildrenData() {
		super();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	public int getContentsId() {
		return contentsId;
	}

	public void setContentsId(int contentsId) {
		this.contentsId = contentsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPicUri() {
		return picUri;
	}

	public void setPicUri(String picUri) {
		this.picUri = picUri;
	}

	public String getMediaUri() {
		return mediaUri;
	}

	public void setMediaUri(String mediaUri) {
		this.mediaUri = mediaUri;
	}

	public int getIsFree() {
		return isFree;
	}

	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isChoose() {
		return isChoose;
	}

	public void setChoose(boolean choose) {
		isChoose = choose;
	}

	public ChildrenData(int contentsId, String title, String duration, int chapterId, int courseId, int type, String picUri, String mediaUri, int isFree, String description, boolean isChoose) {

		this.contentsId = contentsId;
		this.title = title;
		this.duration = duration;
		this.chapterId = chapterId;
		this.courseId = courseId;
		this.type = type;
		this.picUri = picUri;
		this.mediaUri = mediaUri;
		this.isFree = isFree;
		this.description = description;
		this.isChoose = isChoose;
	}
}
