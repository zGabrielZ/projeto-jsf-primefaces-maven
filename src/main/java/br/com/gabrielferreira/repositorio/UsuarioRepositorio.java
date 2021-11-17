package br.com.gabrielferreira.repositorio;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.codec.binary.Base64;

import br.com.gabrielferreira.entidade.Usuario;
import br.com.gabrielferreira.repositorio.generico.RepositorioGenerico;

public class UsuarioRepositorio extends RepositorioGenerico<Usuario>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public boolean verificarEmail(String email){
		String jpql = "SELECT u FROM Usuario u where u.email = :email";
		TypedQuery<Usuario> query = entityManager.createQuery(jpql,Usuario.class);
		query.setParameter("email", email);
		
		List<Usuario> usuarios = query.getResultList();
		
		return !usuarios.isEmpty() ? true : false;
	}
	
	public Usuario getUsuarioByEmail(String email) {
		String jpql = "SELECT u FROM Usuario u where u.email = :email";
		TypedQuery<Usuario> query = entityManager.createQuery(jpql,Usuario.class);
		query.setParameter("email", email);
		Usuario usuario = verificarNulo(query);
		return usuario;
	}
	
	public Usuario verificarEmailAndSenha(String email, String senha){
		String senhaTransformada = transformarSenha(senha);
		String jpql = "SELECT u FROM Usuario u where u.email = :email and u.senha = :senhaTransformada";
		TypedQuery<Usuario> query = entityManager.createQuery(jpql,Usuario.class);
		query.setParameter("email", email);
		query.setParameter("senhaTransformada", senhaTransformada);
		
		Usuario usuario = verificarNulo(query);
		return usuario;
	}
	
	public String transformarSenha(String senha) {
		Base64 base64 = new Base64();
		String senhaSerializada = base64.encodeAsString(senha.getBytes());
		return senhaSerializada;
	}
	
	
}
