package br.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

@Entity
public class Endereco {

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	@Expose
	private int id;

	@Column(nullable = false, length = 9)
	@Expose
	private String cep;

	@Column(nullable = false, length = 50)
	@Expose
	private String cidade;

	@Column(nullable = false, length = 50)
	@Expose
	private String bairro;

	@Column(nullable = false, length = 2)
	@Expose
	private String estado;

	@Column(nullable = false, length = 200)
	@Expose
	private String logradouro;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean ativo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
