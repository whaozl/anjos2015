package com.express.back.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.express.back.dto.BackUserLoginData;
import com.express.back.exception.LoginException;

public class LoginInterceptor implements HandlerInterceptor {
	private String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除 

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		System.out.println(requestUrl);
		if (null != allowUrls && allowUrls.length >= 1){
			for (String url : allowUrls) {
				if (requestUrl.contains(url)) {
					return true;
				}
			}
		}
		BackUserLoginData buld = (BackUserLoginData) request.getSession().getAttribute("buld");
		if(buld != null) {    
            return true;  //返回true，则这个方面调用后会接着调用postHandle(),  afterCompletion()  
        }else{
            // 未登录跳转到登录页面  
            throw new LoginException();//返回到配置文件中定义的路径
        	//return true;//测试
        }
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	public String[] getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}
}
