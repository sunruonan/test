package com.mmall.httpFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmall.common.Const;
import com.mmall.common.LoginUserContext;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.threadLocal.RequestHolder;

public class HttpFilter implements Filter{
	private static final Logger logger=LoggerFactory.getLogger(HttpFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest=(HttpServletRequest)request;
//		logger.info("do filter,{},{}",Thread.currentThread().getId(), servletRequest.getServletPath());
//		System.err.println(Thread.currentThread().getId()+","+servletRequest.getServletPath());
		HttpSession session = servletRequest.getSession();
		User user = (User)session.getAttribute(Const.CURRENT_USER);
//		if(user==null) {
//			System.out.println("未登录，需要强制登录status=10");
//		}
		if(user!=null) {
			LoginUserContext.setUser(user);
			chain.doFilter(request, response);
			return;
		}else {
			System.err.println("未登录，需要强制登录status=10");
		}
		
	}

	@Override
	public void destroy() {
		
	}

}
