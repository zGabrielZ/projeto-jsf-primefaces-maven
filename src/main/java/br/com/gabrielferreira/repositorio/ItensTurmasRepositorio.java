package br.com.gabrielferreira.repositorio;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.com.gabrielferreira.entidade.ItensTurma;
import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.entidade.Professor;
import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.repositorio.generico.RepositorioGenerico;
public class ItensTurmasRepositorio extends RepositorioGenerico<ItensTurma>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaRepositorio pessoaRepositorio;

	@Inject
	private TurmaRepositorio turmaRepositorio;
	
	public ItensTurmasRepositorio() {}
	
	@Override
	public void inserir(ItensTurma itensTurma) {
		getEntityManager().getTransaction().begin();
		Pessoa pessoa = pessoaRepositorio.pesquisarPorId(itensTurma.getProfessor().getId(), Pessoa.class);
		Turma turma = turmaRepositorio.pesquisarPorId(itensTurma.getTurma().getId(), Turma.class);
		
		Professor professor = (Professor) pessoa;
		professor.getItensTurmas().add(itensTurma);
		turma.getItensTurmas().add(itensTurma);
		
		getEntityManager().persist(itensTurma);
		getEntityManager().getTransaction().commit();
	}
	
	public List<ItensTurma> listarItensTurmas(){
		String jpql = "SELECT i FROM ItensTurma i join i.professor p join i.turma t order by i.id desc";
		TypedQuery<ItensTurma> query = getEntityManager().createQuery(jpql,ItensTurma.class);
		return query.getResultList();
	}
	
	public boolean verificarProfessorAndTurmaRepetido(Integer idProfessor, Integer idTurma){
		String jpql = "SELECT i FROM ItensTurma i join i.professor p join i.turma t where p.id = :idProfessor and i.id = :idTurma";
		TypedQuery<ItensTurma> query = getEntityManager().createQuery(jpql,ItensTurma.class);
		query.setParameter("idProfessor", idProfessor);
		query.setParameter("idTurma", idTurma);
		
		List<ItensTurma> itensTurmas = query.getResultList();
		
		return !itensTurmas.isEmpty()?true:false;
	}
	
}
