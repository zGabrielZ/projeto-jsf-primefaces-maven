package br.com.gabrielferreira.search;

import java.io.Serializable;

import br.com.gabrielferreira.entidade.enums.Sexo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomeCompleto;
	private String cpf;
	private Sexo sexo;
	
	

}
