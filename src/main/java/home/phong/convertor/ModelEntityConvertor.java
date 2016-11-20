package home.phong.convertor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import home.phong.data.Role;
import home.phong.data.User;
import home.phong.data.Video;
import home.phong.model.UserModel;
import home.phong.model.VideoModel;

public class ModelEntityConvertor {

	public static UserModel convertToUserModel(User user){
		UserModel model = new UserModel();
		model.setUuid(user.getUuid());
		model.setName(user.getName());
		model.setRole(user.getRole().name());
		model.setPassword(user.getPassword());
		model.setAge(user.getAge());
		model.setCity(user.getCity());
		model.setDistrict(user.getDistrict());
		model.setEmail(user.getEmail());
		model.setMobileno(user.getMobileNo());
		model.setStatus(user.getStatus());
		model.setRegistrationdatetime(user.getRegistrationdatetime());
		return model;
	}
	
	public static List<UserModel> convertToUserModel(List<User> users){
		List<UserModel> returnLst = new ArrayList<UserModel>();
		for (User user : users) {
			returnLst.add(convertToUserModel(user));
		}
		return returnLst;
	}
	
	public static User convertToEntity(UserModel userModel) {
		User user = new User();
		user.setName(userModel.getName());
		user.setPassword(userModel.getPassword());
		user.setRole(Role.valueOf(userModel.getRole()));
		user.setAge(userModel.getAge());
		user.setCity(userModel.getCity());
		user.setDistrict(userModel.getDistrict());
		user.setMobileNo(userModel.getMobileno());
		user.setStatus(userModel.getStatus());
		user.setRegistrationdatetime(new Date());
		return user;
	}
	
	public static Video convertToEntity(VideoModel videoModel){
		Video video = new Video();
		video.setVideoId(videoModel.getVideoId());
		video.setFilename(videoModel.getFilename());
		video.setFiletype(videoModel.getFiletype());
		video.setFilepath(videoModel.getFilepath());
		video.setVideotitle(videoModel.getVideotitle());
		video.setVideodescription(videoModel.getVideodescription());
		video.setRef_uuid(videoModel.getRef_uuid());
		video.setRef_email(videoModel.getRef_email());
		video.setPublishno(videoModel.getPublishno());
		video.setPublish_dicsion(videoModel.getPublish_dicsion());
		video.setDicisionby(videoModel.getDicisionby());
		video.setDicisiondate(videoModel.getDicisiondate());
		return video;
	}
	
	public static VideoModel convertToModel(Video video){
		VideoModel videoModel = new VideoModel();
		videoModel.setFilename(video.getFilename());
		videoModel.setFiletype(video.getFiletype());
		videoModel.setFilepath(video.getFilepath());
		videoModel.setVideoId(video.getVideoId());
		videoModel.setVideotitle(video.getVideotitle());
		videoModel.setVideodescription(video.getVideodescription());
		videoModel.setRef_uuid(video.getRef_uuid());
		videoModel.setRef_email(video.getRef_email());
		videoModel.setPublishno(video.getPublishno());
		videoModel.setPublish_dicsion(video.getPublish_dicsion());
		videoModel.setDicisionby(video.getDicisionby());
		videoModel.setDicisiondate(video.getDicisiondate());
		return videoModel;
	}
	
	public static List<VideoModel> convertToModel (List<Video> videos) {
		List<VideoModel> videoModels = new ArrayList<VideoModel>();
		if (videos != null && videos.size() > 0) {
			for (Video v : videos) {
				VideoModel video = convertToModel(v);
				videoModels.add(video);
			}
		}
		return videoModels;
	}
}
