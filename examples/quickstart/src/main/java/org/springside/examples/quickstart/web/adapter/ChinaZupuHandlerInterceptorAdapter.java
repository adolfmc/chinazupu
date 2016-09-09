package org.springside.examples.quickstart.web.adapter;

import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springside.examples.quickstart.entity.LoginLogger;
import org.springside.examples.quickstart.repository.LoginLoggerDao;

/**
 * 访问请求拦截
 * 
 * @author mc
 *
 */
@Component
public class ChinaZupuHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

	@Autowired
	private LoginLoggerDao loginLoggerDao;


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LoginLogger log = new LoginLogger();
		String requestUrl = request.getRequestURL().toString();// 得到请求的URL地址
//		String requestUri = request.getRequestURI();// 得到请求的资源
		String queryString = request.getQueryString();// 得到请求的URL地址中附带的参数
//		String remoteHost = request.getRemoteHost();
//		int remotePort = request.getRemotePort();
//		String remoteUser = request.getRemoteUser();
//		String method = request.getMethod();// 得到请求URL地址时使用的方法
//		String pathInfo = request.getPathInfo();
//		String localName = request.getLocalName();// 获取WEB服务器的主机名
		String remoteAddr = request.getRemoteAddr();// 得到来访者的IP地址
		String localAddr = request.getLocalAddr();// 获取WEB服务器的IP地址

		
		
		log.setCreateDate(Calendar.getInstance().getTime());
		StringBuffer sb = new StringBuffer(50);
		try {
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String nextElement = headerNames.nextElement();
				sb.append("[" + nextElement + "|" + request.getHeader(nextElement) + "]").append("\n");
			}
		} catch (Exception e) {
		}

		log.setIp(remoteAddr+"|"+localAddr+"|"+queryString);
		log.setInfo(sb.toString());
		log.setUrl(requestUrl);
		loginLoggerDao.save(log);
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}
