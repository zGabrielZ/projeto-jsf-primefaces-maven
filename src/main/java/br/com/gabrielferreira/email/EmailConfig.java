package br.com.gabrielferreira.email;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String email = "ferreiragab261297@gmail.com";
	private String senha = "palmeirasganhatudo2020";
	private String listaDestinatarios = "ferreiragabriel2612@gmail.com, ferreiragab261297@gmail.com";
	private String nomeRemetente = "Gabriel Ferreira";
	private String assunto;
	private String texto;
	private Message mensagem;
	
	
	public void enviarEmail(boolean enviarEmailHtml) {
		conexaoGmail(enviarEmailHtml);		
	}
	
	public void conexaoGmail(boolean enviarEmailHtml) {
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.ssl.trust", "*"); // Autenticar com segurança SSL
			properties.put("mail.smtp.auth", "true"); // Autorização
			properties.put("mail.smtp.startls", "true"); // Autenticação 
			properties.put("mail.smtp.host", "smtp.gmail.com"); // Servidor do Gmail
			properties.put("mail.smtp.port", "465"); // Porta do servidor 
			properties.put("mail.smtp.socketFactory.port", "465"); // Expecifica a porta a ser conectada a ser conectada pelo socket
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Classe socket da conexão ao SMTP
	
			Session session = Session.getInstance(properties, new Authenticator() {
				
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email, senha);
				}
			});
			
			entregarPara(session,enviarEmailHtml);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void entregarPara(Session session,boolean enviarEmailHtml) {
		try {
			
			Address[] paraUsuarios = InternetAddress.parse(listaDestinatarios); // Pegando os destinarios
			mensagem = new MimeMessage(session);
			mensagem.setFrom(new InternetAddress(email,nomeRemetente)); // Quem está enviando
			mensagem.setRecipients(Message.RecipientType.TO, paraUsuarios); // E-mails de destino
			mensagem.setSubject(assunto); // Assunto do e-mail
			if(enviarEmailHtml) {
				mensagem.setContent(texto, "text/html; charset=utf-8");
			} else {
				mensagem.setText(texto);
			}
			
			Transport.send(mensagem);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
	
	
}
