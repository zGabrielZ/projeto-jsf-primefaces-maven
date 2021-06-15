package br.com.gabrielferreira.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.gabrielferreira.entidade.ItensTurma;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.repositorio.ItensTurmasRepositorio;
import br.com.gabrielferreira.service.ItensTurmasService;
import br.com.gabrielferreira.utils.Transacional;

public class ItensTurmasServiceImpl implements Serializable,ItensTurmasService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ItensTurmasRepositorio itensTurmasRepositorio;

	@Transacional
	@Override
	public void getInserirItensTurmas(ItensTurma itensTurma) throws RegraDeNegocioException {
		getVerificarProfessorAndTurmaRepetido(itensTurma);
		itensTurmasRepositorio.inserir(itensTurma);
	}

	@Transacional
	@Override
	public void getRemoverItensTurmas(ItensTurma itensTurma) {
		itensTurmasRepositorio.remover(itensTurma);
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
	public List<ItensTurma> getListarItensTurmasByProfessor(Integer idProfessor) {
		List<ItensTurma> itensTurmas = itensTurmasRepositorio.listarItensTurmasByProfessor(idProfessor);
		return itensTurmas;
	}

	@Override
	public List<ItensTurma> getListarItensTurmasByTurma(Integer idTurma) {
		List<ItensTurma> itensTurmas = itensTurmasRepositorio.listarItensTurmasByTurma(idTurma);
		return itensTurmas;
	}

	

}
