package br.com.gabrielferreira.entidade;



import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.gabrielferreira.entidade.enums.Sexo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= {"turma"})
@Entity
@Table(name = "tab_aluno")
public class Aluno extends Pessoa{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Campo do número da mátricula não pode ser vazio")
	@Size(max = 11,message = "Não pode passa de 11 caracteres")
	@Column(name = "numero_matricula")
	private String numeroMatricula;
	
	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;
	
	public Aluno() {}

	public Aluno(Integer id, String nomeCompleto, String cpf, Sexo sexo, Date dataNascimento, List<Telefone> telefones,
			boolean editarPessoa, String numeroMatricula, Turma turma) {
		super(id, nomeCompleto, cpf, sexo, dataNascimento, telefones);
		this.numeroMatricula = numeroMatricula;
		this.turma = turma;
	}

	
	
	
	
	
	

}
