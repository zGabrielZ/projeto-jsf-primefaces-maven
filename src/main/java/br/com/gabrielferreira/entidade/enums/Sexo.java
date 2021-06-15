package br.com.gabrielferreira.entidade.enums;

public enum Sexo {

	MASCULINO(1,"Masculino"),
	FEMININO(2,"Feminino");
	
	private int codigo;
	private String descricao;
	
	Sexo(int codigo,String descricao){
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
