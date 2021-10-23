package br.com.gabrielferreira.controller;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.RollbackException;

import br.com.gabrielferreira.email.ProfessorEmail;
import br.com.gabrielferreira.entidade.Graduacao;
import br.com.gabrielferreira.entidade.Professor;
import br.com.gabrielferreira.entidade.enums.Sexo;
import br.com.gabrielferreira.entidade.enums.TipoPerfil;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.search.ProfessorSearch;
import br.com.gabrielferreira.service.impl.ProfessorServiceImpl;
import br.com.gabrielferreira.utils.FacesMessages;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class ProfessorController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProfessorServiceImpl professorServiceImpl;
	
	@Inject
	private ProfessorEmail professorEmail;
	
	private List<Professor> professores;
	
	private ProfessorSearch professorSearchParam;
	
	private Professor professor;
	
	private Graduacao graduacao;
	
	private Professor professorSelecionado;
	
	private boolean enviarEmailProfessorCadatrado;
	
	private Professor professorDetalhes;
	
	@PostConstruct
	public void inicializar() {
		professorSearchParam = new ProfessorSearch();
		professor = new Professor();
		graduacao = new Graduacao();
		professor.setGraduacao(graduacao);
		verificarParametro();
	}
	
	private void verificarParametro() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idDetalheProfessor = params.get("codigoDetalheProfessor");
		String idAtualizarProfessor = params.get("codigoAtualizarProfessor");
		
		if(idDetalheProfessor != null) {
			professorDetalhes = professorServiceImpl.getConsultarDetalhe(Integer.parseInt(idDetalheProfessor));
		}
		
		if(idAtualizarProfessor != null) {
			professor = professorServiceImpl.getConsultarDetalhe(Integer.parseInt(idAtualizarProfessor));
		}
	}
	
	public void novo() {
		professor = new Professor();
		graduacao = new Graduacao();
		professor.setGraduacao(graduacao);
	}
	
	public void consultar() {
		professores = professorServiceImpl.getFiltar(professorSearchParam);
	}
	
	public void limparPesquisa() {
		professorSearchParam = new ProfessorSearch();
		consultar();
	}
	
	public void cadastrar() {
		
		if(professor.getId() == null) {
			inserirProfessor(professor);
		} else {
			atualizarProfessor(professor);
		}
			
	}
	
	private void inserirProfessor(Professor professor) {
		try {
			professorServiceImpl.getInserirProfessor(professor);
			FacesMessages.adicionarMensagem("cadastroProfessorForm:msg", FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso !",
					null);
			novo();
			enviarEmailProfessorCadatrado = true;
			//professorEmail.assuntoEmail(professor);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroProfessorForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	private void atualizarProfessor(Professor professor) {
		try {
			professorServiceImpl.getAtualizarProfessor(professor);
			FacesMessages.adicionarMensagem("cadastroProfessorForm:msg", FacesMessage.SEVERITY_INFO, "Atualizado com sucesso !",
					null);
			novo();
			enviarEmailProfessorCadatrado = false;
			//professorEmail.assuntoEmail(professor);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroProfessorForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	public void excluirProfessor() {
		try {
			Professor professor = professorSelecionado;			
			professorServiceImpl.getRemoverProfessor(professor);
			//professorEmail.assuntoEmailProfessorExcluido(professor);
			consultar();
			FacesMessages.adicionarMensagem("consultaProfessoresForm:msg", FacesMessage.SEVERITY_INFO, "Removido com sucesso !",
					null);
		} catch(RollbackException e) {
			FacesMessages.adicionarMensagem("consultaProfessoresForm:msg", FacesMessage.SEVERITY_ERROR, "Não é possível excluir, pois tem turmas associadas !",
					"Não é possível excluir !");
		} catch (Exception e) {
			FacesMessages.adicionarMensagem("consultaProfessoresForm:msg", FacesMessage.SEVERITY_ERROR,e.getMessage(),
					null);
		}
	}
	
	public TipoPerfil[] getPerfil() {
		return TipoPerfil.values();
	}
	
	public Sexo[] getSexo() {
		return Sexo.values();
	}
	
}
