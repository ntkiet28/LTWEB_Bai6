package Service;

import java.util.List;
import Entity.Video;

public interface VideoService {
	void create(Video video);

	void update(Video video);

	void delete(Long id);
	
	Video findById(int cateId);

	List<Video> findByIdRL(int cateId);

	List<Video> getListAll();

	List<Video> searchByTitle(String keyword);
}