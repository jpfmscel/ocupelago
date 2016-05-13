package br.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.entidades.rest.ImagemREST;

import com.google.gson.annotations.Expose;

@Entity
public class Projeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	@Expose
	private int id;

	@Column(nullable = false, length = 100)
	@Expose
	private String titulo;

	@Column(nullable = false, length = 3000)
	@Expose
	private String descricao;

	@Column(nullable = true, length = 1000)
	@Expose
	private String videoURL;

	@Column(nullable = true, length = 1000)
	@Expose
	private String URL_facebook;

	@Column(nullable = true, length = 1000)
	@Expose
	private String URL_youtube;

	@Column(nullable = true, length = 1000)
	@Expose
	private String URL_twitter;

	@Column(nullable = true, length = 1000)
	@Expose
	private String URL_site;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Expose
	private boolean ativo = true;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Imagem> imagens;

	@Expose
	@Transient
	private List<ImagemREST> imagensREST;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Instituicao> patrocinadores;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Instituicao> apoio;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Instituicao> parceiros;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Associado> patrocinadoresAssociado;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Associado> apoioAssociado;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Associado> parceirosAssociado;

	public List<ImagemREST> getImagensREST() {
		if (imagensREST == null) {
			imagensREST = new ArrayList<ImagemREST>();
			for (Imagem imagem : getImagens()) {
				imagensREST.add(new ImagemREST(imagem));
			}
		}
		return imagensREST;
	}

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public String getURL_facebook() {
		return URL_facebook;
	}

	public void setURL_facebook(String uRL_facebook) {
		URL_facebook = uRL_facebook;
	}

	public String getURL_youtube() {
		return URL_youtube;
	}

	public void setURL_youtube(String uRL_youtube) {
		URL_youtube = uRL_youtube;
	}

	public String getURL_twitter() {
		return URL_twitter;
	}

	public void setURL_twitter(String uRL_twitter) {
		URL_twitter = uRL_twitter;
	}

	public String getURL_site() {
		return URL_site;
	}

	public void setURL_site(String uRL_site) {
		URL_site = uRL_site;
	}

	public List<Instituicao> getPatrocinadores() {
		if (patrocinadores == null) {
			patrocinadores = new ArrayList<>();
		}
		return patrocinadores;
	}

	public void setPatrocinadores(List<Instituicao> patrocinadores) {
		this.patrocinadores = patrocinadores;
	}

	public List<Instituicao> getApoio() {
		if (apoio == null) {
			apoio = new ArrayList<>();
		}
		return apoio;
	}

	public void setApoio(List<Instituicao> apoio) {
		this.apoio = apoio;
	}

	public List<Instituicao> getParceiros() {
		if (parceiros == null) {
			parceiros = new ArrayList<>();
		}
		return parceiros;
	}

	public void setParceiros(List<Instituicao> parceiros) {
		this.parceiros = parceiros;
	}

	public List<Associado> getPatrocinadoresAssociado() {
		if (patrocinadoresAssociado == null) {
			patrocinadoresAssociado = new ArrayList<>();
		}
		return patrocinadoresAssociado;
	}

	public void setPatrocinadoresAssociado(List<Associado> patrocinadoresAssociado) {
		this.patrocinadoresAssociado = patrocinadoresAssociado;
	}

	public List<Associado> getApoioAssociado() {
		if (apoioAssociado == null) {
			apoioAssociado = new ArrayList<>();
		}
		return apoioAssociado;
	}

	public void setApoioAssociado(List<Associado> apoioAssociado) {
		this.apoioAssociado = apoioAssociado;
	}

	public List<Associado> getParceirosAssociado() {
		if (parceirosAssociado == null) {
			parceirosAssociado = new ArrayList<>();
		}
		return parceirosAssociado;
	}

	public void setParceirosAssociado(List<Associado> parceirosAssociado) {
		this.parceirosAssociado = parceirosAssociado;
	}

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", titulo=" + titulo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Projeto other = (Projeto) obj;
		if (id != other.id)
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<Imagem> getImagens() {
		if (imagens == null) {
			imagens = new ArrayList<>();
		}
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	public String getFixedVideoURL() {
		if (videoURL != null && videoURL.contains("watch?v=")) {
			return videoURL.replace("watch?v=", "v/");
		}
		return videoURL;
	}

	public String getDescricaoTruncated() {
		if (descricao != null && descricao.length() > 400) {
			return descricao.substring(0, 400).concat("...");
		}
		return descricao;
	}

}
