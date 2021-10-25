package br.com.gabrielferreira.service;

import java.util.List;

import br.com.gabrielferreira.entidade.ItensTurma;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
public interface ItensTurmasService {
	
	void getInserirItensTurmas(ItensTurma itensTurma) throws RegraDeNegocioException;
	
	void getRemoverItensTurmas(ItensTurma itensTurma);
		
	List<ItensTurma> getListarItensTurmas();
	
	void getVerificarProfessorAndTurmaRepetido(ItensTurma itensTurma) throws RegraDeNegocioException;
	
	ItensTurma getDetalhe(Integer id);
}
