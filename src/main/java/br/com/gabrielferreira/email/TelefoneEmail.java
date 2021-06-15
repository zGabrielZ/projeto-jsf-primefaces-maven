package br.com.gabrielferreira.email;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.gabrielferreira.controller.TelefoneController;
import br.com.gabrielferreira.email.EmailConfig;
import br.com.gabrielferreira.entidade.Telefone;

public class TelefoneEmail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmailConfig emailConfig;
	
	@Inject
	private TelefoneController telefoneController;
	
	public void assuntoEmail(Telefone telefone) {
		StringBuilder stringBuilder = new StringBuilder();
		
		if(telefoneController.isEnviarEmailTelefoneCadastrado()) {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Telefone cadastrado com sucesso !</h1> <br/> ");
			emailConfig.setAssunto("Telefone Cadastrado");
		} else if (telefoneController.isEnviarEmailTelefoneExcluido()){
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Telefone deletado com sucesso !</h1> <br/> ");
			emailConfig.setAssunto("Telefone Deletado");
		} else {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Telefone atualizado com sucesso !</h1> <br/> ");
			emailConfig.setAssunto("Telefone Atualizado");
		}
		
		stringBuilder.append("<p style=\"text-align: center;\" > Dados do telefone abaixo:</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > DDD : " + telefone.getDdd() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Número : " + telefone.getNumero() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome da pessoa : " + telefone.getPessoa().getNomeCompleto() +"</p> <br/> ");
	
		emailConfig.setTexto(stringBuilder.toString());
		emailConfig.enviarEmail(true);
	}

}
