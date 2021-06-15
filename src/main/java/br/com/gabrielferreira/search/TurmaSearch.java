package br.com.gabrielferreira.search;

import java.io.Serializable;

import br.com.gabrielferreira.entidade.enums.Turno;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TurmaSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String numero;
	private Turno turno;
}
