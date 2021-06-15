package br.com.gabrielferreira.email;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.gabrielferreira.controller.TurmaController;
import br.com.gabrielferreira.email.EmailConfig;
import br.com.gabrielferreira.entidade.Turma;

public class TurmaEmail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmailConfig emailConfig;
	
	@Inject
	private TurmaController turmaController;
	
	public void assuntoEmail(Turma turma) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if(turmaController.isEnviarEmailTurmaCadatrado()) {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Turma cadastrada com sucesso !</h1> <br/> ");
			emailConfig.setAssunto("Turma Cadastrada");
		} else {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Turma atualizada com sucesso !</h1> <br/> ");
			emailConfig.setAssunto("Turma Atualizada");
		}
		
		stringBuilder.append("<p style=\"text-align: center;\" > Dados da turma abaixo:</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome : " + turma.getNomeTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Número : " + turma.getNumeroTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Turno : " + turma.getTurno().getDescricao() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Vagas : " + turma.getVagas() +"</p> <br/> ");
		
		emailConfig.setTexto(stringBuilder.toString());
		emailConfig.enviarEmail(true);
	}
	
	public void assuntoEmailTurmaExcluido(Turma turma) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Turma deletada com sucesso !</h1> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome : " + turma.getNomeTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Número : " + turma.getNumeroTurma() +"</p> <br/> ");
		
		emailConfig.setAssunto("Turma Deletada");
		emailConfig.setTexto(stringBuilder.toString());
		emailConfig.enviarEmail(true);
	}

}
