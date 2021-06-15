package br.com.gabrielferreira.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.gabrielferreira.controller.LoginController;


public class UsuarioFilter implements Filter{
	
	@Inject
	private LoginController loginController;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) 	response;
		String url = httpServletRequest.getRequestURL().toString();
		
		if( ( url.contains("/aluno") || url.contains("/evento") || url.contains("/professor") || url.contains("/telefone") || url.contains("/turma") || url.contains("/HomePrincipal.xhtml") ) 
				&& !loginController.isPassou()) {
			httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath() + "/login/LoginUsuario.xhtml");
		} else {
			chain.doFilter(httpServletRequest, httpServletResponse);;
		}
		
		
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
