package br.com.gabrielferreira.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import br.com.gabrielferreira.entidade.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude= {"telefones"})
@Entity
@Table(name = "tab_pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Campo do nome não pode ser vazio")
	@Size(max = 150,message = "Não pode passa de 150 caracteres")
	@Column(name = "nome_completo")
	private String nomeCompleto;
	
	@CPF(message = "Cpf inválido")
	@NotBlank(message = "Campo do cpf não pode ser vazio")
	private String cpf;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "É necessário informar o sexo")
	private Sexo sexo;
	
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo da data de nascimento não pode ser vazio ou digitado incorretamente")
	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@OneToMany(mappedBy = "pessoa",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	private List<Telefone> telefones = new ArrayList<Telefone>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
