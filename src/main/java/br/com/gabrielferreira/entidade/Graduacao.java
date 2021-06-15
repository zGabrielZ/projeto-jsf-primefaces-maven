package br.com.gabrielferreira.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

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
@ToString(exclude= {"professores"})
@EqualsAndHashCode
@Entity
@Table(name = "tab_graduacao")
public class Graduacao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotBlank(message = "Campo do nome da graduação não pode ser vazio")
	@Size(max = 150,message = "Não pode passa de 150 caracteres")
	@Column(name = "nome_graduacao")
	private String nomeGraduacao;
	
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo da data de formação não pode ser vazio ou digitado incorretamente")
	@Column(name = "data_formacao")
	private Date dataFormacao;

	@OneToMany(mappedBy = "graduacao")
	private List<Professor> professores = new ArrayList<Professor>();
	
	
	
}
