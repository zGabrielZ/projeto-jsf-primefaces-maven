package br.com.gabrielferreira.email;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import br.com.gabrielferreira.controller.AlunoController;
import br.com.gabrielferreira.email.config.EmailConfig;
import br.com.gabrielferreira.entidade.Aluno;
import br.com.gabrielferreira.entidade.Email;

public class AlunoEmail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmailConfig emailConfig;
	
	@Inject
	private AlunoController alunoController;
	
	public void assuntoEmail(Aluno aluno, String emailDestino) {
		
		Email email = new Email();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		Locale locale = new Locale("pt","BR");
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",locale);
		
		if(alunoController.isEnviarEmailAlunoCadatrado()) {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Aluno cadastrado com sucesso !</h1> <br/> ");
			email.setTitulo("Aluno Cadastrado");
		} else {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Aluno atualizado com sucesso !</h1> <br/> ");
			email.setTitulo("Aluno Atualizado");
		}
		
		stringBuilder.append("<p style=\"text-align: center;\" > Dados do aluno abaixo:</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome : " + aluno.getNomeCompleto() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > CPF : " + aluno.getCpf() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Sexo : " + aluno.getSexo().getDescricao() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Data de nascimento : " + sdf.format(aluno.getDataNascimento()) +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Número da matrícula : " + aluno.getNumeroMatricula() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Turma : " + aluno.getTurma().getNomeTurma() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Turno : " + aluno.getTurma().getTurno().getDescricao() +"</p> <br/> ");
		
		email.getDestinatarios().add(emailDestino);
		email.setAssunto(stringBuilder.toString());
		emailConfig.enviarEmail(email);
	}
	
	public void assuntoEmailAlunoExcluido(Aluno aluno, String emailDestino) {
		
		Email email = new Email();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Aluno deletado com sucesso !</h1> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome : " + aluno.getNomeCompleto() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > CPF : " + aluno.getCpf() +"</p> <br/> ");
		
		email.setTitulo("Aluno Deletado");
		email.getDestinatarios().add(emailDestino);
		email.setAssunto(stringBuilder.toString());
		emailConfig.enviarEmail(email);
	}

}
