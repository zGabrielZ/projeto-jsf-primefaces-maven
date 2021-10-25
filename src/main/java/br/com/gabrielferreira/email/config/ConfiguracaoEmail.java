package br.com.gabrielferreira.email.config;

import java.util.Properties;

import javax.mail.Session;

import br.com.gabrielferreira.entidade.Email;

public interface ConfiguracaoEmail {

	Properties definirPropriedades();
	
	Session conectarServidor(Properties properties);
	
	void enviarEmail(Email email);
}
