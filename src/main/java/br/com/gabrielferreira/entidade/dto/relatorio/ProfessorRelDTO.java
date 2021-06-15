package br.com.gabrielferreira.entidade.dto.relatorio;


import java.util.Date;
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
public class ProfessorRelDTO extends PessoaRelDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomeGraduacao;
	private Date dataFormacao;
	private Date anoAdmissao;
	private Integer qtdHoras;
	private String tipoPerfil;

}
