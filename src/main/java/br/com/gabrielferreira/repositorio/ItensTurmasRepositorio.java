package br.com.gabrielferreira.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.gabrielferreira.entidade.ItensTurma;
import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.entidade.Professor;
import br.com.gabrielferreira.entidade.Turma;
public class ItensTurmasRepositorio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	@Inject
	private PessoaRepositorio pessoaRepositorio;

	@Inject
	private TurmaRepositorio turmaRepositorio;
	
	public ItensTurmasRepositorio() {}
	
	public void inserir(ItensTurma itensTurma) {
		Pessoa pessoa = pessoaRepositorio.procurarPorId(itensTurma.getProfessor().getId());
		Turma turma = turmaRepositorio.procurarPorId(itensTurma.getTurma().getId());
		
		Professor professor = (Professor) pessoa;
		professor.getItensTurmas().add(itensTurma);
		turma.getItensTurmas().add(itensTurma);
		
		entityManager.persist(itensTurma);
	}
	
	public ItensTurma procurarPorId(Integer id) {
		return entityManager.find(ItensTurma.class, id);
	}
	
	public void remover(ItensTurma itensTurma) {
		itensTurma = procurarPorId(itensTurma.getId());
		entityManager.remove(itensTurma);
	}
	
	public List<ItensTurma> listarItensTurmas(){
		String jpql = "SELECT i FROM ItensTurma i join i.professor p join i.turma t";
		TypedQuery<ItensTurma> query = entityManager.createQuery(jpql,ItensTurma.class);
		return query.getResultList();
	}
	
	public List<ItensTurma> listarItensTurmasByProfessor(Integer idProfessor){
		String jpql = "SELECT i FROM ItensTurma i join i.professor p where p.id = :idProfessor";
		TypedQuery<ItensTurma> query = entityManager.createQuery(jpql,ItensTurma.class);
		query.setParameter("idProfessor", idProfessor);
		return query.getResultList();
	}
	
	public List<ItensTurma> listarItensTurmasByTurma(Integer idTurma){
		String jpql = "SELECT i FROM ItensTurma i join i.turma t where t.id = :idTurma";
		TypedQuery<ItensTurma> query = entityManager.createQuery(jpql,ItensTurma.class);
		query.setParameter("idTurma", idTurma);
		return query.getResultList();
	}
	
	public boolean verificarProfessorAndTurmaRepetido(Integer idProfessor, Integer idTurma){
		String jpql = "SELECT i FROM ItensTurma i join i.professor p join i.turma t where p.id = :idProfessor and i.id = :idTurma";
		TypedQuery<ItensTurma> query = entityManager.createQuery(jpql,ItensTurma.class);
		query.setParameter("idProfessor", idProfessor);
		query.setParameter("idTurma", idTurma);
		
		List<ItensTurma> itensTurmas = query.getResultList();
		
		return !itensTurmas.isEmpty()?true:false;
	}
	
}
