package br.com.gabrielferreira.service;

import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.exception.RegraDeNegocioException;

public interface PessoaService {

	void getVerificarCpf(String cpf) throws RegraDeNegocioException;
	
	void getVerificarCpfAtualizar(Pessoa pessoa) throws RegraDeNegocioException;
	
	Pessoa getConsultarDetalhe(Integer id);
}
