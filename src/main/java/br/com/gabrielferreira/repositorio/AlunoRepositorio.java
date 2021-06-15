package br.com.gabrielferreira.repositorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.gabrielferreira.entidade.Aluno;
import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.entidade.dto.relatorio.AlunoRelDTO;
import br.com.gabrielferreira.search.AlunoSearch;

public class AlunoRepositorio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public AlunoRepositorio() {}
	
	public List<Aluno> filtrar(AlunoSearch alunoSearch){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Aluno> criteriaQuery = criteriaBuilder.createQuery(Aluno.class);
		Root<Aluno> root = criteriaQuery.from(Aluno.class);
		
		List<Predicate> predicatesFiltros = criarFiltroAluno(alunoSearch, criteriaBuilder, root);
		
		criteriaQuery.where((Predicate[])predicatesFiltros.toArray(new Predicate[0]));
		
		TypedQuery<Aluno> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Aluno> alunos = typedQuery.getResultList();
		return alunos;
	}
	
	private List<Predicate> criarFiltroAluno(AlunoSearch alunoSearch, CriteriaBuilder criteriaBuilder
			, Root<Aluno> root){
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(StringUtils.isNotBlank(alunoSearch.getNomeCompleto())) {
			Predicate predicateNome = criteriaBuilder.like(root.get("nomeCompleto"), "%" + alunoSearch.getNomeCompleto() + "%");
			predicates.add(predicateNome);
		}
		
		if(StringUtils.isNotBlank(alunoSearch.getCpf())) {
			Predicate predicateNumero = criteriaBuilder.like(root.get("cpf"), "%" + alunoSearch.getCpf() + "%");
			predicates.add(predicateNumero);
		}
		
		if(alunoSearch.getSexo() != null) {
			Predicate predicateNumero = criteriaBuilder.equal(root.get("sexo"), alunoSearch.getSexo());
			predicates.add(predicateNumero);
		}
		
		if(StringUtils.isNotBlank(alunoSearch.getNumeroMatricula())) {
			Predicate predicateNumero = criteriaBuilder.like(root.get("numeroMatricula"), "%" + alunoSearch.getNumeroMatricula() + "%");
			predicates.add(predicateNumero);
		}
		
		if(alunoSearch.getTurma() != null) {
			
			Join<Aluno, Turma> turmaJoin = root.join("turma");
			turmaJoin.alias("t");
			
			Predicate predicateNomeTurma = criteriaBuilder.equal(turmaJoin.get("nomeTurma"), alunoSearch.getTurma().getNomeTurma());
			predicates.add(predicateNomeTurma);
		}
		
		return predicates;
	}

	public List<Aluno> verificarAlunoId(Integer id){
		String jpql = "SELECT a FROM Aluno a where a.id = :id";
		TypedQuery<Aluno> query = entityManager.createQuery(jpql,Aluno.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public boolean verificarNumero(String numero){
		String jpql = "SELECT a FROM Aluno a where a.numeroMatricula = :numero";
		TypedQuery<Aluno> query = entityManager.createQuery(jpql,Aluno.class);
		query.setParameter("numero", numero);
		
		List<Aluno> alunos = query.getResultList();
		
		return !alunos.isEmpty()?true:false;
	}
	
	public boolean verificarNumeroAtualizado(String numero, Integer id){
		String jpql = "SELECT a FROM Aluno a where a.numeroMatricula = :numero and a.id <> :id";
		TypedQuery<Aluno> query = entityManager.createQuery(jpql,Aluno.class);
		query.setParameter("numero", numero);
		query.setParameter("id", id);
		
		List<Aluno> alunos = query.getResultList();
		
		return !alunos.isEmpty()?true:false;
	}
	
	@SuppressWarnings("unchecked")
	public List<AlunoRelDTO> listarAlunosRelatorio(String nome, String sexo, String turma){
		Query query = entityManager.createNamedQuery("Aluno.findListarAlunos");
		query.setParameter("nome", "%"+nome+"%");
		query.setParameter("sexo", "%"+sexo+"%");
		query.setParameter("turma", "%"+turma+"%");
		
		
		List<Object[]> objs = query.getResultList();
		List<AlunoRelDTO> alunos = new ArrayList<AlunoRelDTO>();
		
		for (Object[] o : objs) {
			 Object[] aux = o;
			 AlunoRelDTO alunoRelDTO = new AlunoRelDTO();
			 alunoRelDTO.setId((Integer) aux[0]);
		     alunoRelDTO.setNome((String)aux[1]);
		     alunoRelDTO.setCpf((String)aux[2]);
		     alunoRelDTO.setData((Date) aux[3]);
		     alunoRelDTO.setSexo((String) aux[4]);
		     alunoRelDTO.setMatricula((String) aux[5]);
		     alunoRelDTO.setTurma((String) aux[6]);
		     alunos.add(alunoRelDTO);
		}
	
		return alunos;
		
	}
}
