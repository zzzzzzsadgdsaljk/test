package kesun.dao;

import kesun.entity.Page;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/11/9.
 */
public abstract class DaoSupport implements IDoData {
    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    private String mapperName;//映射文件名称

    public String getMapperName() {
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }


    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }
    public int save(Object obj) {
        return sqlSessionTemplate.insert(mapperName+".insert",obj);
    }

    public int batchSave(List objs) {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TransactionFactory transactionFactory = new JdbcTransactionFactory(); // 事务工厂
        Transaction newTransaction =  transactionFactory.newTransaction(sqlSession.getConnection());

        try {
            if (objs != null) {
                for (int i = 0; i < objs.size(); i++) {
                    if (sqlSession.insert(mapperName+".insert", objs.get(i))<=0)
                    {
                        newTransaction.rollback();//回滚
                        return -2;
                    }
                }
                sqlSession.clearCache();
                newTransaction.commit();
            }
        }catch (Exception e){
            try {
                newTransaction.rollback();//回滚
            } catch (SQLException e1) {
                e1.printStackTrace();
                return -1;
            }
            e.printStackTrace();
            return -1;
        }finally{
            sqlSession.close();//关闭资源
        }
        return objs.size();
    }

    public int batchUpdate(List objs) {
        int result = 0;
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TransactionFactory transactionFactory = new JdbcTransactionFactory(); // 事务工厂
        Transaction newTransaction =  transactionFactory.newTransaction(sqlSession.getConnection());
        try{
            if(objs!=null){
                for(int i=0,size=objs.size();i<size;i++){
                    result =  sqlSession.update(mapperName+".updateByPrimaryKey", objs.get(i));
                    if (result<=0)
                    {
                        newTransaction.rollback();//回滚
                        return -2;
                    }
                }
                sqlSession.clearCache();
                newTransaction.commit();
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return -1;
        }
        finally{
            sqlSession.close();
        }
        return result;
    }

    public int update(Object obj) {
        return sqlSessionTemplate.update(mapperName+".updateByPrimaryKey",obj);
    }

    public int delete(Object obj) {
        return sqlSessionTemplate.delete(mapperName+".deleteByPrimaryKey",obj);
    }

    public int batchDelete(List objs) {
        int result = 0;
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TransactionFactory transactionFactory = new JdbcTransactionFactory(); // 事务工厂
        Transaction newTransaction =  transactionFactory.newTransaction(sqlSession.getConnection());
        try {
            if (objs != null) {
                for (int i = 0, size = objs.size(); i < size; i++) {
                    if (objectInUse((String) objs.get(i))) {
                        newTransaction.rollback();
                        return -1;
                    }
                    if (sqlSession.delete(mapperName + ".deleteByPrimaryKey", objs.get(i))<=0)
                    {
                        newTransaction.rollback();
                        return -1;
                    }
                    result++;
                }
                sqlSession.clearCache();
                newTransaction.commit();
            }
        }catch (Exception ex){
            return -1;
        }finally{
            sqlSession.close();
        }
        return result;
    }

    public Object getMe(Object obj) {
        return sqlSessionTemplate.selectOne(mapperName+".selectByPrimaryKey",obj);
    }

    public Boolean objectInUse(String id) {
        List<?> obj=sqlSessionTemplate.selectList(mapperName+".findInUseById",id);
        if (obj==null || obj.size()==0)
            return false;
        else
            return true;
    }

    public List<Map<String, Object>> findForMap(Map values) {
        return sqlSessionTemplate.selectList(mapperName+".selectForMap",values);
    }

    public List<?> find(Map values) {
        return sqlSessionTemplate.selectList(mapperName+".selectForObject",values);
    }

    public List<Map<String, Object>> findByPage(Map conValues) {
        return sqlSessionTemplate.selectList(mapperName+".findByPage", conValues);
    }

    public int getRowsCount(Map values) {
        return (Integer)sqlSessionTemplate.selectOne(mapperName+".getRowsCount",values);
    }

    /*查找符合记录的总行数*/
    public int  findRowsCount(String tableName,String condition,List<Object> values)
    {
        String cmd="SELECT count(*) total FROM "+tableName+" WHERE "+condition;
        try
        {
            PreparedStatement pstmt =sqlSessionTemplate.getConnection().prepareStatement(cmd);
            setPreparedStatementParam(pstmt, values);
            ResultSet rs=pstmt.executeQuery();
            int total=0;
            if (rs.next()==false)
                total=0;
            else
                total= rs.getInt("total");
            rs.close();
            pstmt.close();
            return  total;
        }catch(java.sql.SQLException ex)
        {
            return 0;
        }catch (Exception e) {
            return 0;
        }
    }


    /**************************
     * name:findbyPage
     * @description:find data by page,but no order by statement;
     * @param cols:columns expression,for exmaple select * from test where name=? *
     * @param tableName:table's name
     * @param condition:find need condition
     * @param page:page code,from start 0
     * @param rows:display rows by per page
     ***************************/
    public Page find(String cols, String tableName, String condition, List<Object> conValues, int page, int rows, String orderFields, boolean isAsc)
    {
        page=page-1;
        Connection con=sqlSessionTemplate.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        String cmd="";
		/*适合于Oracle指令*/
        //if (isAsc)
        //cmd="select * from (SELECT ROWNUM AS rowno,table_alias.*  FROM (SELECT  "+cols+" FROM "+tableName+"  WHERE "+condition+" order by "+orderFields+" asc) table_alias) WHERE rowno >"+page*rows+" and rowno<= "+(page*rows+rows);
        //else
        //cmd="select * from (SELECT ROWNUM AS rowno,table_alias.*  FROM (SELECT  "+cols+" FROM "+tableName+"  WHERE "+condition+" order by "+orderFields+" desc) table_alias) WHERE rowno >"+page*rows+" and rowno<= "+(page*rows+rows);

		/*适合于MySQL指令*/
        if (isAsc)
            cmd="select table_b.* from (SELECT (@rownum:= @rownum+1) AS rowno,table_alias.*  FROM (SELECT  "+cols+" FROM (select @rownum:=0) r,"+tableName+"  WHERE "+condition+" order by "+orderFields+" asc) table_alias) table_b WHERE table_b.rowno >"+page*rows+" and table_b.rowno<= "+(page*rows+rows);
        else {
            cmd="select table_b.* from (SELECT (@rownum:= @rownum+1) AS rowno,table_alias.*  FROM (SELECT  "+cols+" FROM (select @rownum:=0) r,"+tableName+"  WHERE "+condition+" order by "+orderFields+" desc) table_alias) table_b WHERE table_b.rowno >"+page*rows+" and table_b.rowno<= "+(page*rows+rows);
        }

        try
        {
            ps=con.prepareStatement(cmd,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);//����������
            setPreparedStatementParam(ps, conValues);
            rs=ps.executeQuery();
            if (rs.next()==false)
                return null;
            else
            {
                rs.previous();
                Page data=new Page();
                data.setPage(page);
                data.setRowsCount(rows);
                data.setTotal(findRowsCount(tableName, condition,conValues));
                data.setRows(resultToList(rs));

                rs.close();
                ps.close();;
                con.close();

                return data;
            }
        }catch(java.sql.SQLException ex)
        {
            return null;
        }catch (Exception e) {
            return null;
        }
    }

    /**********************************************************
     * 将ResultSet转换成List
     *************************************************************/
    private List<Map<String,Object>> resultToList(ResultSet rsTemp)
    {
        if (rsTemp==null) return null;
        List<Map<String,Object>> list = new java.util.ArrayList<Map<String,Object>>();

        int columnCount=0;
        try {
            java.sql.ResultSetMetaData md = rsTemp.getMetaData();
            columnCount = md.getColumnCount();
            while (rsTemp.next()) {
                Map<String,Object> rowData = new HashMap<String,Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rsTemp.getObject(i));

                }
                list.add(rowData);
            }
            return list;
        } catch (java.sql.SQLException e) {
            return null;
        }
        catch (Exception ex)
        {
            return null;
        }

    }

    /*问号参数指令赋值*/
    private void setPreparedStatementParam(PreparedStatement pstmt, List<Object> paramList) throws Exception {
        if(pstmt == null || paramList == null || paramList.isEmpty()) {
            return;
        }
        DateFormat df = DateFormat.getDateTimeInstance();
        for (int i = 0; i < paramList.size(); i++) {
            if(paramList.get(i) instanceof Integer) {
                int paramValue = ((Integer)paramList.get(i)).intValue();
                pstmt.setInt(i+1, paramValue);
            } else if(paramList.get(i) instanceof Float) {
                float paramValue = ((Float)paramList.get(i)).floatValue();
                pstmt.setFloat(i+1, paramValue);
            } else if(paramList.get(i) instanceof Double) {
                double paramValue = ((Double)paramList.get(i)).doubleValue();
                pstmt.setDouble(i+1, paramValue);
            } else if(paramList.get(i) instanceof Date) {
                pstmt.setString(i+1, df.format((Date)paramList.get(i)));
            } else if(paramList.get(i) instanceof Long) {
                long paramValue = ((Long)paramList.get(i)).longValue();
                pstmt.setLong(i+1, paramValue);
            } else if(paramList.get(i) instanceof String) {
                pstmt.setString(i+1, (String)paramList.get(i));
            }else {
                pstmt.setObject(i+1, paramList.get(i));
            }
        }
        return;
    }

    /*新增常规操作*/
    public int doInsert(String methodName,Object obj)
    {
        return sqlSessionTemplate.insert(mapperName+"."+methodName,obj);
    }
    /*批量新增常规操作*/
    public int doBatchInsert(String methodName,List objs){
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TransactionFactory transactionFactory = new JdbcTransactionFactory(); // 事务工厂
        Transaction newTransaction =  transactionFactory.newTransaction(sqlSession.getConnection());
        try {
            if (objs != null) {
                for (int i = 0; i < objs.size(); i++) {
                    int result=sqlSession.insert(mapperName+"."+methodName, objs.get(i));
                    if (result<=0)
                    {
                        sqlSession.flushStatements();
                        newTransaction.rollback();//回滚
                        return -2;
                    }
                }
                sqlSession.flushStatements();
                sqlSession.clearCache();
                newTransaction.commit();
            }
        }catch (Exception e){
            sqlSession.flushStatements();
            e.printStackTrace();
            return -1;
        }finally{
            sqlSession.close();//关闭资源
        }
        return objs.size();
    }
    /*修改常规操作*/
    public int doUpdate(String methodName,Object obj)
    {
        return sqlSessionTemplate.update(mapperName+"."+methodName,obj);
    }
    /*批量修改常规操作*/
    public int doBatchUpdate(String methodName,List objs )throws Exception{
        int result = 0;
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TransactionFactory transactionFactory = new JdbcTransactionFactory(); // 事务工厂
        Transaction newTransaction =  transactionFactory.newTransaction(sqlSession.getConnection());
        try{
            if(objs!=null){
                for(int i=0,size=objs.size();i<size;i++){
                    result =  sqlSession.update(mapperName+"."+methodName, objs.get(i));
                    if (result<=0)
                    {
                        sqlSession.flushStatements();
                        newTransaction.rollback();//回滚
                        return -2;
                    }
                }
                sqlSession.flushStatements();
                sqlSession.clearCache();
                newTransaction.commit();
            }
        }finally{
            sqlSession.close();
        }
        return result;
    }
    /*删除常规操作*/
    public int doDelete(String methodName,Object obj)
    {
        return sqlSessionTemplate.delete(mapperName+"."+methodName,obj);
    }

    /*批量删除常规操作*/
    public int doBatchDelete(String methodName,List objs ){
        int result = 0;
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TransactionFactory transactionFactory = new JdbcTransactionFactory(); // 事务工厂
        Transaction newTransaction =  transactionFactory.newTransaction(sqlSession.getConnection());
        try {
            if (objs != null) {
                for (int i = 0, size = objs.size(); i < size; i++) {
                    if (objectInUse((String) objs.get(i))) {
                        newTransaction.rollback();
                        sqlSession.clearCache();
                        return -1;
                    }
                    if (sqlSession.delete(mapperName + "."+methodName, objs.get(i))<=0)
                    {
                        sqlSession.flushStatements();
                        sqlSession.clearCache();
                        newTransaction.rollback();//回滚
                        return -2;
                    }
                    result++;
                }
                sqlSession.flushStatements();
                newTransaction.commit();
                sqlSession.clearCache();
            }
        }catch (Exception ex){
            return -1;
        }finally{
            sqlSession.close();
        }
        return result;
    }

    /*通常查找操作*/
    public List<?> doFind(String methodName,Map values) throws Exception {
        return sqlSessionTemplate.selectList(mapperName+"."+methodName,values);
    }

    public Object doFindObject(String methodName,Map values) throws Exception {
        return sqlSessionTemplate.selectOne(mapperName+"."+methodName,values);
    }
    public Object doFindObject(String methodName,Object obj) throws Exception {
        return sqlSessionTemplate.selectOne(mapperName+"."+methodName,obj);
    }
    /*通常查找操作*/
    public List<?> doFind(String methodName,Object obj) throws Exception {
        return sqlSessionTemplate.selectList(mapperName+"."+methodName,obj);
    }

}
