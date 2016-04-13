package br.entidades;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
public class Noticia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	private int id;

	@Column(nullable = false, length = 100)
	private String titulo;

	@Column(nullable = false, length = 300)
	private String subtitulo;

	@Column(nullable = false, length = 3000)
	private String descricao;

	@Column(nullable = true, length = 1000)
	private String videoURL;

	@Column(nullable = true, length = 1000)
	private String URL_facebook;

	@Column(nullable = true, length = 1000)
	private String URL_youtube;

	@Column(nullable = true, length = 1000)
	private String URL_twitter;

	@Column(nullable = true, length = 1000)
	private String URL_site;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date criadoEm;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean ativo = true;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Imagem> imagens;

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

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
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
		Noticia other = (Noticia) obj;
		if (id != other.id)
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Noticia [id=" + id + ", titulo=" + titulo + ", subtitulo=" + subtitulo + ", videoURL=" + videoURL + "]";
	}

	public Date getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Date criadoEm) {
		this.criadoEm = criadoEm;
	}

	public String getDataCriadaFormatada() {
		return new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(getCriadoEm()) + " h de Bras�lia";
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

	public String getDescricaoTruncated() {
		if (descricao != null && descricao.length() > 400) {
			return descricao.substring(0, 400).concat("...");
		}
		return descricao;
	}
	
	public String getFixedVideoURL(){
		if(videoURL.contains("watch?v=")){
			return videoURL.replace("watch?v=", "v/");
		}
		return null;
	}


}
