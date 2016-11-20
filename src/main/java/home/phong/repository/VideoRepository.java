package home.phong.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import home.phong.data.Video;

public interface VideoRepository extends CrudRepository<Video, Integer> {

	public List<Video> findByUserid(Integer userid);
}
