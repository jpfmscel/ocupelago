package br.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.gson.annotations.Expose;

@Entity
public class LocalEsporte implements Serializable {

	private static final long serialVersionUID = 1L;

	public LocalEsporte(Esporte e, Local l) {
		setId_esporte(e.getId());
		setId_local(l.getId());
	}

	public LocalEsporte() {
	}

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	@Expose
	private int id;

	@Column(nullable = false)
	private int id_local;

	@Column(nullable = false)
	private int id_esporte;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_local() {
		return id_local;
	}

	public void setId_local(int id_local) {
		this.id_local = id_local;
	}

	public int getId_esporte() {
		return id_esporte;
	}

	public void setId_esporte(int id_esporte) {
		this.id_esporte = id_esporte;
	}

}
