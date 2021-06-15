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

import br.com.gabrielferreira.email.ProfessorEmail;
import br.com.gabrielferreira.entidade.Graduacao;
import br.com.gabrielferreira.entidade.Professor;
import br.com.gabrielferreira.entidade.enums.Sexo;
import br.com.gabrielferreira.entidade.enums.TipoPerfil;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.search.ProfessorSearch;
import br.com.gabrielferreira.service.impl.ProfessorServiceImpl;
import br.com.gabrielferreira.utils.FacesMessages;
import br.com.gabrielferreira.utils.SessionUtil;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ProfessorController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProfessorServiceImpl professorServiceImpl;
	
	@Inject
	private NavegacaoController navegacaoController;
	
	@Inject
	private ProfessorEmail professorEmail;
	
	@Getter
	@Setter
	private List<Professor> professores;
	
	@Getter
	@Setter
	private ProfessorSearch professorSearchParam;
	
	@Getter
	@Setter
	private Professor professor;
	
	@Getter
	@Setter
	private Graduacao graduacao;
	
	@Getter
	@Setter
	private Professor professorSelecionado;
	
	@Getter
	@Setter
	private boolean enviarEmailProfessorCadatrado;
	
	@PostConstruct
	public void inicializar() {
		professorSearchParam = new ProfessorSearch();
		professor = new Professor();
		graduacao = new Graduacao();
		
		professor.setGraduacao(graduacao);
	}
	
	public void consultar() {
		professores = professorServiceImpl.getFiltar(professorSearchParam);
	}
	
	public void limparPesquisa() {
		professorSearchParam = new ProfessorSearch();
	}
	
	public void cadastrar() {
		
		if(professor.getId() == null) {
			inserirProfessor(professor);
			graduacao = new Graduacao();
			professor = new Professor();
			professor.setGraduacao(graduacao);
		} else {
			atualizarProfessor(professor);
		}
			
	}
	
	private void inserirProfessor(Professor professor) {
		try {
			professorServiceImpl.getInserirProfessor(professor);
			FacesMessages.adicionarMensagem("cadastroProfessorForm:msg", FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso !",
					null);
			enviarEmailProfessorCadatrado = true;
			professorEmail.assuntoEmail(professor);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroProfessorForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	private void atualizarProfessor(Professor professor) {
		try {
			professorServiceImpl.getAtualizarProfessor(professor);
			FacesMessages.adicionarMensagem("consultaProfessoresForm:msg", FacesMessage.SEVERITY_INFO, "Atualizado com sucesso !",
					null);
			enviarEmailProfessorCadatrado = false;
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			navegacaoController.consultaProfessor();
			professorEmail.assuntoEmail(professor);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("atualizarProfessorForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	public void excluirProfessor() {
		try {
			Professor professor = professorSelecionado;			
			professorServiceImpl.getRemoverProfessor(professor);
			professorEmail.assuntoEmailProfessorExcluido(professor);
			consultar();
			FacesMessages.adicionarMensagem("consultaProfessoresForm:msg", FacesMessage.SEVERITY_INFO, "Removido com sucesso !",
					null);
		} catch (Exception e) {
			FacesMessages.adicionarMensagem("consultaProfessoresForm:msg", FacesMessage.SEVERITY_ERROR, "Não é possível excluir, pois tem entidades relacionada !",
					"Não é possível excluir !");
		}
	}
	
	public void carregar() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = params.get("codigo");
		professor = professorServiceImpl.getConsultarDetalhe(Integer.parseInt(id));
	}
	
	public String selecionarProfessor(Professor professor) {
		this.professor = professor;
		return "/professor/detalhe/DetalheProfessor?faces-redirect=true&codigo="+this.professor.getId();
	}
	
	public String selecionarTelefone(Professor professor) {
		this.professor = professor;
		SessionUtil.setParam("telefone", professor);
		return "/telefone/CadastroTelefone?faces-redirect=true&codigo="+this.professor.getId();
	}
	
	public String selecionarConsultaTelefone(Professor professor) {
		this.professor = professor;
		SessionUtil.setParam("telefone", professor);
		return "/telefone/ConsultaTelefone?faces-redirect=true&codigo="+this.professor.getId();
	}
	
	public String editarProfessor(Professor professor) {
		this.professor = professor;
		return "/professor/atualizar/AtualizarProfessor?faces-redirect=true&codigo="+this.professor.getId();
	}
	
	public void limparFormulario() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		HtmlInputText htmlInputTextNome = (HtmlInputText) uiViewRoot.findComponent("cadastroProfessorForm:tabView:nome");
		HtmlInputText htmlInputTextCpf = (HtmlInputText) uiViewRoot.findComponent("cadastroProfessorForm:tabView:cpf");
		HtmlSelectOneMenu htmlSelectOneMenuSexo = (HtmlSelectOneMenu) uiViewRoot.findComponent("cadastroProfessorForm:tabView:sexo");
		HtmlInputText htmlInputTextDataNascimento = (HtmlInputText) uiViewRoot.findComponent("cadastroProfessorForm:tabView:data");
		HtmlInputText htmlInputTextAnoAdmissao = (HtmlInputText) uiViewRoot.findComponent("cadastroProfessorForm:tabView:ano");
		HtmlSelectOneMenu htmlSelectOneMenuPerfil = (HtmlSelectOneMenu) uiViewRoot.findComponent("cadastroProfessorForm:tabView:perfil");
		HtmlInputText htmlInputTextHora = (HtmlInputText) uiViewRoot.findComponent("cadastroProfessorForm:tabView:hora");
		HtmlInputText htmlInputTextNomeGraduacao = (HtmlInputText) uiViewRoot.findComponent("cadastroProfessorForm:tabView:nomeGraduacao");
		HtmlInputText htmlInputTextDataFormacao = (HtmlInputText) uiViewRoot.findComponent("cadastroProfessorForm:tabView:dataFormacao");
		htmlInputTextNome.setSubmittedValue("");
		htmlInputTextCpf.setSubmittedValue("");
		htmlSelectOneMenuSexo.setSubmittedValue("");
		htmlInputTextDataNascimento.setSubmittedValue("");
		htmlInputTextAnoAdmissao.setSubmittedValue("");
		htmlSelectOneMenuPerfil.setSubmittedValue("");
		htmlInputTextHora.setSubmittedValue("");
		htmlInputTextNomeGraduacao.setSubmittedValue("");
		htmlInputTextDataFormacao.setSubmittedValue("");
		professor = new Professor();
	}
	
	public TipoPerfil[] getPerfil() {
		return TipoPerfil.values();
	}
	
	public Sexo[] getSexo() {
		return Sexo.values();
	}
	
}
