package br.com.gabrielferreira.service.impl;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
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
	public void getVerificarCpf(String cpf) throws RegraDeNegocioException {
		if(pessoaRepositorio.verificarCpf(cpf)) {
			throw new RegraDeNegocioException("Não é possível cadastrar este cpf, pois já está cadastrado !");
		}
	}
	
	@Override
	public void getVerificarCpfAtualizar(Pessoa pessoa) throws RegraDeNegocioException {
		if(pessoaRepositorio.verificarCpfAtualizado(pessoa.getCpf(), pessoa.getId())) {
			throw new RegraDeNegocioException("Não é possível atualizar este cpf, pois já está cadastrado !");
		}
	}	

	@Override
	public Pessoa getConsultarDetalhe(Integer id) {
		Pessoa pessoa = pessoaRepositorio.pesquisarPorId(id, Pessoa.class);
		return pessoa;
	}

}
