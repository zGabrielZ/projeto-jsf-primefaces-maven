package br.com.gabrielferreira.repositorio;
import java.util.List;
import javax.persistence.TypedQuery;

import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.repositorio.generico.RepositorioGenerico;

public class PessoaRepositorio extends RepositorioGenerico<Pessoa>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public boolean verificarCpf(String cpf){
		String jpql = "SELECT p FROM Pessoa p where p.cpf = :cpf";
		TypedQuery<Pessoa> query = getEntityManager().createQuery(jpql,Pessoa.class);
		query.setParameter("cpf", cpf);
		
		List<Pessoa> pessoas = query.getResultList();
		
		return !pessoas.isEmpty() ? true : false;
	}
	
	public boolean verificarCpfAtualizado(String cpf,Integer id){
		String jpql = "SELECT p FROM Pessoa p where p.cpf = :cpf and p.id <> :id";
		TypedQuery<Pessoa> query = getEntityManager().createQuery(jpql,Pessoa.class);
		query.setParameter("cpf", cpf);
		query.setParameter("id", id);
		List<Pessoa> verificar = query.getResultList();
		
		return !verificar.isEmpty() ? true : false;
	}

}
