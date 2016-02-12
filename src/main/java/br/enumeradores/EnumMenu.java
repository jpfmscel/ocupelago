package br.enumeradores;

public enum EnumMenu {

	DASHBOARD ("dashboard", "/pages/index.xhtml"),
	DISPOSITIVOS ("dispositivos", "/pages/dispositivos.xhtml"),
	USUARIOS ("usuarios", "/pages/inserirUsuario.xhtml");
//	DASHBOARD ("dashboard", "index.xhtml"),
//	DASHBOARD ("dashboard", "index.xhtml");
	
	private String menuItem;
	private String pagina;
	
	EnumMenu(String menuItem, String pagina){
		setMenuItem(menuItem);
		setPagina(pagina);
	}
	
	public String getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem;
	}
	public String getPagina() {
		return pagina;
	}
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	
	public static String getPagina (String menuItem){
		for (EnumMenu en : EnumMenu.values()) {
			if(en.getMenuItem().equalsIgnoreCase(menuItem)){
				return en.getPagina();
			}
		}
		return null;
	}
}
