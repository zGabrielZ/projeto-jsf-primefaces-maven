package br.com.gabrielferreira.entidade.enums;

public enum TipoPerfil {

	ESTAGIARIO(1,"Estagiário"),
	JUNIOR(2,"Júnior"),
	PLENO(3,"Pleno"),
	SENIOR(4,"Sênior"),
	CONSULTOR(5,"Consultor");
	
	private int codigo;
	private String descricao;
	
	TipoPerfil(int codigo,String descricao){
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
