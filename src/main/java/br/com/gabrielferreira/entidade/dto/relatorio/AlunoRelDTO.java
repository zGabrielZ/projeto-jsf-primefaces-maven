package br.com.gabrielferreira.entidade.dto.relatorio;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlunoRelDTO extends PessoaRelDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String matricula;
	private String turma;

}
