package home.mechanixlab.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import home.mechanixlab.data.Video;


public interface VideoRepository extends CrudRepository<Video, Integer> {

	public List<Video> findByUserid(Integer userid);
}
