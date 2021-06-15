package br.com.gabrielferreira.service;
import java.util.List;


import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.entidade.Professor;
import br.com.gabrielferreira.entidade.dto.relatorio.ProfessorRelDTO;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.search.ProfessorSearch;

public interface ProfessorService {

	void getInserirProfessor(Pessoa pessoa) throws RegraDeNegocioException;
	
	void getAtualizarProfessor(Pessoa pessoa) throws RegraDeNegocioException;
	
	void getRemoverProfessor(Pessoa pessoa);
	
	List<Professor> getFiltar(ProfessorSearch professorSearch);
	
	Professor getConsultarDetalhe(Integer id);
	
	List<Professor> getListar();
	
	List<ProfessorRelDTO> getListarProfessoresRelatorio(String nome, String sexo, String graduacao);
	
	void getGerarRelatorioProfessor(String nome, String sexo, String graduacao);
}
