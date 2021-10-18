package br.com.gabrielferreira.service.impl;

import java.io.Serializable;

import br.com.gabrielferreira.entidade.Usuario;
import br.com.gabrielferreira.exception.RegraDeNegocioException;

import javax.inject.Inject;
import br.com.gabrielferreira.repositorio.UsuarioRepositorio;
import br.com.gabrielferreira.service.UsuarioService;

public class UsuarioServiceImpl implements Serializable,UsuarioService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepositorio usuarioRepositorio;
	
	@Override
	public void getInserirUsuario(Usuario usuario) throws RegraDeNegocioException {
		getVerificarEmail(usuario);
		usuarioRepositorio.inserir(usuario);
	}

	@Override
	public void getVerificarEmail(Usuario usuario) throws RegraDeNegocioException {	
		if(usuarioRepositorio.verificarEmail(usuario.getEmail())) {
			throw new RegraDeNegocioException("Não é possível cadastrar esse e-mail, pois já está cadastrado");
		}
	}

	@Override
	public Usuario getVerificarEmailAndSenha(String email, String senha) {
		Usuario usuario = usuarioRepositorio.verificarEmailAndSenha(email, senha);
		return usuario;
	}
	
	

}
