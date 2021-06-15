package br.com.gabrielferreira.service;


import java.util.List;

import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.entidade.Telefone;
import br.com.gabrielferreira.entidade.dto.relatorio.TelefoneRelDTO;
import br.com.gabrielferreira.exception.RegraDeNegocioException;

public interface TelefoneService {

	void getInserirTelefone(Telefone telefone, Pessoa pessoa) throws RegraDeNegocioException;
	
	void getAtualizarTelefone(Telefone telefone) throws RegraDeNegocioException;
	
	void getRemoverTelefone(Telefone telefone);
	
	List<Telefone> getTelefonesByIdPessoa(Integer id);
	
	List<TelefoneRelDTO> getByIdPessoa(Integer idPessoa);
	
	boolean getVerificarNumero(String numero);
	
	boolean getVerificarNumeroAtualizado(String numero, Integer id);
	
	void getVerificarNumero(Telefone telefone) throws RegraDeNegocioException;
	
	void getVerificarNumeroAtualizado(Telefone telefone) throws RegraDeNegocioException;
}
