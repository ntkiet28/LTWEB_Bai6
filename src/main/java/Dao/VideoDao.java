package Dao;

import java.util.List;
import Entity.Video;

public interface VideoDao {
	void create(Video video);

	void update(Video video);

	void delete(Long id);
	Video findById(int id);

	 List<Video>  findByIdRL(int id);

	List<Video> getListAll();

	List<Video> searchByTitle(String keyword);
}