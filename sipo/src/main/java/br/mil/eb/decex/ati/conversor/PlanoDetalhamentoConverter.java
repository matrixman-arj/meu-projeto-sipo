package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.modelo.PlanoDetalhamento;

/**
 * Conversor padrão para planos de detalhamento
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@FacesConverter(value="planoDetalhamentoConverter")
public class PlanoDetalhamentoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null && !value.isEmpty()) 
			return (PlanoDetalhamento) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof PlanoDetalhamento){
			PlanoDetalhamento planoDetalhamento = (PlanoDetalhamento) value;
			planoDetalhamento.getCodigo();
			if(planoDetalhamento != null && planoDetalhamento instanceof PlanoDetalhamento 
					&& planoDetalhamento.getCodigo() != null) {
				component.getAttributes().put(planoDetalhamento.getCodigo(), planoDetalhamento);
				return planoDetalhamento.getCodigo();
			}
		}
		
		return "";
	}	
		
}