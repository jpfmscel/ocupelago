package br.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.dao.LocalDAO;
import br.entidades.Local;
 
@FacesConverter(value="localConverter")
public class LocalConverter implements Converter {
 
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String key) {
        return new LocalDAO().buscarPorNome(key);
    }
 
    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        return ((Local)arg2).getNome();
    }
}