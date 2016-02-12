package br.managedBeans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class DashboardView implements Serializable {

	private static final long serialVersionUID = 3980319180670116123L;

	private String itemMenu = "dashboard";
	
	public String mudarMenu(String itemMenu){
		setItemMenu(itemMenu);
//		String retorno = EnumMenu.getPagina(itemMenu);
		if(itemMenu.contains("index")){
			return itemMenu;
		}
		System.out.println("navegação > "+itemMenu);
		return "pages/"+itemMenu;
	}
	
	public String getClasseAtiva(String itemMenu){
		if(itemMenu.equalsIgnoreCase(getItemMenu())){
			return "active";
		}
		return " ";
	}

	public boolean hasErrorMessage(){
		for (FacesMessage fm : FacesContext.getCurrentInstance().getMessageList()) {
			if(fm.getSeverity().equals(FacesMessage.SEVERITY_ERROR)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasInfoMessage(){
		for (FacesMessage fm : FacesContext.getCurrentInstance().getMessageList()) {
			if(fm.getSeverity().equals(FacesMessage.SEVERITY_INFO)){
				return true;
			}
		}
		return false;
	}
	
	public String getItemMenu() {
		return itemMenu;
	}

	public void setItemMenu(String itemMenu) {
		this.itemMenu = itemMenu;
	}
	
}