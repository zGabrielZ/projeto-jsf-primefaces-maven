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
import javax.servlet.http.HttpSession;

import br.com.gabrielferreira.email.AlunoEmail;
import br.com.gabrielferreira.entidade.Aluno;
import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.entidade.Usuario;
import br.com.gabrielferreira.entidade.enums.Sexo;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.search.AlunoSearch;
import br.com.gabrielferreira.service.impl.AlunoServiceImpl;
import br.com.gabrielferreira.service.impl.TurmaServiceImpl;
import br.com.gabrielferreira.utils.FacesMessages;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class AlunoController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AlunoServiceImpl alunoServiceImpl;
	
	@Inject
	private TurmaServiceImpl turmaServiceImpl;
	
	@Inject
	private AlunoEmail alunoEmail;
	
	private List<Aluno> alunos;
	
	private AlunoSearch alunoSearchParam;
	
	private Aluno aluno;
	
	private Aluno alunoDetalhe;
	
	private Aluno alunoSelecionado;
	
	private boolean enviarEmailAlunoCadatrado;
	
	@PostConstruct
	public void inicializar() {
		alunoSearchParam = new AlunoSearch();
		aluno = new Aluno();
		verificarParametro();
	}
	
	private void verificarParametro() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idDetalheAluno = params.get("codigoDetalheAluno");
		String idAtualizarAluno = params.get("codigoAtualizarAluno");
		
		if(idDetalheAluno != null) {
			alunoDetalhe = alunoServiceImpl.getConsultarDetalhe(Integer.parseInt(idDetalheAluno));
		}
		
		if(idAtualizarAluno != null) {
			aluno = alunoServiceImpl.getConsultarDetalhe(Integer.parseInt(idAtualizarAluno));
		}
	}
	
	public void consultar() {
		alunos = alunoServiceImpl.getFiltar(alunoSearchParam);
	}
	
	public void novo() {
		aluno = new Aluno();
	}
	
	public void cadastrar() {
		if(aluno.getId() == null) {
			inserirAluno();
		} else {
			atualizarAluno();
		}
	}
	
	private void inserirAluno() {
		try {
			alunoServiceImpl.getInserirAluno(aluno);
			FacesMessages.adicionarMensagem("cadastroAlunoForm:msg", FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso !",
					null);
			enviarEmailAlunoCadatrado = true;
			alunoEmail.assuntoEmail(aluno,obterEmailUsuarioLogado());
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroAlunoForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	private void atualizarAluno() {
		try {
			alunoServiceImpl.getAtualizarAluno(aluno);
			FacesMessages.adicionarMensagem("cadastroAlunoForm:msg", FacesMessage.SEVERITY_INFO, "Atualizado com sucesso !",
					null);
			enviarEmailAlunoCadatrado = false;
			alunoEmail.assuntoEmail(aluno,obterEmailUsuarioLogado());
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroAlunoForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	public void excluirAluno() {
		Aluno aluno = alunoSelecionado;			
		Aluno alunoDados = getDados(aluno.getId());
		alunoServiceImpl.getRemoverAluno(aluno);
		alunoEmail.assuntoEmailAlunoExcluido(alunoDados,obterEmailUsuarioLogado());
		consultar();
		FacesMessages.adicionarMensagem("consultaAlunosForm:msg", FacesMessage.SEVERITY_INFO, "Removido com sucesso !",
				null);
	}
	
	private Aluno getDados(Integer id) {
		Aluno aluno = new Aluno();
		aluno = alunoServiceImpl.getConsultarDetalhe(id);
		return aluno;
	}
	
	public List<Turma> getTurmasConsultaAlunos(){
		return turmaServiceImpl.getListarTurmas();
	}
	
	public void limparPesquisa() {
		alunoSearchParam = new AlunoSearch();
		consultar();
	}
	
	public Sexo[] getSexo() {
		return Sexo.values();
	}
	
	private String obterEmailUsuarioLogado() {
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false);
		Usuario usuario = (Usuario) httpSession.getAttribute("usuarioLogado");
		return usuario.getEmail();
	}
	
	
	
}
