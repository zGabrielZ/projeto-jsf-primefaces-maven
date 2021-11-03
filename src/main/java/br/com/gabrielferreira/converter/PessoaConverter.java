package br.com.gabrielferreira.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielferreira.entidade.Pessoa;
import br.com.gabrielferreira.service.impl.PessoaServiceImpl;

@Named
@RequestScoped
public class PessoaConverter implements Converter{

	@Inject
	private PessoaServiceImpl pessoaServiceImpl;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pessoa pessoa = null;
		
		if(value != null) {
			if(value.contains("Selecione")) {
				return null;
			} else {
				pessoa = pessoaServiceImpl.getConsultarDetalhe(new Integer(value));
			}
		}
		
		return pessoa;
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Pessoa pessoa = (Pessoa) value;
			return String.valueOf(pessoa.getId());
		}
		
		return null;
	}

	
	
}
