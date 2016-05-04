package br.entidades.rest;

import java.io.Serializable;
import java.util.Date;

import br.entidades.Avaliacao;

import com.google.gson.annotations.Expose;

public class AvaliacaoREST implements Serializable {

	public AvaliacaoREST(Avaliacao a) {
		setComentario(a.getComentario());
		setCriadoEm(a.getCriadoEm());
		setId(a.getId());
		setNota(a.getNota());
		setTitulo(a.getTitulo());
	}

	private static final long serialVersionUID = -5105981615176151282L;

	@Expose
	private int id;

	@Expose
	private String titulo;

	@Expose
	private String comentario;

	@Expose
	private Integer nota;

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
		AvaliacaoREST other = (AvaliacaoREST) obj;
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

	@Override
	public String toString() {
		return "AvaliacaoREST [id=" + id + ", titulo=" + titulo + ", comentario=" + comentario + ", nota=" + nota + ", criadoEm=" + criadoEm + "]";
	}

}
