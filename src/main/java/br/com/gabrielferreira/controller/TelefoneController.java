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

import br.com.gabrielferreira.email.TelefoneEmail;
import br.com.gabrielferreira.entidade.Aluno;
import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.entidade.Telefone;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.service.impl.AlunoServiceImpl;
import br.com.gabrielferreira.service.impl.PessoaServiceImpl;
import br.com.gabrielferreira.service.impl.TelefoneServiceImpl;
import br.com.gabrielferreira.utils.FacesMessages;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class TelefoneController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TelefoneServiceImpl telefoneServiceImpl;
	
	@Inject
	private PessoaServiceImpl pessoaServiceImpl;
	
	@Inject
	private AlunoServiceImpl alunoServiceImpl;
	
	@Inject
	private NavegacaoController navegacaoController;
	
	@Inject
	private TelefoneEmail telefoneEmail;
	
	@Getter
	@Setter
	private Pessoa pessoa;
	
	@Getter
	@Setter
	private Telefone telefone;
	
	@Getter
	@Setter
	private List<Telefone> telefones;
	
	@Getter
	@Setter
	private Telefone telefoneSelecionado;
	
	@Getter
	@Setter
	private boolean editar;
	
	@Getter
	@Setter
	private boolean enviarEmailTelefoneCadastrado;
	
	@Getter
	@Setter
	private boolean enviarEmailTelefoneExcluido;
	
	@PostConstruct
	public void inicializar() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = params.get("codigo");
		pessoa = pessoaServiceImpl.getConsultarDetalhe(Integer.parseInt(id));
		telefone = new Telefone();
		telefones = telefoneServiceImpl.getTelefonesByIdPessoa(pessoa.getId());
	}
	
	public boolean isVerificarAlunoOuProfessor() {
		if(pessoa instanceof Aluno) {
			if(alunoServiceImpl.getConsultarDetalhe(pessoa.getId()) != null) {
				return true;
			}
		}
		return false;
	}

	public void cadastrar() throws RegraDeNegocioException  {
		
		if(telefone.getId() == null) {
			inserirTelefone(telefone, pessoa);
			telefone = new Telefone();
		} else {
			atualizarTelefone(telefone);
		}	
	}
	
	private void atualizarTelefone(Telefone telefone) {
		try {
			if(telefone.isEditarTelefone()) {
				telefone.setEditarTelefone(false);
			}
			telefoneServiceImpl.getAtualizarTelefone(telefone);
			enviarEmailTelefoneCadastrado = false;
			telefoneEmail.assuntoEmail(telefone);
			if(isVerificarAlunoOuProfessor()) {
				FacesMessages.adicionarMensagem("consultaAlunosForm:msg", FacesMessage.SEVERITY_INFO, "Telefone atualizado com sucesso !",
						null);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				navegacaoController.consultaAluno();
			} else {
				FacesMessages.adicionarMensagem("consultaProfessoresForm:msg", FacesMessage.SEVERITY_INFO, "Telefone atualizado com sucesso !",
						null);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				navegacaoController.consultaProfessor();
			}
		} catch (RegraDeNegocioException e) {
			telefone.setEditarTelefone(true);
			FacesMessages.adicionarMensagem("consultaTelefonesForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}

	public void excluirTelefone() {
		try {
			Telefone telefone = telefoneSelecionado;			
			telefoneServiceImpl.getRemoverTelefone(telefone);
			enviarEmailTelefoneExcluido = true;
			telefoneEmail.assuntoEmail(telefone);
			if(isVerificarAlunoOuProfessor()) {
				FacesMessages.adicionarMensagem("consultaAlunosForm:msg", FacesMessage.SEVERITY_INFO, "Telefone removido com sucesso !",
						null);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				navegacaoController.consultaAluno();
			} else {
				FacesMessages.adicionarMensagem("consultaProfessoresForm:msg", FacesMessage.SEVERITY_INFO, "Telefone removido com sucesso !",
						null);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				navegacaoController.consultaProfessor();
			}
		} catch (Exception e) {
			FacesMessages.adicionarMensagem("consultaTelefonesForm:msg", FacesMessage.SEVERITY_ERROR, "Não é possível excluir, pois tem entidades relacionada !",
					"Não é possível excluir !");
		}
	}
	
	private void inserirTelefone(Telefone telefone, Pessoa pessoa) throws RegraDeNegocioException  {
		try {
			telefoneServiceImpl.getInserirTelefone(telefone, pessoa);
			enviarEmailTelefoneCadastrado = true;
			telefoneEmail.assuntoEmail(telefone);
			if(isVerificarAlunoOuProfessor()) {
				FacesMessages.adicionarMensagem("consultaAlunosForm:msg", FacesMessage.SEVERITY_INFO, "Telefone cadastrado com sucesso !",
						null);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				navegacaoController.consultaAluno();
			} else {
				FacesMessages.adicionarMensagem("consultaProfessoresForm:msg", FacesMessage.SEVERITY_INFO, "Telefone cadastrado com sucesso !",
						null);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				navegacaoController.consultaProfessor();
			}
			
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroTelefoneForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	public void cancelar() {
		editar = false;
		this.telefone.setEditarTelefone(editar);
	}
	
	public void editarTelefone(Telefone telefone) {
		this.telefone = telefone;
		editar = true;
		this.telefone.setEditarTelefone(editar);
	}
	
}
