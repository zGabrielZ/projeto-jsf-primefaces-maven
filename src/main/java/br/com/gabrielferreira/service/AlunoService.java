package br.com.gabrielferreira.service;

import java.util.List;

import br.com.gabrielferreira.entidade.Aluno;
import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.entidade.dto.relatorio.AlunoRelDTO;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.search.AlunoSearch;

public interface AlunoService {

	void getInserirAluno(Pessoa pessoa) throws RegraDeNegocioException;
	
	void getAtualizarAluno(Pessoa pessoa) throws RegraDeNegocioException;
	
	void getRemoverAluno(Pessoa pessoa);
	
	void getVerificarAtualizarOuInserir(Pessoa pessoa) throws RegraDeNegocioException;
	
	List<Aluno> getFiltar(AlunoSearch alunoSearch);
	
	Aluno getConsultarDetalhe(Integer id);
	
	boolean getVerificarNumero(String numero);
	
	void getVerificarNumeroAtualizado(Pessoa pessoa) throws RegraDeNegocioException;
	
	List<AlunoRelDTO> getListarAlunosRelatorio(String nome, String sexo, String turma);
	
	void getGerarRelatorioAluno(String nome, String sexo, String turma);

}
