package br.managedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class ErrorHandler {

	public String getStatusCode() {
		Object e = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.status_code");
		String val = "";
		if(e!=null){
			val = String.valueOf((Integer)e);
		}
		return val;
	}

	public String getMessage() {
		String val = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.message");
		return val;
	}

	public String getExceptionType() {
		Object e = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.exception_type");
		String val = "";
		if(e!=null){
			val = e.toString();
		}
		return val;
	}

	public String getException() {
		Object e = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.exception");
		String val = "";
		if(e == null){
			return val;
		}
		if(e instanceof Exception){
			if(e!=null){
				val = e.toString();
			}
		}else{
			val = e.toString();
		}
		return val;
	}

	public String getRequestURI() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.request_uri");
	}

	public String getServletName() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.servlet_name");
	}

}