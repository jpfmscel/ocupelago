package br.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

@Entity
public class Instituicao {

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	@Expose
	private int id;

	@Column(nullable = false, length = 100)
	@Expose
	private String razaoSocial;

	@Column(nullable = false, length = 100)
	@Expose
	private String nomeFantasia;

	@Column(nullable = true, length = 100)
	@Expose
	private String orgaoResp; // op

	@Column(nullable = true, length = 500)
	@Expose
	private String descricao; // op

	@Column(nullable = false, length = 50)
	@Expose
	private String contato;

	@Column(nullable = false, length = 100)
	@Expose
	private String tipo;

	@Column(nullable = true, length = 100)
	@Expose
	private String email; // op

	@Column(nullable = false)
	@Expose
	private Endereco endereco;

	@Column(nullable = true, length = 15)
	@Expose
	private String telefone1;

	@Column(nullable = true, length = 15)
	@Expose
	private String telefone2;

	@Column(nullable = true, length = 15)
	@Expose
	private String telefone3;

	@Column(nullable = true, length = 500)
	@Expose
	private String URL_site; // op

	@Column
	private Imagem logo;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean ativo;

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getOrgaoResp() {
		return orgaoResp;
	}

	public void setOrgaoResp(String orgaoResp) {
		this.orgaoResp = orgaoResp;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public String getURL_site() {
		return URL_site;
	}

	public void setURL_site(String uRL_site) {
		URL_site = uRL_site;
	}

	public Imagem getLogo() {
		return logo;
	}

	public void setLogo(Imagem logo) {
		this.logo = logo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
