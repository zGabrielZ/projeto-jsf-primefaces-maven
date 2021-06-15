package br.com.gabrielferreira.email;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.gabrielferreira.entidade.ItensTurma;

public class EventoEmail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmailConfig emailConfig;

	public void assuntoEmail(ItensTurma itensTurma) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Evento cadastrado com sucesso !</h1> <br/> ");
		stringBuilder.append("<h2 style=\"text-align: center;\" > Dados do professor abaixo:</h2> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome : " + itensTurma.getProfessor().getNomeCompleto() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > CPF : " + itensTurma.getProfessor().getCpf() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Sexo : " + itensTurma.getProfessor().getSexo().getDescricao() +"</p> <br/> ");
		
		stringBuilder.append("<h2 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Dados da turma abaixo: </h2><br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome : " + itensTurma.getTurma().getNomeTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Número : " + itensTurma.getTurma().getNumeroTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Turno : " + itensTurma.getTurma().getTurno().getDescricao() +"</p> <br/> ");
		
		
		emailConfig.setAssunto("Evento Cadastrado");
		emailConfig.setTexto(stringBuilder.toString());
		emailConfig.enviarEmail(true);
	}
	
	public void assuntoEmailItensTurmaExcluido(ItensTurma itensTurma) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Evento deletado com sucesso !</h1> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome da pessoa : " + itensTurma.getProfessor().getNomeCompleto() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > CPF da pessoa : " + itensTurma.getProfessor().getCpf() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome da turma : " + itensTurma.getTurma().getNomeTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Número da turma : " + itensTurma.getTurma().getNumeroTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Turno da turma : " + itensTurma.getTurma().getTurno().getDescricao() +"</p> <br/> ");
		
		emailConfig.setAssunto("Evento Deletado");
		emailConfig.setTexto(stringBuilder.toString());
		emailConfig.enviarEmail(true);
	}

}
