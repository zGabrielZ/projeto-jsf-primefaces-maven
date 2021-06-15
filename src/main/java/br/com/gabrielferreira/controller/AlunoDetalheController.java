package br.com.gabrielferreira.controller;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielferreira.entidade.Aluno;
import br.com.gabrielferreira.service.impl.AlunoServiceImpl;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class AlunoDetalheController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AlunoServiceImpl alunoServiceImpl;
	
	@Getter
	@Setter
	private Aluno aluno;
	
	@PostConstruct
	public void inicializar() {
		 Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		 String id = params.get("codigo");
		 aluno = alunoServiceImpl.getConsultarDetalhe(Integer.parseInt(id));
	}	
	
}
