package org.isima.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.isima.ui.faces.bean.login.LoginManagedBean;

@WebFilter("/app/*")
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException{ }
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true);
    
        LoginManagedBean user = (LoginManagedBean) session.getAttribute("loginManagedBean");
        
        if (user != null && user.isConnected()) {
        	chain.doFilter(request, response);
        }
        else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
    
    @Override
    public void destroy() { }
}
