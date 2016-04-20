package br.enumeradores;

public enum EnumWebMethods {

	GET_ESPORTES(1, "getEsportes"), GET_ALERTAS(2, "getAlertas"), GET_LOCAIS(3, "getLocais"), GET_PROJETOS(4, "getProjetos"), GET_NOTICIAS(5, "getNoticias"), GET_EVENTOS(6, "getEventos"), ADD_ALERTA(7, "addAlerta"), ADD_AVALIACAO(8, "addAvaliacao"), LOGIN(
			9, "login"), ADD_USUARIO(10, "addUsuario");

	EnumWebMethods(Integer cod, String nome) {
		setNome(nome);
		setCodigo(cod);
	}

	private Integer codigo;
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public static String getNomeByCodigo(int cod) {
		for (EnumWebMethods en : EnumWebMethods.values()) {
			if (en.getCodigo().intValue() == cod) {
				return en.getNome();
			}
		}
		return null;
	}

	public static Integer getCodigoByNome(String nome) {
		for (EnumWebMethods en : EnumWebMethods.values()) {
			if (en.getNome().equalsIgnoreCase(nome)) {
				return en.getCodigo();
			}
		}
		return null;
	}

	public static EnumWebMethods getEnumByCod(Integer cod) {
		for (EnumWebMethods en : EnumWebMethods.values()) {
			if (en.getCodigo().intValue() == cod) {
				return en;
			}
		}
		return null;
	}

}
