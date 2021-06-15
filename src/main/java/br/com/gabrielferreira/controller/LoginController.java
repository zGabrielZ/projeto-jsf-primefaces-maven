package br.com.gabrielferreira.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielferreira.entidade.Usuario;
import br.com.gabrielferreira.service.impl.UsuarioServiceImpl;
import br.com.gabrielferreira.utils.FacesMessages;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
public class LoginController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioServiceImpl usuarioServiceImpl;

	@Getter
	@Setter
	private Usuario usuario;
	
	@Getter
	@Setter
	private boolean passou;
	
	@PostConstruct
	public void inicializar() {
		usuario = new Usuario();
	}
	
	public String logar() {
		List<Usuario> usuarios = usuarioServiceImpl.getVerificarEmailAndSenha(usuario.getEmail(),usuario.getSenha());
		if(!usuarios.isEmpty()) {
			passou = true;
			usuario.setNomeCompleto(usuarios.get(0).getNomeCompleto());
			return "/HomePrincipal.xhtml?faces-redirect=true";
		}
		FacesMessages.adicionarMensagem("loginUsuarioForm:msg", FacesMessage.SEVERITY_ERROR, "Usuário e/ou senha inválidos !",
				null);
		return null;
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login/LoginUsuario.xhtml?faces-redirect=true";
	}
	
}
