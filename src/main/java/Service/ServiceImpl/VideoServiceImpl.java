package Service.ServiceImpl;

import java.util.List;
import Dao.VideoDao;
import Dao.DaoImpl.VideoDaoImpl;
import Entity.Video;
import Service.VideoService;

public class VideoServiceImpl implements VideoService {
    private VideoDao videoDao = new VideoDaoImpl();

    @Override
    public void create(Video video) {
        videoDao.create(video);
    }

    @Override
    public void update(Video video) {
        videoDao.update(video);
    }

    @Override
    public void delete(Long id) {
        videoDao.delete(id);
    }

    @Override
    public  List<Video>  findByIdRL(int id) {
        return videoDao.findByIdRL(id);
    }

    @Override
    public List<Video> getListAll() {
        return videoDao.getListAll();
    }

    @Override
    public List<Video> searchByTitle(String keyword) {
        return videoDao.searchByTitle(keyword);
    }

	@Override
	public Video findById(int cateId) {
	
		return videoDao.findById(cateId);
	}
}