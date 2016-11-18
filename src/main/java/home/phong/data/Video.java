package home.phong.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "es_user")
public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "videoId", nullable = false, updatable = false)
	private Integer videoId;
	
	@Column(name = "ref_uuid")
	private Integer ref_uuid;
	
	@Column(name = "ref_email")
	private String ref_email;
	
	@Column(name = "videotitle")
	private String videotitle;
	
	@Column(name = "videodescription")
	private String videodescription;
	
	@Column(name = "uploaddatetime")
	private Date uploaddatetime;
	
	@Column(name = "publish_diction")
	private String publish_diction;
	
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
	public String getPublish_diction() {
		return publish_diction;
	}
	public void setPublish_diction(String publish_diction) {
		this.publish_diction = publish_diction;
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
