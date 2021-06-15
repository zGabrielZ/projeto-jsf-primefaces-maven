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
import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.service.impl.ItensTurmasServiceImpl;
import br.com.gabrielferreira.service.impl.TurmaServiceImpl;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class TurmaDetalheController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TurmaServiceImpl turmaServiceImpl;
	
	@Inject
	private ItensTurmasServiceImpl itensTurmasServiceImpl;
	
	@Getter
	@Setter
	private List<ItensTurma> itensTurmas;
	
	@Getter
	@Setter
	private Turma turma;
	
	@PostConstruct
	public void inicializar() {
		 Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		 String id = params.get("codigo");
		 turma = turmaServiceImpl.getById(Integer.parseInt(id));
		 itensTurmas = itensTurmasServiceImpl.getListarItensTurmasByTurma(turma.getId());
		 turma.setItensTurmas(itensTurmas);
	}
}
