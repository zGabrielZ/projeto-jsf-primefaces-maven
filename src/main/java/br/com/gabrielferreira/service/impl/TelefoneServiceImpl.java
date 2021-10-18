package br.com.gabrielferreira.service.impl;

import java.io.Serializable;
import java.util.List;

import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.entidade.Telefone;
import br.com.gabrielferreira.entidade.dto.relatorio.TelefoneRelDTO;
import br.com.gabrielferreira.exception.RegraDeNegocioException;

import javax.inject.Inject;

import br.com.gabrielferreira.repositorio.TelefoneRepositorio;
import br.com.gabrielferreira.service.TelefoneService;

public class TelefoneServiceImpl implements Serializable,TelefoneService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private TelefoneRepositorio telefoneRepositorio;
	
	@Override
	public void getInserirTelefone(Telefone telefone, Pessoa pessoa) throws RegraDeNegocioException {
		getVerificarNumero(telefone);
		telefoneRepositorio.inserir(telefone, pessoa);
	}
	
	@Override
	public void getAtualizarTelefone(Telefone telefone) throws RegraDeNegocioException {
		getVerificarNumeroAtualizado(telefone);
		telefoneRepositorio.atualizar(telefone);
	}
	
	@Override
	public void getRemoverTelefone(Telefone telefone) {
		telefoneRepositorio.remover(telefone);
	}

	@Override
	public List<Telefone> getTelefonesByIdPessoa(Integer id) {
		List<Telefone> telefones = telefoneRepositorio.findByTelefones(id);
		return telefones;
	}

	@Override
	public boolean getVerificarNumero(String numero) {
		boolean verificar = telefoneRepositorio.verificarNumero(numero);
		return verificar;
	}

	@Override
	public void getVerificarNumero(Telefone telefone) throws RegraDeNegocioException {
		if(getVerificarNumero(telefone.getNumero())) {
			throw new RegraDeNegocioException("Não é possível cadastrar este número, pois já está cadastrado !");
		}
	}

	@Override
	public boolean getVerificarNumeroAtualizado(String numero, Integer id) {
		boolean verificar = telefoneRepositorio.verificarNumeroAtualizado(numero, id);
		return verificar;
	}

	@Override
	public void getVerificarNumeroAtualizado(Telefone telefone) throws RegraDeNegocioException {
		if(getVerificarNumeroAtualizado(telefone.getNumero(), telefone.getId())) {
			throw new RegraDeNegocioException("Não é possível atualizar este número, pois já está cadastrado");
		}
	}

	@Override
	public List<TelefoneRelDTO> getByIdPessoa(Integer idPessoa) {
		List<TelefoneRelDTO> telefoneRelDTOs = telefoneRepositorio.listarTelefonesRelatorio(idPessoa);
		return telefoneRelDTOs;
	}
	
	

}
