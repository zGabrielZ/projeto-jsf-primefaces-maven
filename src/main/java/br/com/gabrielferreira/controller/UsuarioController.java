package br.com.gabrielferreira.controller;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
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
public class UsuarioController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@Getter
	@Setter
	private Usuario usuario;
	
	@PostConstruct
	public void inicializar() {
		usuario = new Usuario();
	}
	
	public void cadastrar() {
		if(usuario.getId() == null) {
			inserirUsuario(usuario);
			usuario = new Usuario();
		}	
	}
	
	private void inserirUsuario(Usuario usuario) {
		try {
			String senhaCodificada = transformarSenha(usuario.getSenha());
			usuario.setSenha(senhaCodificada);
			usuarioServiceImpl.getInserirUsuario(usuario);
			FacesMessages.adicionarMensagem("cadastroUsuarioForm:msg", FacesMessage.SEVERITY_INFO, "Cadastrado com sucesso !",
					null);
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
	
	public void limparFormulario() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		HtmlInputText htmlInputTextNome = (HtmlInputText) uiViewRoot.findComponent("cadastroUsuarioForm:nome");
		HtmlInputText htmlInputTextEmail = (HtmlInputText) uiViewRoot.findComponent("cadastroUsuarioForm:email");
		HtmlInputText htmlInputTextSenha = (HtmlInputText) uiViewRoot.findComponent("cadastroUsuarioForm:senha");
		htmlInputTextNome.setSubmittedValue("");
		htmlInputTextEmail.setSubmittedValue("");
		htmlInputTextSenha.setSubmittedValue("");
		usuario = new Usuario();
	}
}
