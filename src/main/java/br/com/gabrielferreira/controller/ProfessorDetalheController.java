package br.com.gabrielferreira.controller;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielferreira.entidade.ItensTurma;
import br.com.gabrielferreira.entidade.Professor;
import br.com.gabrielferreira.service.impl.ItensTurmasServiceImpl;
import br.com.gabrielferreira.service.impl.ProfessorServiceImpl;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ProfessorDetalheController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProfessorServiceImpl professorServiceImpl;
	
	@Inject
	private ItensTurmasServiceImpl itensTurmasServiceImpl;
	
	@Getter
	@Setter
	private List<ItensTurma> itensTurmas;
	
	@Getter
	@Setter
	private Professor professor;
	
	@PostConstruct
	public void inicializar() {
		 Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		 String id = params.get("codigo");
		 professor = professorServiceImpl.getConsultarDetalhe(Integer.parseInt(id));
		 itensTurmas = itensTurmasServiceImpl.getListarItensTurmasByProfessor(professor.getId());
		 professor.setItensTurmas(itensTurmas);
	}
	
}
