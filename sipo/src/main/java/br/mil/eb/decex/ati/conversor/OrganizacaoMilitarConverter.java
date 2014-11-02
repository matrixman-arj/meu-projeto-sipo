package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;

/**
 * Conversor padrão para organizações militares
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@FacesConverter(value="organizacaoMilitarConverter")
public class OrganizacaoMilitarConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null && !value.isEmpty()) 
			return (OrganizacaoMilitar) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof OrganizacaoMilitar){
			OrganizacaoMilitar organizacaoMilitar = (OrganizacaoMilitar) value;
			if(organizacaoMilitar != null && organizacaoMilitar instanceof OrganizacaoMilitar 
					&& organizacaoMilitar.getSigla() != null) {
				component.getAttributes().put(organizacaoMilitar.getSigla(), organizacaoMilitar);
				return organizacaoMilitar.getSigla();
			}
		}
		
		return "";
	}	
		
}