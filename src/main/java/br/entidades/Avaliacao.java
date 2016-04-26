package br.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

@Entity
public class Avaliacao implements Serializable {

	private static final long serialVersionUID = -632869959193994525L;

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	@Expose
	private int id;

	@Column(nullable = false, length = 100)
	@Expose
	private String titulo;

	@Column(nullable = false, length = 1000)
	@Expose
	private String comentario;

	@Column(nullable = false)
	@Expose
	private Integer nota;

	@ManyToOne
	@Expose
	private Local local;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean ativo = true;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Expose
	private Date criadoEm;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Date getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Date criadoEm) {
		this.criadoEm = criadoEm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((criadoEm == null) ? 0 : criadoEm.hashCode());
		result = prime * result + id;
		result = prime * result + ((nota == null) ? 0 : nota.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Avaliacao other = (Avaliacao) obj;
		if (ativo != other.ativo)
			return false;
		if (criadoEm == null) {
			if (other.criadoEm != null)
				return false;
		} else if (!criadoEm.equals(other.criadoEm))
			return false;
		if (id != other.id)
			return false;
		if (nota == null) {
			if (other.nota != null)
				return false;
		} else if (!nota.equals(other.nota))
			return false;
		return true;
	}

	public Local getLocal() {
		if (local == null) {
			local = new Local();
		}
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

}
