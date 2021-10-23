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
	private boolean enviarEmailTelefoneCadastrado;
	
	@Getter
	@Setter
	private boolean enviarEmailTelefoneExcluido;
	
	@PostConstruct
	public void iniciar() {
		telefone = new Telefone();
		verificarParametro();
	}
	
	public void verificarParametro() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idCodigoConsultaTelefone = params.get("codigoConsultaTelefone");
		String idCodigoCadastroTelefone = params.get("codigoCadastroTelefone");
		String idCodigoAtualizarTelefone = params.get("codigoAtualizarTelefone");
		String idCodigoAtualizarTelefonePessoa = params.get("codigoAtualizarTelefonePessoa");
		
		if(idCodigoConsultaTelefone != null) {
			telefones = telefoneServiceImpl.getTelefonesByIdPessoa(Integer.parseInt(idCodigoConsultaTelefone));
			pessoa = pessoaServiceImpl.getConsultarDetalhe(Integer.parseInt(idCodigoConsultaTelefone));
		}
		
		if(idCodigoCadastroTelefone != null) {
			pessoa = pessoaServiceImpl.getConsultarDetalhe(Integer.parseInt(idCodigoCadastroTelefone));
		}
		
		if(idCodigoAtualizarTelefone != null && idCodigoAtualizarTelefonePessoa != null) {
			telefone = telefoneServiceImpl.getByTelefone(Integer.parseInt(idCodigoAtualizarTelefone));
			pessoa = pessoaServiceImpl.getConsultarDetalhe(Integer.parseInt(idCodigoAtualizarTelefonePessoa));
		}
		
	}
	
	public boolean isVerificarAlunoOuProfessor() {
		if(pessoa instanceof Aluno) {
			return true;
		}
		return false;
	}
	
	public void novo() {
		telefone = new Telefone();
	}

	public void cadastrar() throws RegraDeNegocioException  {
		if(telefone.getId() == null) {
			inserir();
		} else {
			atualizar();
		}	
	}
	
	private void inserir() {
		try {
			telefoneServiceImpl.getInserirTelefone(telefone,pessoa);
			FacesMessages.adicionarMensagem("cadastroTelefoneForm", FacesMessage.SEVERITY_INFO, "Telefone cadastrado com sucesso !",
					null);
			novo();
			enviarEmailTelefoneCadastrado = true;
			//telefoneEmail.assuntoEmail(telefone);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroTelefoneForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	private void atualizar() {
		try {
			telefoneServiceImpl.getAtualizarTelefone(telefone);
			FacesMessages.adicionarMensagem("cadastroTelefoneForm", FacesMessage.SEVERITY_INFO, "Telefone atualizado com sucesso !",
					null);
			novo();
			enviarEmailTelefoneCadastrado = false;
			//telefoneEmail.assuntoEmail(telefone);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroTelefoneForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	private void consultarTelefoneAposExcluir(Telefone telefone) {
		Integer idPessoa = telefone.getPessoa().getId();
		pessoa = pessoaServiceImpl.getConsultarDetalhe(idPessoa);
		telefones = telefoneServiceImpl.getTelefonesByIdPessoa(idPessoa);
	}
	
	public void excluirTelefone() {
		Telefone telefone = telefoneSelecionado;			
		telefoneServiceImpl.getRemoverTelefone(telefone);
		enviarEmailTelefoneExcluido = true;
		//telefoneEmail.assuntoEmail(telefone);
		consultarTelefoneAposExcluir(telefone);
		FacesMessages.adicionarMensagem("consultaTelefonesForm:msg", FacesMessage.SEVERITY_INFO, "Removido com sucesso !",
				null);
	}
	
}
