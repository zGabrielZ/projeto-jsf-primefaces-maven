package br.com.gabrielferreira.repositorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.entidade.Telefone;
import br.com.gabrielferreira.entidade.dto.relatorio.TelefoneRelDTO;

public class TelefoneRepositorio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public TelefoneRepositorio() {}
	
	public void inserir(Telefone telefone, Pessoa pessoa) {
		List<Telefone> telefones = findByTelefones(pessoa.getId());
		pessoa.setTelefones(telefones);
		telefone.setPessoa(pessoa);
		pessoa.getTelefones().add(telefone);
		entityManager.persist(telefone);
	}
	
	public Telefone procurarPorId(Integer id) {
		return entityManager.find(Telefone.class, id);
	}
	
	public void remover(Telefone telefone) {
		telefone = procurarPorId(telefone.getId());
		entityManager.remove(telefone);
	}
	
	public void atualizar(Telefone telefone) {
		entityManager.merge(telefone);
	}
	
	public List<Telefone> findByTelefones(Integer id) {
		String jpql = "SELECT t FROM Pessoa p JOIN p.telefones t WHERE p.id = :id";
		TypedQuery<Telefone> query = entityManager.createQuery(jpql,Telefone.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public boolean verificarNumero(String numero) {
		String jpql = "SELECT t FROM Telefone t WHERE t.numero = :numero";
		TypedQuery<Telefone> query = entityManager.createQuery(jpql,Telefone.class);
		query.setParameter("numero", numero);
		List<Telefone> telefones = query.getResultList();
		return !telefones.isEmpty() ? true : false;
	}
	
	public boolean verificarNumeroAtualizado(String numero,Integer id) {
		String jpql = "SELECT t FROM Telefone t WHERE t.numero = :numero and t.id <> :id";
		TypedQuery<Telefone> query = entityManager.createQuery(jpql,Telefone.class);
		query.setParameter("numero", numero);
		query.setParameter("id", id);
		List<Telefone> telefones = query.getResultList();
		return !telefones.isEmpty() ? true : false;
	}
	
	@SuppressWarnings("unchecked")
	public List<TelefoneRelDTO> listarTelefonesRelatorio(Integer idPessoa){
		Query query = entityManager.createNamedQuery("Telefone.findListarTelefones");
		query.setParameter("idPessoa", idPessoa);
		
		List<Object[]> objs = query.getResultList();
		List<TelefoneRelDTO> telefones = new ArrayList<TelefoneRelDTO>();
		
		for (Object[] o : objs) {
			 Object[] aux = o;
			 TelefoneRelDTO telefoneRelDTO = new TelefoneRelDTO();
			 telefoneRelDTO.setDdd((String)aux[0]);
			 telefoneRelDTO.setNumero((String)aux[1]);
			 telefones.add(telefoneRelDTO);
		}
	
		return telefones;
		
	}
	
	

}
