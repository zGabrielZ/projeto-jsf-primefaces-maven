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

import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.repositorio.TurmaRepositorio;


public class TurmaFilter implements Filter{
	
	@Inject
	private TurmaRepositorio turmaRepositorio;
	
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
		
		List<Turma> turmas = null;
		if(id != null) {
			turmas = turmaRepositorio.verificarTurmaId(Integer.parseInt(id));
		}
		
		if((url.contains("/turma/detalhe") || url.contains("/turma/atualizar") ) && id != null && !turmas.isEmpty()) {
			chain.doFilter(httpServletRequest, httpServletResponse);
		} else {
			httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath() + "/turma/ConsultaTurma.xhtml");
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
