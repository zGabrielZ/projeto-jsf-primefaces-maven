package br.com.gabrielferreira.service;
import java.util.List;

import br.com.gabrielferreira.entidade.Usuario;
import br.com.gabrielferreira.exception.RegraDeNegocioException;

public interface UsuarioService {

	void getInserirUsuario(Usuario usuario) throws RegraDeNegocioException;
	
	void getVerificarEmail(Usuario usuario) throws RegraDeNegocioException;
	
	List<Usuario> getVerificarEmailAndSenha(String email, String senha);

}
