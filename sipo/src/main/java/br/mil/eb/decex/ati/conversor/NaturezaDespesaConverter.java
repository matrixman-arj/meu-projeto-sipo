package br.mil.eb.decex.ati.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.mil.eb.decex.ati.modelo.NaturezaDespesa;

/**
 * Conversor padrão para naturezas de despesa
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@FacesConverter(value="naturezaDespesaConverter")
public class NaturezaDespesaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null && !value.isEmpty()) 
			return (NaturezaDespesa) component.getAttributes().get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof NaturezaDespesa){
			NaturezaDespesa naturezaDespesa = (NaturezaDespesa) value;
			if(naturezaDespesa != null && naturezaDespesa instanceof NaturezaDespesa 
					&& naturezaDespesa.getCodigo() != null) {
				component.getAttributes().put(naturezaDespesa.getCodigo().toString(), naturezaDespesa);
				return naturezaDespesa.getCodigo().toString();
			}
		}
		
		return "";
	}	
		
}