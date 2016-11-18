package home.phong.service;

import java.util.List;

import home.phong.model.VideoModel;

public interface VideoService {

	public VideoModel save(VideoModel videoModel);
	
	public List<VideoModel> findVideoByCurrentUser(Integer userId);
}
