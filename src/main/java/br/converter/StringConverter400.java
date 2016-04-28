package br.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "stringConverter400")
public class StringConverter400 implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String descricao = value.toString();
		if (descricao != null && descricao.length() > 400) {
			return descricao.substring(0, 400).concat("...");
		}
		return descricao;
	}

}
