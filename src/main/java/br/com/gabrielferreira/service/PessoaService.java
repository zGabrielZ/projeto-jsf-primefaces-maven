package br.com.gabrielferreira.service;

import br.com.gabrielferreira.entidade.Pessoa;

public interface PessoaService {

	boolean getVerificarCpf(String cpf);
	
	Pessoa getConsultarDetalhe(Integer id);
}
