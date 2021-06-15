package br.com.gabrielferreira.search;

import br.com.gabrielferreira.entidade.Turma;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoSearch extends PessoaSearch{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String numeroMatricula;
	private Turma turma;

	

}
