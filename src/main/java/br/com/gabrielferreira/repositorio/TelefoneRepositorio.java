package br.com.gabrielferreira.repositorio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.gabrielferreira.entidade.Telefone;
import br.com.gabrielferreira.entidade.dto.relatorio.TelefoneRelDTO;
import br.com.gabrielferreira.repositorio.generico.RepositorioGenerico;

public class TelefoneRepositorio extends RepositorioGenerico<Telefone>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Telefone> findByTelefones(Integer id) {
		String jpql = "SELECT t FROM Pessoa p JOIN p.telefones t WHERE p.id = :id";
		TypedQuery<Telefone> query = getEntityManager().createQuery(jpql,Telefone.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public boolean verificarNumero(String numero) {
		String jpql = "SELECT t FROM Telefone t WHERE t.numero = :numero";
		TypedQuery<Telefone> query = getEntityManager().createQuery(jpql,Telefone.class);
		query.setParameter("numero", numero);
		List<Telefone> telefones = query.getResultList();
		return !telefones.isEmpty() ? true : false;
	}
	
	public boolean verificarNumeroAtualizado(String numero,Integer id) {
		String jpql = "SELECT t FROM Telefone t WHERE t.numero = :numero and t.id <> :id";
		TypedQuery<Telefone> query = getEntityManager().createQuery(jpql,Telefone.class);
		query.setParameter("numero", numero);
		query.setParameter("id", id);
		List<Telefone> telefones = query.getResultList();
		return !telefones.isEmpty() ? true : false;
	}
	
	@SuppressWarnings("unchecked")
	public List<TelefoneRelDTO> listarTelefonesRelatorio(Integer idPessoa){
		Query query = getEntityManager().createNamedQuery("Telefone.findListarTelefones");
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
