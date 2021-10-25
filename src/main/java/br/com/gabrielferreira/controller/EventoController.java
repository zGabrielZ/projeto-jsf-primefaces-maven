package br.com.gabrielferreira.controller;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.gabrielferreira.email.EventoEmail;
import br.com.gabrielferreira.entidade.ItensTurma;
import br.com.gabrielferreira.entidade.Professor;
import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.entidade.Usuario;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.service.impl.ItensTurmasServiceImpl;
import br.com.gabrielferreira.service.impl.ProfessorServiceImpl;
import br.com.gabrielferreira.service.impl.TurmaServiceImpl;
import br.com.gabrielferreira.utils.FacesMessages;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class EventoController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ItensTurmasServiceImpl itensTurmasServiceImpl;
	
	@Inject
	private ProfessorServiceImpl professorServiceImpl;
	
	@Inject
	private TurmaServiceImpl turmaServiceImpl;
	
	@Inject
	private EventoEmail eventoEmail;
	
	private List<ItensTurma> itensTurmas;
	
	private ItensTurma itensTurma;

	private ItensTurma itensTurmaSelecionado;
	
	@PostConstruct
	public void inicializar() {
		itensTurma = new ItensTurma();
		itensTurmas = itensTurmasServiceImpl.getListarItensTurmas();
	}
	
	public void novo() {
		itensTurma = new ItensTurma();
	}
	
	public void cadastrar() {
		if(itensTurma.getId() == null) {
			inserirItens();
		}	
	}
	
	private void inserirItens() {
		try {
			itensTurmasServiceImpl.getInserirItensTurmas(itensTurma);
			eventoEmail.assuntoEmail(itensTurma,obterEmailUsuarioLogado());
			inicializar();
			FacesMessages.adicionarMensagem("adicionarEventoForm:msg", FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso !",
					null);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("adicionarEventoForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	public void excluirItensTurmas() {
		ItensTurma itensTurma = itensTurmaSelecionado;	
		ItensTurma dadosItens = getDados(itensTurma.getId());
		itensTurmasServiceImpl.getRemoverItensTurmas(itensTurma);
		inicializar();
		FacesMessages.adicionarMensagem("adicionarEventoForm:msg", FacesMessage.SEVERITY_INFO, "Removido com sucesso !",
				null);
		eventoEmail.assuntoEmailItensTurmaExcluido(dadosItens,obterEmailUsuarioLogado());
	}
	
	private ItensTurma getDados(Integer id) {
		return itensTurmasServiceImpl.getDetalhe(id);
	}
	
	public List<Turma> getTurmas(){
		return turmaServiceImpl.getListarTurmas();
	}
	
	public List<Professor> getProfessores(){
		return professorServiceImpl.getListar();
	}
	
	private String obterEmailUsuarioLogado() {
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false);
		Usuario usuario = (Usuario) httpSession.getAttribute("usuarioLogado");
		return usuario.getEmail();
	}
	
	
}
