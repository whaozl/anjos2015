package com.whaozl.anjos.persist.datasource;

/**
 * 数据源上下文
 * @author whaozl
 *
 */
public class DBContextHolder {
	
	private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<DataSourceType>(){
		@Override
		protected DataSourceType initialValue(){
			return DataSourceType.LKKData;
		}
	};  
    
    public static void set( DataSourceType dataSourceType ) {  
    	contextHolder.set(dataSourceType);  
    }  
      
    public static DataSourceType get() {  
        return contextHolder.get();  
    }  
      
    public static void reset() {  
        contextHolder.set( DataSourceType.LKKData );  
    }  
}
