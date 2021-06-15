package br.com.gabrielferreira.service;

import java.util.List;

import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.entidade.dto.TurmaDTO;
import br.com.gabrielferreira.entidade.dto.relatorio.TurmaRelDTO;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.search.TurmaSearch;

public interface TurmaService {
	
	void getInserirTurma(Turma turma) throws RegraDeNegocioException;
	
	void getAtualizarTurma(Turma turma) throws RegraDeNegocioException;
	
	void getRemoverTurma(Turma turma);
		
	List<TurmaDTO> getFiltar(TurmaSearch turmaSearch);
	
	List<Turma> getVerificarNumero(String numero);
	
	List<TurmaRelDTO> getListarTurmasRelatorio(String nome, String turno);
	
	void getGerarRelatorioTurma(String nome, String turno);
	
	void getVerificarNomeAndTurno(Turma turma) throws RegraDeNegocioException;
	
	void getVerificarNomeAndTurnoAtualizado(Turma turma) throws RegraDeNegocioException;
	
	void getVerificarNumeroAtualizado(Turma turma) throws RegraDeNegocioException;
	
	List<Turma> getListarTurmas();
	
	Turma getById(Integer id);
	
}
