package br.com.gabrielferreira.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import lombok.Getter;
import lombok.Setter;

import com.sun.faces.util.MessageFactory;

@Named
@RequestScoped
public class VerificarNumeroTurmaValidator implements Validator {
	
	@Getter
	@Setter
	private String descricaoErro;
	
	@Getter
	@Setter
	private String descricaoCampo;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String numero = (String) value;
		
		Object label = MessageFactory.getLabel(context, component);
		
		if(numero.isEmpty()) {
			
			descricaoErro = label + ": Não é possível cadastrar este número, pois é preciso ser informado !";
			descricaoCampo = "Por favor informe o número novamente !";
			FacesMessage facesMessage = new FacesMessage();
			validarMsgErro(descricaoErro, descricaoCampo, facesMessage);
		
		} else if (numero != null && !StringUtils.isNumeric(numero)) {
			
			descricaoErro = label + ": Não é possível cadastrar este número, pois tem que ser númerico !";
			descricaoCampo = "Por favor informe o número novamente !";
			FacesMessage facesMessage = new FacesMessage();
			validarMsgErro(descricaoErro, descricaoCampo, facesMessage);
		}
		
	}
	
	private void validarMsgErro(String descricaoErro, String descricaoCampo, FacesMessage facesMessage) {
		facesMessage =  new FacesMessage(FacesMessage.SEVERITY_ERROR,descricaoErro,descricaoCampo);
		throw new ValidatorException(facesMessage);
	}

}
