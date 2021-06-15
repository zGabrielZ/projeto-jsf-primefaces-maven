package br.com.gabrielferreira.validator;

import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import com.sun.faces.util.MessageFactory;

import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class VerificarDataAdmissaoValidator implements Validator {
	
	@Getter
	@Setter
	private String descricaoErro;
	
	@Getter
	@Setter
	private String descricaoCampo;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Date dataAdmissao = (Date) value;
		Date dataAtual = new Date();
		Calendar c1 = null;
		
		Object label = MessageFactory.getLabel(context, component);
		
		if(dataAdmissao != null) {
			c1 = getCalendar(dataAdmissao);
		}
		Calendar c2 = getCalendar(dataAtual);
		
		if(dataAdmissao == null) {
			
			descricaoErro = label + ": Não é possível cadastrar esta data de admissão, pois a data precisa ser informada !";
			descricaoCampo = "Por favor informe a data de admissão novamente !";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,descricaoErro,descricaoCampo);
			throw new ValidatorException(message);
		
		} else if(dataAdmissao != null && c1.after(c2) ) {
			
			descricaoErro = label + ": Não é possível cadastrar esta data de admissão, a data não pode ser maior que a data atual !";
			descricaoCampo = "Por favor informe a data de admissão novamente !";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,descricaoErro,descricaoCampo);
			throw new ValidatorException(message);
		
		}
		
	}
	
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
}
