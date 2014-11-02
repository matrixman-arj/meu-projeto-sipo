package br.mil.eb.decex.ati.conversor;

import java.text.ParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.swing.text.MaskFormatter;

@FacesConverter("cpfConverter")
public class CPFConverter implements Converter {

	/**
	 * Conversor padrão CPF
	 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
	 * @version 1.0
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null) {
			value = value.replace(".","");
			value = value.replace("-","");
			return value;
		}
				
		return null;
	}

	@Override
	public String getAsString(FacesContext faces, UIComponent component, Object value) {
		
		if(value != null && value instanceof String) {
		
			MaskFormatter mask = null;
			
			try {
				mask = new MaskFormatter("###.###.###-##");
				mask.setValueContainsLiteralCharacters(false);
				return mask.valueToString(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}		
		
		}
		return null;	
	}	
}