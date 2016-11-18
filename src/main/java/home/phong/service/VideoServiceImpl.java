package home.phong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.phong.convertor.ModelEntityConvertor;
import home.phong.data.Video;
import home.phong.model.VideoModel;
import home.phong.repository.VideoRepository;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoRepository videoRepository;
	
	@Override
	public VideoModel save(VideoModel videoModel) {
		Video video = ModelEntityConvertor.convertToEntity(videoModel);
		Video persitVideo = videoRepository.findOne(1);
		persitVideo.setVideotitle(video.getVideotitle());
		persitVideo.setVideodescription(video.getVideodescription());
		Video savedVideo = videoRepository.save(persitVideo);
		return ModelEntityConvertor.convertToModel(savedVideo);
	}

	@Override
	public List<VideoModel> findVideoByCurrentUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
