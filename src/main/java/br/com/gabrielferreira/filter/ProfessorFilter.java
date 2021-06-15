package br.com.gabrielferreira.filter;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.gabrielferreira.entidade.Professor;
import br.com.gabrielferreira.repositorio.ProfessorRepositorio;


public class ProfessorFilter implements Filter{
	
	@Inject
	private ProfessorRepositorio professorRepositorio;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String url = httpServletRequest.getRequestURL().toString();
		String id = httpServletRequest.getParameter("codigo");
		
		List<Professor> professores = null;
		if(id != null) {
			professores = professorRepositorio.verificarProfessorId(Integer.parseInt(id));
		}
		
		
		if((url.contains("/professor/detalhe") || url.contains("/professor/atualizar")) && id != null && !professores.isEmpty()) {
			chain.doFilter(httpServletRequest, httpServletResponse);
		} else {
			httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath() + "/professor/ConsultaProfessor.xhtml");
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
