package br.com.gabrielferreira.email;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.gabrielferreira.controller.TelefoneController;
import br.com.gabrielferreira.email.config.EmailConfig;
import br.com.gabrielferreira.entidade.Email;
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
	
	public void assuntoEmail(Telefone telefone, String emailDestino) {
		
		Email email = new Email();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if(telefoneController.isEnviarEmailTelefoneCadastrado()) {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Telefone cadastrado com sucesso !</h1> <br/> ");
			email.setTitulo("Telefone Cadastrado");
		} else if (telefoneController.isEnviarEmailTelefoneExcluido()){
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Telefone deletado com sucesso !</h1> <br/> ");
			email.setTitulo("Telefone Deletado");
		} else {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Telefone atualizado com sucesso !</h1> <br/> ");
			email.setTitulo("Telefone Atualizado");
		}
		
		stringBuilder.append("<p style=\"text-align: center;\" > Dados do telefone abaixo:</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > DDD : " + telefone.getDdd() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Número : " + telefone.getNumero() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome da pessoa : " + telefone.getPessoa().getNomeCompleto() +"</p> <br/> ");
	
		email.getDestinatarios().add(emailDestino);
		email.setAssunto(stringBuilder.toString());
		emailConfig.enviarEmail(email);
	}

}
