package home.mechanixlab.service;

import java.util.List;

import home.mechanixlab.model.VideoModel;


public interface VideoService {

	public VideoModel save(VideoModel videoModel);
	
	public VideoModel findVideoById(Integer id);
	
	public List<VideoModel> findVideoByCurrentUser(Integer userId);
	
	public VideoModel update (VideoModel videoModel);
}
