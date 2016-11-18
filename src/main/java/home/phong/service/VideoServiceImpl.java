package home.phong.service;

import java.util.List;

import org.springframework.stereotype.Service;

import home.phong.model.VideoModel;

@Service
public class VideoServiceImpl implements VideoService {

	@Override
	public int save(VideoModel videoModel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<VideoModel> findVideoByCurrentUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
