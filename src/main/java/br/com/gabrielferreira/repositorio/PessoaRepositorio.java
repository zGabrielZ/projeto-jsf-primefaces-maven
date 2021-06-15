package br.com.gabrielferreira.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.gabrielferreira.entidade.Pessoa;

public class PessoaRepositorio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public PessoaRepositorio() {}
		
	public boolean verificarCpf(String cpf){
		String jpql = "SELECT p FROM Pessoa p where p.cpf = :cpf";
		TypedQuery<Pessoa> query = entityManager.createQuery(jpql,Pessoa.class);
		query.setParameter("cpf", cpf);
		
		List<Pessoa> pessoas = query.getResultList();
		
		return !pessoas.isEmpty()?true:false;
	}
	
	public void inserir(Pessoa pessoa) {
		entityManager.persist(pessoa);
	}
	
	public void remover(Pessoa pessoa) {
		pessoa = procurarPorId(pessoa.getId());
		entityManager.remove(pessoa);
	}
	
	public void atualizar(Pessoa pessoa) {
		entityManager.merge(pessoa);
	}
	
	public Pessoa procurarPorId(Integer id) {
		return entityManager.find(Pessoa.class, id);
	}

}
