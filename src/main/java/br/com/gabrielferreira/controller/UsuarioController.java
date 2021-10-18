package br.com.gabrielferreira.controller;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.codec.binary.Base64;
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
	private UsuarioServiceImpl usuarioServiceImpl;
	
	private Usuario usuario;
	
	@PostConstruct
	public void inicializar() {
		usuario = new Usuario();
	}
	
	public void cadastrar() {
		if(usuario.getId() == null) {
			inserirUsuario();
			novo();
		}	
	}
	
	public void novo() {
		usuario = new Usuario();
	}
	
	private void inserirUsuario() {
		try {
			String senhaCodificada = transformarSenha(usuario.getSenha());
			usuario.setSenha(senhaCodificada);
			usuarioServiceImpl.getInserirUsuario(usuario);
			FacesMessages.adicionarMensagem("cadastroUsuarioForm:msg", FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso !",
					null);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("cadastroUsuarioForm:msg", FacesMessage.SEVERITY_ERROR, e.getMessage(),
					null);
		}
	}
	
	private String transformarSenha(String senha) {
		Base64 base64 = new Base64();
		String senhaSerializada = base64.encodeAsString(senha.getBytes());
		return senhaSerializada;
	}
}
