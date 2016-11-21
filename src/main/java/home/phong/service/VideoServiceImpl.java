package home.phong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
//		Video persitVideo = videoRepository.findOne(1);
//		persitVideo.setVideotitle(video.getVideotitle());
//		persitVideo.setVideodescription(video.getVideodescription());
		Video savedVideo = videoRepository.save(video);
		return ModelEntityConvertor.convertToModel(savedVideo);
	}

	@Override
	public List<VideoModel> findVideoByCurrentUser(Integer userId) {
		List<Video> videos = videoRepository.findByUserid(userId);
		List<VideoModel> videoModels = ModelEntityConvertor.convertToModel(videos);
		return videoModels;
	}

	@Override
	public VideoModel findVideoById(Integer id) {
		// TODO Auto-generated method Estub
		Video video = videoRepository.findOne(id);
		VideoModel model = ModelEntityConvertor.convertToModel(video);
		return model;
	}

	@Override
	public VideoModel update(VideoModel videoModel) {
		if (videoModel != null) {
			Video video = videoRepository.findOne(videoModel.getVideoId());
			if (video != null) {
				if (!StringUtils.isEmpty(videoModel.getVideotitle())) {
					video.setVideotitle(videoModel.getVideotitle());
				}
				if (!StringUtils.isEmpty(videoModel.getVideodescription())) {
					video.setVideodescription(videoModel.getVideodescription());
				}
				if (!StringUtils.isEmpty(videoModel.getRef_email())) {
					video.setUseremail(videoModel.getRef_email());
				}
				if (!StringUtils.isEmpty(videoModel.getPublishno())) {
					video.setPublishno(videoModel.getPublishno());
				}
				if (!StringUtils.isEmpty(videoModel.getPublish_dicsion())) {
					video.setPublish_dicsion(videoModel.getPublish_dicsion());
				}
				if (!StringUtils.isEmpty(videoModel.getDicisionby())) {
					video.setDicisionby(videoModel.getDicisionby());
				}
				if (!StringUtils.isEmpty(videoModel.getDicisiondate())) {
					video.setDicisiondate(videoModel.getDicisiondate());
				}
				Video returnVideo = videoRepository.save(video);
				return ModelEntityConvertor.convertToModel(returnVideo);
			}
		}
		return null;
	}

}
