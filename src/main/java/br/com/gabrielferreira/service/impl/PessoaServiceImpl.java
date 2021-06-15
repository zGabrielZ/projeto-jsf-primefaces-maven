package br.com.gabrielferreira.service.impl;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.repositorio.PessoaRepositorio;
import br.com.gabrielferreira.service.PessoaService;

public class PessoaServiceImpl implements Serializable,PessoaService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaRepositorio pessoaRepositorio;

	@Override
	public boolean getVerificarCpf(String cpf) {
		boolean verificar = pessoaRepositorio.verificarCpf(cpf);
		return verificar;
	}

	@Override
	public Pessoa getConsultarDetalhe(Integer id) {
		Pessoa pessoa = pessoaRepositorio.procurarPorId(id);
		return pessoa;
	}	

}
