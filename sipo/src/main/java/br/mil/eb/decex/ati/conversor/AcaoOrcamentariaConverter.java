package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.modelo.AcaoOrcamentaria;

/**
 * Conversor padrão para ações orçamentárias
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@FacesConverter(value="acaoOrcamentariaConverter")
public class AcaoOrcamentariaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null && !value.isEmpty()) 
			return (AcaoOrcamentaria) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof AcaoOrcamentaria){
			AcaoOrcamentaria acaoOrcamentaria = (AcaoOrcamentaria) value;
			if(acaoOrcamentaria != null && acaoOrcamentaria instanceof AcaoOrcamentaria 
					&& acaoOrcamentaria.getCodigo() != null) {
				component.getAttributes().put(acaoOrcamentaria.getCodigo(), acaoOrcamentaria);
				return acaoOrcamentaria.getCodigo();
			}
		}
		
		return "";
	}	
		
}