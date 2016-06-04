package br.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.gson.annotations.Expose;

@Entity
public class EventoEsporte implements Serializable {

	private static final long serialVersionUID = 1L;

	public EventoEsporte(Esporte e, Evento v) {
		setId_esporte(e.getId());
		setId_evento(v.getId());
	}

	public EventoEsporte() {
	}

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	@Expose
	private int id;

	@Column(nullable = false)
	private int id_evento;

	@Column(nullable = false)
	private int id_esporte;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_evento() {
		return id_evento;
	}

	public void setId_evento(int id_evento) {
		this.id_evento = id_evento;
	}

	public int getId_esporte() {
		return id_esporte;
	}

	public void setId_esporte(int id_esporte) {
		this.id_esporte = id_esporte;
	}

}
