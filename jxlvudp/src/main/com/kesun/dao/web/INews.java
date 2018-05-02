package kesun.dao.web;


import kesun.entity.web.ImageNews;
import kesun.entity.web.News;

import javax.print.DocFlavor;
import java.util.List;

/**
 * Created by wph-pc on 2017/11/3.
 */
public interface INews {
    int changeStatus(String status, List<String> ids);
    int delImg(String imageAddress);//删除图片
    public List<ImageNews> imageNews(String id);
}
