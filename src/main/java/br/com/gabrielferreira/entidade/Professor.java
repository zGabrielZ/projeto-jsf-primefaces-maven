package br.com.gabrielferreira.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.gabrielferreira.entidade.enums.Sexo;
import br.com.gabrielferreira.entidade.enums.TipoPerfil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= {"itensTurmas"})
@Entity
@Table(name = "tab_professor")
public class Professor extends Pessoa{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Valid
	@NotNull(message = "É necessário informar a graduação")
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
	@JoinColumn(name = "graduacao_id")
	private Graduacao graduacao;
	
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo da data de admissão não pode ser vazio ou digitado incorretamente")
	@Column(name = "data_admissao")
	private Date anoAdmissao;
	
	@NotNull(message = "Campo da quantidade de horas não pode ser vazia")
	@Min(value = 1,message = "Mínimo é de uma hora")
	@Max(value = 12,message="Máximo é de doze quatro horas")
	private Integer qtdHoras;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_perfil")
	private TipoPerfil tipoPerfil;
	
	@OneToMany(mappedBy="professor", fetch = FetchType.LAZY)
	private List<ItensTurma> itensTurmas = new ArrayList<>();
	
	public Professor() {}

	public Professor(Integer id, String nomeCompleto, String cpf, Sexo sexo, Date dataNascimento,
			List<Telefone> telefones, boolean editarPessoa, Graduacao graduacao, Date anoAdmissao, Integer qtdHoras,
			List<ItensTurma> itensTurmas) {
		super(id, nomeCompleto, cpf, sexo, dataNascimento, telefones);
		this.graduacao = graduacao;
		this.anoAdmissao = anoAdmissao;
		this.qtdHoras = qtdHoras;
		this.itensTurmas = itensTurmas;
	}
	
	
	

}
