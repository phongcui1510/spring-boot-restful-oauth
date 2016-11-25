package home.mechanixlab.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "es_video")
public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "videoid", nullable = false, updatable = false)
	private Integer videoId;
	
	@Column(name = "userid")
	private Integer userid;
	
	@Column(name = "useremail")
	private String useremail;
	
	@Column(name = "videotitle")
	private String videotitle;
	
	@Column(name = "videodescription")
	private String videodescription;
	
	@Column(name = "uploaddatetime")
	private Date uploaddatetime;
	
	@Column(name = "publish_dicsion")
	private String publish_dicsion;
	
	@Column(name = "dicisiondate")
	private String dicisiondate;
	
	@Column(name = "dicisionby")
	private String dicisionby;
	
	@Column(name = "publishno")
	private String publishno;
	
	@Column(name = "filepath")
	private String filepath;
	
	@Column(name = "filetype")
	private String filetype;
	
	@Column(name = "filename")
	private String filename;
	
	@Column(name = "thumbnailpath")
	private String thumbnailpath;
	
	
	public String getThumbnailpath() {
		return thumbnailpath;
	}
	public void setThumbnailpath(String thumbnailpath) {
		this.thumbnailpath = thumbnailpath;
	}
	public Integer getVideoId() {
		return videoId;
	}
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
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
	
}
