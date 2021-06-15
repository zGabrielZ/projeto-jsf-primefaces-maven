package br.com.gabrielferreira.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.codec.binary.Base64;

import br.com.gabrielferreira.entidade.Usuario;

public class UsuarioRepositorio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public UsuarioRepositorio() {}
	
	public void inserir(Usuario usuario) {
		entityManager.persist(usuario);
	}
	
	public boolean verificarEmail(String email){
		String jpql = "SELECT u FROM Usuario u where u.email = :email";
		TypedQuery<Usuario> query = entityManager.createQuery(jpql,Usuario.class);
		query.setParameter("email", email);
		
		List<Usuario> usuarios = query.getResultList();
		
		return !usuarios.isEmpty()?true:false;
	}
	
	public List<Usuario> verificarEmailAndSenha(String email, String senha){
		String senhaTransformada = transformarSenha(senha);
		String jpql = "SELECT u FROM Usuario u where u.email = :email and u.senha = :senhaTransformada";
		TypedQuery<Usuario> query = entityManager.createQuery(jpql,Usuario.class);
		query.setParameter("email", email);
		query.setParameter("senhaTransformada", senhaTransformada);
		
		List<Usuario> usuarios = query.getResultList();
		return usuarios;
	}
	
	private String transformarSenha(String senha) {
		Base64 base64 = new Base64();
		String senhaSerializada = base64.encodeAsString(senha.getBytes());
		return senhaSerializada;
	}
	
	
}
