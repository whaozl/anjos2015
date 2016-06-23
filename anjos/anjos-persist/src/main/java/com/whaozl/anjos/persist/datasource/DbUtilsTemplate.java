package com.whaozl.anjos.persist.datasource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
 
/**
*/
@Component
public class DbUtilsTemplate {
	private static final Logger logger = Logger.getLogger(DbUtilsTemplate.class);
 
    private DataSource dataSource;
    private QueryRunner queryRunner;
 
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public QueryRunner getQueryRunner() {
        return this.queryRunner;
    }
 
    public DataSource getDataSource() {
        return this.dataSource;
    }
 
    /**
     * 执行sql语句
     * @param sql sql语句
     * @return 受影响的行数
     */
    public int update(String sql) {
        return update(sql, null);
    }
       
    /**
     * 执行sql语句
     * <code>
     * executeUpdate("update user set username = 'kitty' where username = ?", "hello kitty");
     * </code>
     * @param sql sql语句
     * @param param 参数
     * @return 受影响的行数
     */
    public int update(String sql, Object param) {
        return update(sql, new Object[] { param });
    }
       
    /**
     * 执行sql语句
     * @param sql sql语句
     * @param params 参数数组
     * @return 受影响的行数
     */
    public int update(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        int affectedRows = 0;
        try {
            if (params == null) {
                affectedRows = queryRunner.update(sql);
            } else {
                affectedRows = queryRunner.update(sql, params);
            }
        } catch (SQLException e) {
            logger.error("尝试更新数据时出现错误", e);
        }
        return affectedRows;
    }
    
