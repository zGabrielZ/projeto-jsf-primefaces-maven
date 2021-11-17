package br.com.gabrielferreira.controller;
import java.io.Serializable;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielferreira.email.UsuarioEmail;
import br.com.gabrielferreira.entidade.Usuario;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.service.impl.UsuarioServiceImpl;
import br.com.gabrielferreira.utils.FacesMessages;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class UsuarioController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioEmail usuarioEmail;
	
	@Inject
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@Inject
	private NavegacaoController navegacaoController;
	
	private Usuario usuario;
	
	private boolean codigoEnviado;
	
	private Integer codigo;
	
	private String senha;
	
	private Usuario buscarUsuarioEmail;
	
	@PostConstruct
	public void inicializar() {
		codigoEnviado = false;
		usuario = new Usuario();
		buscarUsuarioEmail = new Usuario();
	}
	
	public void cadastrar() {
		if(usuario.getId() == null) {
			inserirUsuario();
		}	
	}
	
	public void enviarCodigo() {
		try {
			usuarioServiceImpl.getVerificarEmailTrocarSenha(buscarUsuarioEmail);
			codigoEnviado = true;
			
			String email = buscarUsuarioEmail.getEmail();
			Integer codigoGerado = gerarCodigoAleatorio();
			
			buscarUsuarioEmail.setCodigoGerado(codigoGerado);
			usuarioEmail.assuntoEmail(email, codigoGerado);
			
			FacesMessages.adicionarMensagem("updateUsuarioForm:msg", FacesMessage.SEVERITY_INFO, "Código enviado com sucesso !",
					null);
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("updateUsuarioForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	private Integer gerarCodigoAleatorio() {
		Random random = new Random();
		Integer valor = random.nextInt(50) + 1; // 1 até 50
		return valor;
	}
	
	public void atualizarSenha() {
		if(codigo.equals(buscarUsuarioEmail.getCodigoGerado())) {
			Usuario usuario = usuarioServiceImpl.getUsuario(buscarUsuarioEmail.getEmail());
			usuario.setSenha(senha);
			
			usuarioServiceImpl.getAtualizarSenhaUsuario(usuario);
			
			FacesMessages.adicionarMensagem("loginUsuarioForm:msg", FacesMessage.SEVERITY_INFO, "Senha atualizada sucesso !",
					null);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			navegacaoController.login();
		} else {
			FacesMessages.adicionarMensagem("updateUsuarioForm:msg", FacesMessage.SEVERITY_ERROR, "Código incorreto !",
					null);
		}
	}
	
	public void novo() {
		usuario = new Usuario();
		codigoEnviado = false;
	}
	
	private void inserirUsuario() {
		try {
			usuarioServiceImpl.getInserirUsuario(usuario);
			FacesMessages.adicionarMensagem("cadastroUsuarioForm:msg", FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso !",
					null);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroUsuarioForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
}
