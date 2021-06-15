package br.com.gabrielferreira.controller;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
public class TurmaController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TurmaServiceImpl turmaServiceImpl;
	
	@Inject
	private NavegacaoController navegacaoController;
	
	@Inject
	private TurmaEmail turmaEmail;
	
	@Getter
	@Setter
	private List<TurmaDTO> turmas;
	
	@Getter
	@Setter
	private TurmaSearch turmaSearchParam;
	
	@Getter
	@Setter
	private Turma turma;
	
	@Getter
	@Setter
	private TurmaDTO turmaSelecionado;
	
	@Getter
	@Setter
	private boolean enviarEmailTurmaCadatrado;
	
	@PostConstruct
	public void inicializar() {
		turmaSearchParam = new TurmaSearch();
		turma = new Turma();
	}
	
	public void consultar() {
		turmas = turmaServiceImpl.getFiltar(turmaSearchParam);
	}
	
	public void cadastrar() {
		if(turma.getId() == null) {
			inserirTurma(turma);
			turma = new Turma();
		} else {
			atualizarTurma(turma);
		}
			
	}
	
	private void inserirTurma(Turma turma) {
		try {
			turmaServiceImpl.getInserirTurma(turma);
			FacesMessages.adicionarMensagem("cadastroTurmasForm:msg", FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso !",
					null);
			enviarEmailTurmaCadatrado = true;
			turmaEmail.assuntoEmail(turma);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroTurmasForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	private void atualizarTurma(Turma turma) {
		try {
			turmaServiceImpl.getAtualizarTurma(turma);
			FacesMessages.adicionarMensagem("consultaTurmasForm:msg", FacesMessage.SEVERITY_INFO,
					"Atualizado com sucesso !", null);

			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

			navegacaoController.consultaTurma();
			
			enviarEmailTurmaCadatrado = false;
			turmaEmail.assuntoEmail(turma);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("atualizarTurmasForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
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
			turmaEmail.assuntoEmailTurmaExcluido(turma);
		} catch (Exception e) {
			FacesMessages.adicionarMensagem("consultaTurmasForm:msg", FacesMessage.SEVERITY_ERROR, "Não é possível excluir, pois tem aluno ou professor relacionado com essa turma !",
					"Não é possível excluir !");
		}
	}
	
	public void carregar() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = params.get("codigo");
		turma = turmaServiceImpl.getById(Integer.parseInt(id));
	}
	
	public String editarTurma(TurmaDTO turmaDTO) {
		Turma turmaSel = getTurmaDto(turmaDTO);
		this.turma = turmaSel;
		return "/turma/atualizar/AtualizarTurma?faces-redirect=true&codigo="+this.turma.getId();
	}
	
	public String selecionarTurma(TurmaDTO turmaDTO) {
		this.turma = getTurmaDto(turmaDTO);
		return "/turma/detalhe/DetalheTurma?faces-redirect=true&codigo="+this.turma.getId();
	}

	private Turma getTurmaDto(TurmaDTO turmaSelecionado) {
		Turma turma = new Turma();
		turma.setId(turmaSelecionado.getId());
		return turma;
	}
	
	public void limparFormulario() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		HtmlInputText htmlInputTextNome = (HtmlInputText) uiViewRoot.findComponent("cadastroTurmasForm:nome");
		HtmlInputText htmlInputTextNumero = (HtmlInputText) uiViewRoot.findComponent("cadastroTurmasForm:numero");
		HtmlSelectOneMenu htmlInputTextTurno = (HtmlSelectOneMenu) uiViewRoot.findComponent("cadastroTurmasForm:turno");
		HtmlInputText htmlInputTextVaga = (HtmlInputText) uiViewRoot.findComponent("cadastroTurmasForm:vaga");
		htmlInputTextNome.setSubmittedValue("");
		htmlInputTextNumero.setSubmittedValue("");
		htmlInputTextVaga.setSubmittedValue("");
		htmlInputTextTurno.setSubmittedValue("");
		turma = new Turma();
	}
	
	public void limparCamposPesquisa() {
		turmaSearchParam = new TurmaSearch();
	}
	
	public List<Turma> getTurmasConsultaAlunos(){
		return turmaServiceImpl.getListarTurmas();
	}

	public Turno[] getTurno() {
		return Turno.values();
	}
}