    /**
     * 执行批量sql语句
     * @param sql sql语句
     * @param params 二维参数数组
     * @return 受影响的行数的数组
     */
    public int[] batchUpdate(String sql, Object[][] params, boolean IsTransaction){
    	int[] affectedRows = new int[0];
    	if(IsTransaction==true){
    		Connection conn =null;
    		try {
				conn = dataSource.getConnection();
				queryRunner = new QueryRunner();
				conn.setAutoCommit(false);//开启事务
				affectedRows = queryRunner.batch(conn, sql, params);
				conn.commit();//提交事务
			} catch (SQLException e) {
				e.printStackTrace();
				if(conn!=null){
						try {
							conn.rollback();//回滚事务
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
				}
			}finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
    		
    	}else{
            queryRunner = new QueryRunner(dataSource);
            try {
                affectedRows = queryRunner.batch(sql, params);
            } catch (SQLException e) {
                logger.error("尝试批量更新数据时出现错误", e);
            }
    	}
        return affectedRows;
    }
    
    
 
    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * @param sql sql语句
     * @return 查询结果
     */
    public List<Map<String, Object>> find(String sql) {
        return find(sql, null);
    }
       
    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * @param sql sql语句
     * @param param 参数
     * @return 查询结果
     */
    public List<Map<String, Object>> find(String sql, Object param) {
        return find(sql, new Object[] {param});
    }
       
    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * @param sql sql语句
     * @param params 参数数组
     * @return 查询结果
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> find(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        try {
            if (params == null) {
                list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler());
            } else {
                list = (List<Map<String, Object>>) queryRunner.query(sql, params, new MapListHandler());
            }
        } catch (SQLException e) {
            logger.error("尝试查询数据时出现错误", e);
        }
        return list;
    }
    
    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * @param sql sql语句
     * @return 查询结果
     */
    public List<Object[]> query(String sql) {
        return query(sql, null);
    }
       
    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * @param sql sql语句
     * @param param 参数
     * @return 查询结果
     */
    public List<Object[]> query(String sql, Object param) {
        return query(sql, new Object[] {param});
    }
       
    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * @param sql sql语句
     * @param params 参数数组
     * @return 查询结果
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> query(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            if (params == null) {
                list = (List<Object[]>) queryRunner.query(sql, new ArrayListHandler());
            } else {
                list = (List<Object[]>) queryRunner.query(sql, params, new ArrayListHandler());
            }
        } catch (SQLException e) {
            logger.error("尝试查询数据时出现错误", e);
        }
        return list;
    }
    
    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     * @param entityClass 类名
     * @param sql sql语句
     * @return 查询结果
     */
    public <T> List<T> find(Class<T> entityClass, String sql) {
        return find(entityClass, sql, null);
    }
       
    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     * @param entityClass 类名
     * @param sql sql语句
     * @param param 参数
     * @return 查询结果
     */
    public <T> List<T> find(Class<T> entityClass, String sql, Object param) {
        return find(entityClass, sql, new Object[] { param });
    }
       
    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     * @param entityClass 类名
     * @param sql sql语句
     * @param params 参数数组
     * @return 查询结果
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<T> entityClass, String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        List<T> list = new ArrayList<T>();
        try {
            if (params == null) {
                list = (List<T>) queryRunner.query(sql, new BeanListHandler(entityClass));
            } else {
                list = (List<T>) queryRunner.query(sql, params, new BeanListHandler(entityClass));
            }
        } catch (SQLException e) {
            logger.error("尝试查询数据时出现错误", e);
        }
        return list;
    }
    
	public List<Object[]> findByArrayListHandlerMuti(String sql) {
		return findByArrayListHandlerMuti(sql, null);
	}
    
	/**
	 * 返回的是List的数组
	 */
    @SuppressWarnings("unchecked")
	public List<Object[]> findByArrayListHandlerMuti(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            if (params == null) {
                list = (List<Object[]>) queryRunner.query(sql, new ArrayListHandler());
            } else {
                list = (List<Object[]>) queryRunner.query(sql, params, new ArrayListHandler());
            }
        } catch (SQLException e) {
            logger.error("尝试查询数据时出现错误", e);
        }
        return list;
    }
    
    
    /**
     * 查询出结果集中的第一条记录，并封装成对象
     * @param entityClass 类名
     * @param sql sql语句
     * @return 对象
     */
    public <T> T findFirst(Class<T> entityClass, String sql) {
        return findFirst(entityClass, sql, null);
    }
       
    /**
     * 查询出结果集中的第一条记录，并封装成对象
     * @param entityClass 类名
     * @param sql sql语句
     * @param param 参数
     * @return 对象
     */
    public <T> T findFirst(Class<T> entityClass, String sql, Object param) {
        return findFirst(entityClass, sql, new Object[] { param });
    }
       
    /**
     * 查询出结果集中的第一条记录，并封装成对象
     * @param entityClass 类名
     * @param sql sql语句
     * @param params 参数数组
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public <T> T findFirst(Class<T> entityClass, String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new BeanHandler(entityClass));
            } else {
                object = queryRunner.query(sql, params, new BeanHandler(entityClass));
            }
        } catch (SQLException e) {
            logger.error("尝试查询数据时出现错误", e);
        }
        return (T) object;
    }
       
    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     * @param sql sql语句
     * @return 封装为Map的对象
     */
    public Map<String, Object> findFirst(String sql) {
        return findFirst(sql, null);
    }
       
    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     * @param sql sql语句
     * @param param 参数
     * @return 封装为Map的对象
     */
    public Map<String, Object> findFirst(String sql, Object param) {
        return findFirst(sql, new Object[] { param });
    }
       
    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     * @param sql sql语句
     * @param params 参数数组
     * @return 封装为Map的对象
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> findFirst(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Map<String, Object> map = null;
        try {
            if (params == null) {
                map = (Map<String, Object>) queryRunner.query(sql, new MapHandler());
            } else {
                map = (Map<String, Object>) queryRunner.query(sql, params, new MapHandler());
            }
        } catch (SQLException e) {
            logger.error("尝试查询数据时出现错误", e);
        }
        return map;
    }
       
    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * @param sql sql语句
     * @param columnName 列名
     * @return 结果对象
     */
    public Object findBy(String sql, String columnName) {
        return findBy(sql, columnName, null);
    }
       
    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * @param sql sql语句
     * @param columnName 列名
     * @param param 参数
     * @return 结果对象
     */
    public Object findBy(String sql, String columnName, Object param) {
        return findBy(sql, columnName, new Object[] { param });
    }
       
    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * @param sql sql语句
     * @param columnName 列名
     * @param params 参数数组
     * @return 结果对象
     */
    public Object findBy(String sql, String columnName, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler(columnName));
            } else {
                object = queryRunner.query(sql, params, new ScalarHandler(columnName));
            }
        } catch (SQLException e) {
            logger.error("尝试查询数据时出现错误", e);
        }
        return object;
    }
       
    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * @param sql sql语句
     * @param columnIndex 列索引
     * @return 结果对象
     */
    public Object findBy(String sql, int columnIndex) {
        return findBy(sql, columnIndex, null);
    }
       
    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * @param sql sql语句
     * @param columnIndex 列索引
     * @param param 参数
     * @return 结果对象
     */
    public Object findBy(String sql, int columnIndex, Object param) {
        return findBy(sql, columnIndex, new Object[] { param });
    }
       
    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * @param sql sql语句
     * @param columnIndex 列索引
     * @param params 参数数组
     * @return 结果对象
     */
    public Object findBy(String sql, int columnIndex, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler(columnIndex));
            } else {
                object = queryRunner.query(sql, params, new ScalarHandler(columnIndex));
            }
        } catch (SQLException e) {
            logger.error("尝试查询数据时出现错误", e);
        }
        return object;
    }
}