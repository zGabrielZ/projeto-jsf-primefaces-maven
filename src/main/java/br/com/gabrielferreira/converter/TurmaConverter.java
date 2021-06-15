package br.com.gabrielferreira.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gabrielferreira.entidade.Turma;
import br.com.gabrielferreira.service.impl.TurmaServiceImpl;

@Named
@RequestScoped
public class TurmaConverter implements Converter{

	@Inject
	private TurmaServiceImpl turmaServiceImpl;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Turma turma = null;
		
		if(value != null) {
			if(value.equals("Selecione")) {
				return null;
			} else {
				turma = turmaServiceImpl.getById(new Integer(value));
			}
		}
		
		return turma;
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Turma turma = (Turma) value;
			return String.valueOf(turma.getId());
		}
		
		return null;
	}

	
	
}
