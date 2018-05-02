package kesun.entity;
import java.util.Map;
import java.util.List;
/**
 * Created by wph-pc on 2017/5/27.
 */
public class Page {
    private Integer page;//page index,begin 0
    private Integer rowsCount;//display row amount per page
    private Integer total;//data rows total
    private List<Map<String,Object>> rows=null;//data content

    public Integer getRowsCount() {
        return rowsCount;
    }


    public void setRowsCount(Integer rowsCount) {
        this.rowsCount = rowsCount;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


}
