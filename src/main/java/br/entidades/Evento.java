package br.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.entidades.rest.ImagemREST;

import com.google.gson.annotations.Expose;

@Entity
public class Evento implements Serializable {

	private static final long serialVersionUID = -3256561447102179412L;

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	@Expose
	private int id;

	@Column(nullable = false, length = 100)
	@Expose
	private String nome;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Expose
	private Date dataInicio;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Expose
	private Date dataFim;

	@ManyToOne(cascade = CascadeType.ALL)
	@Expose
	private Local local;

	@Column(nullable = false, length = 15)
	@Expose
	private String telefone;

	@Column(nullable = true, length = 100)
	@Expose
	private String nomeEmpresaOrganizadora;

	@Column(nullable = false, length = 1000)
	@Expose
	private String informacoes;

	@Column(nullable = true, length = 100)
	@Expose
	private String email;

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
	private List<Esporte> listaEsporte;

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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNomeEmpresaOrganizadora() {
		return nomeEmpresaOrganizadora;
	}

	public void setNomeEmpresaOrganizadora(String nomeEmpresaOrganizadora) {
		this.nomeEmpresaOrganizadora = nomeEmpresaOrganizadora;
	}

	public String getInformacoes() {
		return informacoes;
	}

	public void setInformacoes(String informacoes) {
		this.informacoes = informacoes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<Esporte> getListaEsporte() {
		return listaEsporte;
	}

	public void setListaEsporte(List<Esporte> listaEsporte) {
		this.listaEsporte = listaEsporte;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + id;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((nomeEmpresaOrganizadora == null) ? 0 : nomeEmpresaOrganizadora.hashCode());
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
		Evento other = (Evento) obj;
		if (ativo != other.ativo)
			return false;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nomeEmpresaOrganizadora == null) {
			if (other.nomeEmpresaOrganizadora != null)
				return false;
		} else if (!nomeEmpresaOrganizadora.equals(other.nomeEmpresaOrganizadora))
			return false;
		return true;
	}

	public String getFixedVideoURL() {
		if (videoURL.contains("watch?v=")) {
			return videoURL.replace("watch?v=", "v/");
		}
		return videoURL;
	}

}
