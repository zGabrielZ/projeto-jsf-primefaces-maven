package br.com.gabrielferreira.email;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.gabrielferreira.controller.TurmaController;
import br.com.gabrielferreira.email.config.EmailConfig;
import br.com.gabrielferreira.entidade.Email;
import br.com.gabrielferreira.entidade.Turma;

public class TurmaEmail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TurmaController turmaController;
	
	@Inject
	private EmailConfig emailConfig;
	
	public void assuntoEmail(Turma turma, String emailDestinatario) {
		
		Email email = new Email();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if(turmaController.isEnviarEmailTurmaCadatrado()) {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Turma cadastrada com sucesso !</h1> <br/> ");
			email.setTitulo("Turma Cadastrada");
		} else {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Turma atualizada com sucesso !</h1> <br/> ");
			email.setTitulo("Turma Atualizada");
		}
		
		stringBuilder.append("<p style=\"text-align: center;\" > Dados da turma abaixo:</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome : " + turma.getNomeTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Número : " + turma.getNumeroTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Turno : " + turma.getTurno().getDescricao() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Vagas : " + turma.getVagas() +"</p> <br/> ");
		
		email.setAssunto(stringBuilder.toString());
		email.getDestinatarios().add(emailDestinatario);
		emailConfig.enviarEmail(email);
	}
	
	public void assuntoEmailTurmaExcluido(Turma turma, String emailDestinatario) {
		
		Email email = new Email();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Turma deletada com sucesso !</h1> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome : " + turma.getNomeTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Número : " + turma.getNumeroTurma() +"</p> <br/> ");
		
		email.setTitulo("Turma Deletada");
		email.setAssunto(stringBuilder.toString());
		email.getDestinatarios().add(emailDestinatario);
		emailConfig.enviarEmail(email);
	}

}
