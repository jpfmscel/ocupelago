package br.entidades.rest;

import java.io.Serializable;
import java.util.Date;

import br.entidades.Imagem;
import br.util.Util;

import com.google.gson.annotations.Expose;

public class ImagemREST implements Serializable {

	private static final long serialVersionUID = 6273434707549777440L;

	public ImagemREST(Imagem i) {
		setId(i.getId());
		setNomeArquivo(i.getNomeArquivo());
		setConteudo(getBase64EncodedBytes(i));
		setDataCriado(i.getDataCriado());
	}

	@Expose
	private int id;
//	@Expose
	private String nomeArquivo;
//	@Expose
	private String conteudo;
//	@Expose
	private Date dataCriado;

	private String getBase64EncodedBytes(Imagem i) {
		return new String(Util.encodeBase64(i.getData()));
	}

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
		ImagemREST other = (ImagemREST) obj;
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

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
