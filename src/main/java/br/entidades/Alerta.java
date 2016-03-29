package br.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "ativo <> 0")
public class Alerta implements Serializable {

	private static final long serialVersionUID = -5198508373841343025L;

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	private int id;

	@Column(nullable = false)
	private String titulo;

	@Column(nullable = true)
	private String descricao;

	@Column(nullable = false)
	private double latitude;

	@Column(nullable = false)
	private double longitude;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriado;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean ativo = true;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "Alerta [id=" + id + ", titulo=" + titulo + ", latitude="
				+ getLatitude() + ", longitude=" + getLongitude() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(getLatitude());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(getLongitude());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		Alerta other = (Alerta) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(getLatitude()) != Double
				.doubleToLongBits(other.getLatitude()))
			return false;
		if (Double.doubleToLongBits(getLongitude()) != Double
				.doubleToLongBits(other.getLongitude()))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataCriado() {
		return dataCriado;
	}

	public void setDataCriado(Date dataCriado) {
		this.dataCriado = dataCriado;
	}

}
