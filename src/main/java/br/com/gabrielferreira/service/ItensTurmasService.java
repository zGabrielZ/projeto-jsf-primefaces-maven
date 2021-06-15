package br.com.gabrielferreira.service;

import java.util.List;

import br.com.gabrielferreira.entidade.ItensTurma;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
public interface ItensTurmasService {
	
	void getInserirItensTurmas(ItensTurma itensTurma) throws RegraDeNegocioException;
	
	void getRemoverItensTurmas(ItensTurma itensTurma);
		
	List<ItensTurma> getListarItensTurmas();
	
	List<ItensTurma> getListarItensTurmasByProfessor(Integer idProfessor);
	
	List<ItensTurma> getListarItensTurmasByTurma(Integer idTurma);
	
	void getVerificarProfessorAndTurmaRepetido(ItensTurma itensTurma) throws RegraDeNegocioException;
	
}
