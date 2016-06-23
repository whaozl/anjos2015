package com.whaozl.anjos.web.test;

import org.apache.log4j.PropertyConfigurator;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:com/whaozl/anjos/web/config/spring-core.xml"})
@Transactional
public class SpringTest extends AbstractJUnit4SpringContextTests {
	
	 static {  
	        //Log4jConfigurer.initLogging("src/test/resources/com/whaozl/anjos/web/config/log4j.properties");  
			PropertyConfigurator.configure("src/test/resources/com/whaozl/anjos/web/config/log4j.properties");  
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
