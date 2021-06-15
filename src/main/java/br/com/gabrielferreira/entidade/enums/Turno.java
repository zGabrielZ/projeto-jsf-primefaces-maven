package br.com.gabrielferreira.entidade.enums;

public enum Turno {

	MANHA(1,"Manhã"),
	TARDE(2,"Tarde"),
	NOITE(3,"Noite");
	
	private int codigo;
	private String descricao;
	
	Turno(int codigo,String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
