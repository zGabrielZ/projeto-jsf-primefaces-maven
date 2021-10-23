package br.com.gabrielferreira.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

import com.sun.faces.util.MessageFactory;

import br.com.gabrielferreira.entidade.Turma;

@Named
@RequestScoped
public class VerificarTurmaAlunoValidator implements Validator {
	
	@Getter
	@Setter
	private String descricaoErro;
	
	@Getter
	@Setter
	private String descricaoCampo;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Turma turma = (Turma) value;
		
		Object label = MessageFactory.getLabel(context, component);
		
		if(turma == null) {
			descricaoErro = label + ": Não é possível cadastrar esta turma, pois é preciso ser informado !";
			descricaoCampo = "Por favor informe a turma novamente !";
			FacesMessage facesMessage = new FacesMessage();
			validarMsgErro(descricaoErro, descricaoCampo, facesMessage);
		}
		
	}
	
	private void validarMsgErro(String descricaoErro, String descricaoCampo, FacesMessage facesMessage) {
		facesMessage =  new FacesMessage(FacesMessage.SEVERITY_ERROR,descricaoErro,descricaoCampo);
		throw new ValidatorException(facesMessage);
	}
}
