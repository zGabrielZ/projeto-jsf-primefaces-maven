package br.com.gabrielferreira.validator;


import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielferreira.service.impl.PessoaServiceImpl;
import lombok.Getter;
import lombok.Setter;

import com.sun.faces.util.MessageFactory;

@Named
@RequestScoped
public class VerificarCpfPessoaValidator implements Validator {
	
	@Inject
	private PessoaServiceImpl pessoaService;
	
	@Getter
	@Setter
	private String descricaoErro;
	
	@Getter
	@Setter
	private String descricaoCampo;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String cpf = (String) value;
		
		Object label = MessageFactory.getLabel(context, component);
		
		if(cpf.isEmpty()) {
			
			descricaoErro = label + ": Não é possível cadastrar este cpf, pois é preciso informar cpf !";
			descricaoCampo = "Por favor informe o cpf novamente !";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,descricaoErro,descricaoCampo);
			throw new ValidatorException(message);
			
		} else if(cpf != null && pessoaService.getVerificarCpf(cpf)) {
			
			descricaoErro = label + ": Não é possível cadastrar este cpf, pois já está cadastrado !";
			descricaoCampo = "Por favor informe o cpf novamente !";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,descricaoErro,descricaoCampo);
			throw new ValidatorException(message);
		}
		
	}
}
