package br.com.gabrielferreira.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TelefoneFilter implements Filter{
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession httpSession = httpServletRequest.getSession();
		
		String url = httpServletRequest.getRequestURL().toString();
		String id = httpServletRequest.getParameter("codigo");
		
		if(url.contains("/telefone") && id != null && httpSession.getAttribute("telefone") != null) {
			chain.doFilter(httpServletRequest, httpServletResponse);
		} else {
			if(url.contains("/telefone") && id == null && httpSession.getAttribute("telefone") != null) {
				httpSession.removeAttribute("telefone");
				chain.doFilter(httpServletRequest, httpServletResponse); 
			} else {
				httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath() + "/HomePrincipal.xhtml");
			}
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
