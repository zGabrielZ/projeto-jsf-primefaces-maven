package br.com.gabrielferreira.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import br.com.gabrielferreira.entidade.enums.Turno;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude= {"alunos","itensTurmas"})
@EqualsAndHashCode
@Entity
@Table(name = "tab_turma")
public class Turma implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotBlank(message = "Campo da turma não pode ser vazia")
	@Size(max = 150,message = "Não pode passa de 150 caracteres")
	@Column(name = "nome_completo")
	private String nomeTurma;
	
	@NotBlank(message = "Campo do número da turma não pode ser vazia")
	@Size(max = 5,message = "Não pode passa de 5 caracteres")
	@Column(name = "numero_turma")
	private String numeroTurma;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "É necessário informar o turno")
	private Turno turno;
	
	@Min(value = 1, message = "Não pode ser menor do que 1 vaga")
	@Max(value = 150, message = "Não pode ultrapassar de 150 vagas")
	@NotNull(message = "Campo da vaga não pode ser vazia")
	private Integer vagas;
	
	@OneToMany(mappedBy = "turma",fetch = FetchType.LAZY)
	private List<Aluno> alunos = new ArrayList<Aluno>();
	
	@OneToMany(mappedBy="turma", fetch = FetchType.LAZY)
	private List<ItensTurma> itensTurmas = new ArrayList<>();
	
	
}
