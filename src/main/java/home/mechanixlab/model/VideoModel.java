package home.mechanixlab.model;

import java.io.File;
import java.util.Date;

public class VideoModel {

	private Integer videoId;
	private Integer ref_uuid;
	private String ref_email;
	private String videotitle;
	private String videodescription;
	private Date uploaddatetime;
	private String publish_dicsion;
	private String dicisiondate;
	private String dicisionby;
	private String publishno;
	private String filepath;
	private String filetype;
	private String filename;
	private String thumbnailpath;
	private File thumbnailfile;
	private String errorMsg;
	
	public File getThumbnailfile() {
		return thumbnailfile;
	}
	public void setThumbnailfile(File thumbnailfile) {
		this.thumbnailfile = thumbnailfile;
	}
	public String getThumbnailpath() {
		return thumbnailpath;
	}
	public void setThumbnailpath(String thumbnailpath) {
		this.thumbnailpath = thumbnailpath;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Integer getVideoId() {
		return videoId;
	}
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	public Integer getRef_uuid() {
		return ref_uuid;
	}
	public void setRef_uuid(Integer ref_uuid) {
		this.ref_uuid = ref_uuid;
	}
	public String getRef_email() {
		return ref_email;
	}
	public void setRef_email(String ref_email) {
		this.ref_email = ref_email;
	}
	public String getVideotitle() {
		return videotitle;
	}
	public void setVideotitle(String videotitle) {
		this.videotitle = videotitle;
	}
	public String getVideodescription() {
		return videodescription;
	}
	public void setVideodescription(String videodescription) {
		this.videodescription = videodescription;
	}
	public Date getUploaddatetime() {
		return uploaddatetime;
	}
	public void setUploaddatetime(Date uploaddatetime) {
		this.uploaddatetime = uploaddatetime;
	}
	
	public String getPublish_dicsion() {
		return publish_dicsion;
	}
	public void setPublish_dicsion(String publish_dicsion) {
		this.publish_dicsion = publish_dicsion;
	}
	public String getDicisiondate() {
		return dicisiondate;
	}
	public void setDicisiondate(String dicisiondate) {
		this.dicisiondate = dicisiondate;
	}
	public String getDicisionby() {
		return dicisionby;
	}
	public void setDicisionby(String dicisionby) {
		this.dicisionby = dicisionby;
	}
	public String getPublishno() {
		return publishno;
	}
	public void setPublishno(String publishno) {
		this.publishno = publishno;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
//	public MultipartFile getFile() {
//		return file;
//	}
//	public void setFile(MultipartFile file) {
//		this.file = file;
//	}
	
}
