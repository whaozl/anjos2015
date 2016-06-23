package com.whaozl.anjos.service.test;

import java.io.FileNotFoundException;

import org.apache.log4j.PropertyConfigurator;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Log4jConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:com/whaozl/anjos/service/config/spring-core.xml"})
@Transactional
public class SpringTest extends AbstractJUnit4SpringContextTests {
	
	 static {  
	        try {
				Log4jConfigurer.initLogging("classpath:com/whaozl/anjos/service/config/log4j.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}  
			//PropertyConfigurator.configure("src/test/resources/com/whaozl/anjos/service/config/log4j.properties");  
	    } 
	
	public <T> T getBean(Class<T> type){
	    return applicationContext.getBean(type);
	   }
	    
	   public Object getBean(String beanName){
	    return applicationContext.getBean(beanName);
	   }
	   
	   protected ApplicationContext getContext(){
	    return applicationContext;
	   }
}
