package br.mil.eb.decex.ati.conversor;

import java.text.ParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.swing.text.MaskFormatter;

/**
 * Conversor padrão para códigos de natureza de despesas
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@FacesConverter(value="codigoNDConverter")
public class CodigoNDConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null && value.trim().length() != 0) {			
			value = value.replace(".","");
			return Integer.valueOf(value);			
		}
				
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
				
		if(value != null && value instanceof Integer){
			
			MaskFormatter mask = null;
			
			try {
				mask = new MaskFormatter("##.##.##.##");
				mask.setValueContainsLiteralCharacters(false);
				return mask.valueToString(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}		
			
		}
		
		return null;
	}	
		
}