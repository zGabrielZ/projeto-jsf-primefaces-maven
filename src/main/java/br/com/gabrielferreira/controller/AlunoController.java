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

import org.primefaces.model.LazyDataModel;

import br.com.gabrielferreira.email.AlunoEmail;
import br.com.gabrielferreira.entidade.Aluno;
import br.com.gabrielferreira.entidade.enums.Sexo;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.search.AlunoSearch;
import br.com.gabrielferreira.service.impl.AlunoServiceImpl;
import br.com.gabrielferreira.utils.FacesMessages;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class AlunoController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AlunoServiceImpl alunoServiceImpl;
	
	@Inject
	private NavegacaoController navegacaoController;
	
	@Inject
	private AlunoEmail alunoEmail;
	
	@Getter
	@Setter
	private List<Aluno> alunos;
	
	@Getter
	@Setter
	private AlunoSearch alunoSearchParam;
	
	@Getter
	@Setter
	private Aluno aluno;
	
	@Getter
	@Setter
	private Aluno alunoSelecionado;
	
	@Getter
	@Setter
	private boolean enviarEmailAlunoCadatrado;
	
	@Getter
	@Setter
	private LazyDataModel<Aluno> dataModelAluno;
	
	@PostConstruct
	public void inicializar() {
		alunoSearchParam = new AlunoSearch();
		aluno = new Aluno();
	}
	
	public void consultar() {
		alunos = alunoServiceImpl.getFiltar(alunoSearchParam);
	}
	
	public void cadastrar() {
		
		if(aluno.getId() == null) {
			inserirAluno(aluno);
			aluno = new Aluno();
		} else {
			atualizarAluno(aluno);
		}
			
	}
	
	private void inserirAluno(Aluno aluno) {
		try {
			alunoServiceImpl.getInserirAluno(aluno);
			FacesMessages.adicionarMensagem("cadastroAlunoForm:msg", FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso !",
					null);
			enviarEmailAlunoCadatrado = true;
			alunoEmail.assuntoEmail(aluno);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroAlunoForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	private void atualizarAluno(Aluno aluno) {
		try {
			alunoServiceImpl.getAtualizarAluno(aluno);
			FacesMessages.adicionarMensagem("consultaAlunosForm:msg", FacesMessage.SEVERITY_INFO, "Atualizado com sucesso !",
					null);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			navegacaoController.consultaAluno();
			enviarEmailAlunoCadatrado = false;
			alunoEmail.assuntoEmail(aluno);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("atualizarAlunoForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	public void excluirAluno() {
		try {
			Aluno aluno = alunoSelecionado;			
			alunoServiceImpl.getRemoverAluno(aluno);
			alunoEmail.assuntoEmailAlunoExcluido(aluno);
			consultar();
			FacesMessages.adicionarMensagem("consultaAlunosForm:msg", FacesMessage.SEVERITY_INFO, "Removido com sucesso !",
					null);
		} catch (Exception e) {
			FacesMessages.adicionarMensagem("consultaAlunosForm:msg", FacesMessage.SEVERITY_ERROR, "Não é possível excluir, pois tem entidades relacionada !",
					"Não é possível excluir !");
		}
	}
	
	public void carregar() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = params.get("codigo");
		aluno = alunoServiceImpl.getConsultarDetalhe(Integer.parseInt(id));
	}
	
	public String selecionarAluno(Aluno aluno) {
		this.aluno = aluno;
		return "/aluno/detalhe/DetalheAluno?faces-redirect=true&codigo="+this.aluno.getId();
	}
	
	public String selecionarTelefone(Aluno aluno) {
		this.aluno = aluno;
		//SessionUtil.setParam("telefone", aluno);
		return "/telefone/CadastroTelefone?faces-redirect=true&codigo="+this.aluno.getId();
	}
	
	public String selecionarConsultaTelefone(Aluno aluno) {
		this.aluno = aluno;
		//SessionUtil.setParam("telefone", aluno);
		return "/telefone/ConsultaTelefone?faces-redirect=true&codigo="+this.aluno.getId();
	}
	
	public String editarAluno(Aluno aluno) {
		this.aluno = aluno;
		return "/aluno/atualizar/AtualizarAluno?faces-redirect=true&codigo="+this.aluno.getId();
	}
	
	public void limparFormulario() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		HtmlInputText htmlInputTextNome = (HtmlInputText) uiViewRoot.findComponent("cadastroAlunoForm:nome");
		HtmlInputText htmlInputTextCpf = (HtmlInputText) uiViewRoot.findComponent("cadastroAlunoForm:cpf");
		HtmlSelectOneMenu htmlSelectOneMenuSexo = (HtmlSelectOneMenu) uiViewRoot.findComponent("cadastroAlunoForm:sexo");
		HtmlInputText htmlInputTextDataNascimento = (HtmlInputText) uiViewRoot.findComponent("cadastroAlunoForm:data");
		HtmlInputText htmlInputTextNumero = (HtmlInputText) uiViewRoot.findComponent("cadastroAlunoForm:numero");
		HtmlSelectOneMenu htmlSelectOneMenuTurma = (HtmlSelectOneMenu) uiViewRoot.findComponent("cadastroAlunoForm:turma");
		htmlInputTextNome.setSubmittedValue("");
		htmlInputTextCpf.setSubmittedValue("");
		htmlSelectOneMenuSexo.setSubmittedValue("");
		htmlInputTextDataNascimento.setSubmittedValue("");
		htmlInputTextNumero.setSubmittedValue("");
		htmlSelectOneMenuTurma.setSubmittedValue("");
		aluno = new Aluno();
	}
	
	public void limparPesquisa() {
		alunoSearchParam = new AlunoSearch();
	}
	
	public Sexo[] getSexo() {
		return Sexo.values();
	}
	
	
	
}
