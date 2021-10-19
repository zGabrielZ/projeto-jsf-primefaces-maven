package br.com.gabrielferreira.repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.entidade.dto.TurmaDTO;
import br.com.gabrielferreira.entidade.dto.relatorio.TurmaRelDTO;
import br.com.gabrielferreira.entidade.enums.Turno;
import br.com.gabrielferreira.repositorio.generico.RepositorioGenerico;
import br.com.gabrielferreira.search.TurmaSearch;

public class TurmaRepositorio extends RepositorioGenerico<Turma>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<TurmaDTO> filtrar(TurmaSearch turmaSearch){
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Turma> criteriaQuery = criteriaBuilder.createQuery(Turma.class);
		Root<Turma> root = criteriaQuery.from(Turma.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(StringUtils.isNotBlank(turmaSearch.getNome())) {
			Predicate predicateNome = criteriaBuilder.like(root.get("nomeTurma"), "%" + turmaSearch.getNome() + "%");
			predicates.add(predicateNome);
		}
		
		if(StringUtils.isNotBlank(turmaSearch.getNumero())) {
			Predicate predicateNumero = criteriaBuilder.like(root.get("numeroTurma"), "%" + turmaSearch.getNumero() + "%");
			predicates.add(predicateNumero);
		}
		
		if(turmaSearch.getTurno() != null) {
			Predicate predicateNumero = criteriaBuilder.equal(root.get("turno"), turmaSearch.getTurno());
			predicates.add(predicateNumero);
		}
		
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
		criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[0]));
		
		TypedQuery<Turma> typedQuery = getEntityManager().createQuery(criteriaQuery);

		List<Turma> turmas = typedQuery.getResultList();
		
		return turmas.stream().map(x -> new TurmaDTO(x)).collect(Collectors.toList());
	}

	public boolean verificarNomeAndTurno(String nome,Turno turno){
		String jpql = "SELECT t FROM Turma t where t.nomeTurma = :nome and t.turno = :turno";
		TypedQuery<Turma> query = getEntityManager().createQuery(jpql,Turma.class);
		query.setParameter("nome", nome);
		query.setParameter("turno", turno);
		
		List<Turma> turmas = query.getResultList();
		
		return !turmas.isEmpty() ? true : false;
	}
	
	public boolean verificarNomeAndTurnoAtualizado(String nome,Turno turno,Integer id){
		String jpql = "SELECT t FROM Turma t where t.nomeTurma = :nome and t.turno = :turno and t.id <> :id ";
		TypedQuery<Turma> query = getEntityManager().createQuery(jpql,Turma.class);
		query.setParameter("nome", nome);
		query.setParameter("turno", turno);
		query.setParameter("id", id);
		
		List<Turma> turmas = query.getResultList();
		
		return !turmas.isEmpty()?true:false;
	}
		
	public boolean verificarNumeroAtualizado(String numero,Integer id){
		String jpql = "SELECT t FROM Turma t where t.numeroTurma = :numero and t.id <> :id";
		TypedQuery<Turma> query = getEntityManager().createQuery(jpql,Turma.class);
		query.setParameter("numero", numero);
		query.setParameter("id", id);
		List<Turma> verificar = query.getResultList();
		
		return !verificar.isEmpty() ? true : false;
	}
	
	public boolean verificarNumero(String numero){
		String jpql = "SELECT t FROM Turma t where t.numeroTurma = :numero";
		TypedQuery<Turma> query = getEntityManager().createQuery(jpql,Turma.class);
		query.setParameter("numero", numero);
		List<Turma> turmas = query.getResultList();
		return !turmas.isEmpty() ? true : false;
	}
	
	public List<Turma> verificarTurmaId(Integer id){
		String jpql = "SELECT t FROM Turma t where t.id = :id";
		TypedQuery<Turma> query = getEntityManager().createQuery(jpql,Turma.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public Turma getTurmaDetalhes(Integer idTurma){
		String jpql = "SELECT t FROM Turma t left join t.alunos a left join t.itensTurmas i where t.id = :idTurma";
		TypedQuery<Turma> query = getEntityManager().createQuery(jpql,Turma.class);
		query.setParameter("idTurma", idTurma);
		Turma turma = verificarNulo(query);
		return turma;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TurmaRelDTO> listarTurmasRelatorio(String nome, String turno){
		Query query = getEntityManager().createNamedQuery("Turma.findListarTurmas");
		query.setParameter("nome", "%"+nome+"%");
		query.setParameter("turno", "%"+turno+"%");
		
		
		List<Object[]> objs = query.getResultList();
		List<TurmaRelDTO> turmas = new ArrayList<TurmaRelDTO>();
		
		for (Object[] o : objs) {
			 Object[] aux = o;
			 TurmaRelDTO turmaDTO = new TurmaRelDTO();
		     turmaDTO.setNome((String)aux[0]);
		     turmaDTO.setNumero((String)aux[1]);
		     turmaDTO.setTurno((String) aux[2]);
		     turmaDTO.setVagas((Integer) aux[3]);
		     turmas.add(turmaDTO);
		}
	
		return turmas;
		
	}
	
}
