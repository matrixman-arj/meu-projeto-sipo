package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.modelo.SecaoAssessoria;

/**
 * Conversor padrão para organizações militares
 * @author <b>Vanilton</b> Gomes dos Santos - 3° Sgt QE
 * @version 1.0
 */
@FacesConverter(value="secaoAssessoriaConverter")
public class SecaoAssessoriaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null && !value.isEmpty()) 
			return (SecaoAssessoria) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof SecaoAssessoria){
			SecaoAssessoria secaoAssessoria = (SecaoAssessoria) value;
			if(secaoAssessoria != null && secaoAssessoria instanceof SecaoAssessoria 
					&& secaoAssessoria.getSigla() != null) {
				component.getAttributes().put(secaoAssessoria.getSigla(), secaoAssessoria);
				return secaoAssessoria.getSigla();
			}
		}
		
		return "";
	}	
		
}