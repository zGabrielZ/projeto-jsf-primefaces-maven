package br.com.gabrielferreira.email;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import br.com.gabrielferreira.controller.ProfessorController;
import br.com.gabrielferreira.email.config.EmailConfig;
import br.com.gabrielferreira.entidade.Email;
import br.com.gabrielferreira.entidade.Professor;

public class ProfessorEmail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmailConfig emailConfig;
	
	@Inject
	private ProfessorController professorController;
	
	public void assuntoEmail(Professor professor, String emailDestino) {
		
		Email email = new Email();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		Locale locale = new Locale("pt","BR");
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",locale);
		
		if(professorController.isEnviarEmailProfessorCadatrado()) {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Professor cadastrado com sucesso !</h1> <br/> ");
			email.setTitulo("Professor Cadastrado");
		} else {
			stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" >Professor atualizado com sucesso !</h1> <br/> ");
			email.setTitulo("Professor Atualizado");
		}
		
		stringBuilder.append("<p style=\"text-align: center;\" > Dados do professor abaixo:</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome : " + professor.getNomeCompleto() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > CPF : " + professor.getCpf() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Sexo : " + professor.getSexo().getDescricao() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Data de nascimento : " + sdf.format(professor.getDataNascimento()) +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome da graduação : " + professor.getGraduacao().getNomeGraduacao() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Data da formação : " + sdf.format(professor.getGraduacao().getDataFormacao()) +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Ano da admissão : " + sdf.format(professor.getAnoAdmissao()) +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Quantidade de horas : " + professor.getQtdHoras() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Tipo de perfil : " + professor.getTipoPerfil().getDescricao() +"</p> <br/> ");
		
		email.getDestinatarios().add(emailDestino);
		email.setAssunto(stringBuilder.toString());
		emailConfig.enviarEmail(email);
	}
	
	public void assuntoEmailProfessorExcluido(Professor professor, String emailDestino) {
		
		Email email = new Email();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<h1 style=\"size: 15px; text-align: center; font-family: sans-serif; \" > Professor deletado com sucesso !</h1> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > Nome : " + professor.getNomeCompleto() +"</p> <br/> ");
		stringBuilder.append("<p style=\"text-align: center;\" > CPF : " + professor.getCpf() +"</p> <br/> ");
		
		email.getDestinatarios().add(emailDestino);
		email.setTitulo("Professor Deletado");
		email.setAssunto(stringBuilder.toString());
		emailConfig.enviarEmail(email);
	}

}
