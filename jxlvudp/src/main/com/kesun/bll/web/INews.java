package kesun.bll.web;



import kesun.entity.web.ImageNews;

import java.util.List;

/**
 * Created by wph-pc on 2017/11/3.
 */
public interface INews {
    int changeStatus(String status, List<String> ids);
    int delImg(String imageAddress);
    public List<ImageNews> imageNews(String id);
}
