package br.com.gabrielferreira.entidade;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
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
@ToString(exclude= {"pessoa"})
@EqualsAndHashCode
@Entity
@Table(name = "tab_telefone")
public class Telefone implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotBlank(message = "Campo do ddd não pode ser vazio")
	@Size(max = 3,message = "Não pode passa de 3 caracteres")
	private String ddd;
	
	@NotBlank(message = "Campo do número não pode ser vazio")
	@Size(max = 10,message = "Não pode passa de 10 caracteres")
	private String numero;

	@Valid
	@NotNull(message = "É necessário informar a pessoa")
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;
	
	@Transient
	private boolean editarTelefone;
	

}
