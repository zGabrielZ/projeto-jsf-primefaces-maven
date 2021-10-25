package br.com.gabrielferreira.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.gabrielferreira.entidade.ItensTurma;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.repositorio.ItensTurmasRepositorio;
import br.com.gabrielferreira.service.ItensTurmasService;

public class ItensTurmasServiceImpl implements Serializable,ItensTurmasService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ItensTurmasRepositorio itensTurmasRepositorio;

	@Override
	public void getInserirItensTurmas(ItensTurma itensTurma) throws RegraDeNegocioException {
		getVerificarProfessorAndTurmaRepetido(itensTurma);
		itensTurmasRepositorio.inserir(itensTurma);
	}

	@Override
	public void getRemoverItensTurmas(ItensTurma itensTurma) {
		itensTurmasRepositorio.deletarPorId(ItensTurma.class, itensTurma.getId());
	}

	@Override
	public List<ItensTurma> getListarItensTurmas() {
		List<ItensTurma> itensTurmas = itensTurmasRepositorio.listarItensTurmas();
		return itensTurmas;
	}

	@Override
	public void getVerificarProfessorAndTurmaRepetido(ItensTurma itensTurma) throws RegraDeNegocioException {
		if(itensTurmasRepositorio.verificarProfessorAndTurmaRepetido(itensTurma.getProfessor().getId(), itensTurma.getTurma().getId())) {
			throw new RegraDeNegocioException("Não é possível cadastrar com essa turma, pois já tem esse relacionamento");
		}
	}

	@Override
	public ItensTurma getDetalhe(Integer id) {
		return itensTurmasRepositorio.pesquisarPorId(id, ItensTurma.class);
	}

}
