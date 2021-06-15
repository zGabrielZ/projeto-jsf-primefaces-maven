package br.com.gabrielferreira.entidade.dto;

import java.io.Serializable;

import br.com.gabrielferreira.entidade.Turma;

public class TurmaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String numero;
	private String turno;
	private Integer vagas;
	
	public TurmaDTO() {}
	
	public TurmaDTO(Turma turma) {
		id = turma.getId();
		nome = turma.getNomeTurma();
		numero = turma.getNumeroTurma();
		turno = turma.getTurno().getDescricao();
		vagas = turma.getVagas();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Integer getVagas() {
		return vagas;
	}

	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
	
	
}
