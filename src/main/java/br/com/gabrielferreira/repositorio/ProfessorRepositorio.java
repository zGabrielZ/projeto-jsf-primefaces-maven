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
import br.com.gabrielferreira.entidade.Graduacao;
import br.com.gabrielferreira.entidade.Professor;
import br.com.gabrielferreira.entidade.dto.relatorio.ProfessorRelDTO;
import br.com.gabrielferreira.search.ProfessorSearch;


public class ProfessorRepositorio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public ProfessorRepositorio() {}
	
	public List<Professor> filtrar(ProfessorSearch professorSearch){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Professor> criteriaQuery = criteriaBuilder.createQuery(Professor.class);
		Root<Professor> root = criteriaQuery.from(Professor.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(StringUtils.isNotBlank(professorSearch.getNomeCompleto())) {
			Predicate predicateNome = criteriaBuilder.like(root.get("nomeCompleto"), "%" + professorSearch.getNomeCompleto() + "%");
			predicates.add(predicateNome);
		}
		
		if(StringUtils.isNotBlank(professorSearch.getCpf())) {
			Predicate predicateNumero = criteriaBuilder.like(root.get("cpf"), "%" + professorSearch.getCpf() + "%");
			predicates.add(predicateNumero);
		}
		
		if(professorSearch.getSexo() != null) {
			Predicate predicateNumero = criteriaBuilder.equal(root.get("sexo"), professorSearch.getSexo());
			predicates.add(predicateNumero);
		}
		
		if(StringUtils.isNotBlank(professorSearch.getNomeGraduacao())) {
			
			Join<Professor, Graduacao> professorJoin = root.join("graduacao");
			professorJoin.alias("g");
			
			Predicate predicateNomeGraduacao = criteriaBuilder.like(professorJoin.get("nomeGraduacao"),"%" + professorSearch.getNomeGraduacao() + "%");
			predicates.add(predicateNomeGraduacao);
		}
		
		criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[0]));
		
		TypedQuery<Professor> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Professor> professores = typedQuery.getResultList();
		
		return professores;
	}
	
	public List<Professor> verificarProfessorId(Integer id){
		String jpql = "SELECT p FROM Professor p where p.id = :id";
		TypedQuery<Professor> query = entityManager.createQuery(jpql,Professor.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Professor> listarProfessores(){
		String jpql = "SELECT p FROM Professor p";
		TypedQuery<Professor> query = entityManager.createQuery(jpql,Professor.class);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProfessorRelDTO> listarProfessoresRelatorio(String nome, String sexo, String graduacao){
		Query query = entityManager.createNamedQuery("Professor.findListarProfessores");
		query.setParameter("nome", "%"+nome+"%");
		query.setParameter("sexo", "%"+sexo+"%");
		query.setParameter("graduacao", "%"+graduacao+"%");
		
		
		List<Object[]> objs = query.getResultList();
		List<ProfessorRelDTO> professores = new ArrayList<ProfessorRelDTO>();
		
		for (Object[] o : objs) {
			 Object[] aux = o;
			 ProfessorRelDTO professorRelDTO = new ProfessorRelDTO();
			 professorRelDTO.setId((Integer) aux[0]);
			 professorRelDTO.setCpf((String)aux[1]);
			 professorRelDTO.setData((Date)aux[2]);
			 professorRelDTO.setNome((String) aux[3]);
			 professorRelDTO.setSexo((String) aux[4]);
			 professorRelDTO.setNomeGraduacao((String) aux[5]);
			 professorRelDTO.setDataFormacao((Date) aux[6]);
			 professorRelDTO.setAnoAdmissao((Date) aux[7]);
			 professorRelDTO.setQtdHoras((Integer) aux[8]);
			 professorRelDTO.setTipoPerfil((String) aux[9]);
			 professores.add(professorRelDTO);
		}
	
		return professores;
		
	}
}
