package br.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Imagem implements Serializable {

	private static final long serialVersionUID = 6755019750758876410L;

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	private int id;

	@Column(nullable = false)
	private String nomeArquivo;

	@Column(nullable = false, length = 5000000)
	private byte[] data;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriado;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Projeto projeto;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Alerta alerta;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Noticia noticia;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Evento evento;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Esporte esporte;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Local local;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Alerta getAlerta() {
		return alerta;
	}

	public void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Esporte getEsporte() {
		return esporte;
	}

	public void setEsporte(Esporte esporte) {
		this.esporte = esporte;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getDataCriado() == null) ? 0 : getDataCriado().hashCode());
		result = prime * result + id;
		result = prime * result + ((nomeArquivo == null) ? 0 : nomeArquivo.hashCode());
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
		Imagem other = (Imagem) obj;
		if (getDataCriado() == null) {
			if (other.getDataCriado() != null)
				return false;
		} else if (!getDataCriado().equals(other.getDataCriado()))
			return false;
		if (id != other.id)
			return false;
		if (nomeArquivo == null) {
			if (other.nomeArquivo != null)
				return false;
		} else if (!nomeArquivo.equals(other.nomeArquivo))
			return false;
		return true;
	}

	public Date getDataCriado() {
		return dataCriado;
	}

	public void setDataCriado(Date dataCriado) {
		this.dataCriado = dataCriado;
	}

}
