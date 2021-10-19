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

import br.com.gabrielferreira.email.TurmaEmail;
import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.entidade.dto.TurmaDTO;
import br.com.gabrielferreira.entidade.enums.Turno;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.search.TurmaSearch;
import br.com.gabrielferreira.service.impl.TurmaServiceImpl;
import br.com.gabrielferreira.utils.FacesMessages;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class TurmaController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TurmaServiceImpl turmaServiceImpl;
	
	@Inject
	private TurmaEmail turmaEmail;
	
	private List<TurmaDTO> turmas;
	
	private Turma turmaDetalhes;
	
	private TurmaSearch turmaSearchParam;
	
	private Turma turma;

	private TurmaDTO turmaSelecionado;
	
	private boolean enviarEmailTurmaCadatrado;
	
	@PostConstruct
	public void inicializar() {
		turmaSearchParam = new TurmaSearch();
		turma = new Turma();
		verificarParametro();
	}
	
	private void verificarParametro() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idDetalheTurma = params.get("codigoDetalheTurma");
		String idAtualilzarTurma = params.get("codigoAtualilzarTurma");
		
		if(idDetalheTurma != null) {
			turmaDetalhes = turmaServiceImpl.getTurmaDetalhes(Integer.parseInt(idDetalheTurma));
		}
		
		if(idAtualilzarTurma != null) {
			turma = turmaServiceImpl.getById(Integer.parseInt(idAtualilzarTurma));
		}
	}
	
	public void consultar() {
		turmas = turmaServiceImpl.getFiltar(turmaSearchParam);
	}
	
	public void cadastrar() {
		if(turma.getId() == null) {
			inserirTurma();
		} else {
			atualizarTurma();
		}	
	}
	
	private void inserirTurma() {
		try {
			turmaServiceImpl.getInserirTurma(turma);
			FacesMessages.adicionarMensagem("cadastroTurmasForm:msg", FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso !",
					null);
			enviarEmailTurmaCadatrado = true;
			//turmaEmail.assuntoEmail(turma);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroTurmasForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	private void atualizarTurma() {
		try {
			turmaServiceImpl.getAtualizarTurma(turma);
			FacesMessages.adicionarMensagem("cadastroTurmasForm:msg", FacesMessage.SEVERITY_INFO,
					"Atualizado com sucesso !", null);			
			enviarEmailTurmaCadatrado = false;
			//turmaEmail.assuntoEmail(turma);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroTurmasForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
		
	}
	
	public void excluirTurma() {
		try {
			Turma turma = getTurmaDto(turmaSelecionado);			
			turmaServiceImpl.getRemoverTurma(turma);
			consultar();
			FacesMessages.adicionarMensagem("consultaTurmasForm:msg", FacesMessage.SEVERITY_INFO, "Removido com sucesso !",
					null);
			//turmaEmail.assuntoEmailTurmaExcluido(turma);
		} catch (RollbackException e) {
			FacesMessages.adicionarMensagem("consultaTurmasForm:msg", FacesMessage.SEVERITY_ERROR, "Não é possível excluir, pois tem aluno ou professor relacionado com essa turma !",
					"Não é possível excluir !");
		} catch(Exception e) {
			FacesMessages.adicionarMensagem("consultaTurmasForm:msg", FacesMessage.SEVERITY_ERROR,e.getMessage(),
					null);
		}
	}

	private Turma getTurmaDto(TurmaDTO turmaSelecionado) {
		Turma turma = new Turma();
		turma.setId(turmaSelecionado.getId());
		return turma;
	}
	
	public void novo() {
		turma = new Turma();
	}
	
	public void limparCamposPesquisa() {
		turmaSearchParam = new TurmaSearch();
		consultar();
	}
	
	public List<Turma> getTurmasConsultaAlunos(){
		return turmaServiceImpl.getListarTurmas();
	}

	public Turno[] getTurno() {
		return Turno.values();
	}
}
