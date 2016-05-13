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
public class Esporte implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	@Expose
	private int id;

	@Column(nullable = false, length = 100)
	@Expose
	private String nome;

	@Column(nullable = false, length = 3000)
	@Expose
	private String descricao;

	@Column(nullable = false, length = 3000)
	@Expose
	private String recomendacoes;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Expose
	private boolean aereo;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Expose
	private boolean aquatico;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Expose
	private boolean terrestre;

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
	private boolean ativo = true;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Imagem> imagens;

	@Expose
	@Transient
	private List<ImagemREST> imagensREST;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAereo() {
		return aereo;
	}

	public void setAereo(boolean aereo) {
		this.aereo = aereo;
	}

	public boolean isAquatico() {
		return aquatico;
	}

	public void setAquatico(boolean aquatico) {
		this.aquatico = aquatico;
	}

	public boolean isTerrestre() {
		return terrestre;
	}

	public void setTerrestre(boolean terrestre) {
		this.terrestre = terrestre;
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

	@Override
	public String toString() {
		return "Esporte [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", aereo=" + aereo + ", aquatico=" + aquatico + ", terrestre=" + terrestre + ", foto=" + ", URL_facebook=" + URL_facebook + ", URL_youtube=" + URL_youtube
				+ ", URL_twitter=" + URL_twitter + ", URL_site=" + URL_site + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aereo ? 1231 : 1237);
		result = prime * result + (aquatico ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + (terrestre ? 1231 : 1237);
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
		Esporte other = (Esporte) obj;
		if (aereo != other.aereo)
			return false;
		if (aquatico != other.aquatico)
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (terrestre != other.terrestre)
			return false;
		return true;
	}

	public String getRecomendacoes() {
		return recomendacoes;
	}

	public void setRecomendacoes(String recomendacoes) {
		this.recomendacoes = recomendacoes;
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

	public String getTipoEsporteClass() {
		if (aquatico) {
			return "aquatico";
		} else if (aereo) {
			return "aereo";
		} else {
			return "terrestre";
		}
	}

	public String getFixedVideoURL() {
		if (videoURL != null && videoURL.contains("watch?v=")) {
			return videoURL.replace("watch?v=", "v/");
		}
		return videoURL;
	}

}
