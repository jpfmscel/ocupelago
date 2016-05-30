package br.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.entidades.rest.ImagemREST;

import com.google.gson.annotations.Expose;

@Entity
public class Local implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	@Expose
	private int id;

	// 00.000.000/0001-00
	@Column(nullable = true, length = 18)
	@Expose
	private String cnpj;

	@Column(nullable = true, length = 150)
	@Expose
	private String nome;

	@Column(nullable = false, length = 1000)
	@Expose
	private String descricao;

	@Column(nullable = false, length = 50)
	@Expose
	private String categoria;

	@Column(nullable = false, length = 150)
	@Expose
	private String responsavel;

	@Column(nullable = false, length = 100)
	@Expose
	private String email;

	@Column(nullable = false)
	@Expose
	private double latitude;

	@Column(nullable = false)
	@Expose
	private double longitude;

	@Column(nullable = true, length = 1000)
	@Expose
	private String videoURL;

	@Column(nullable = true, length = 1000)
	@Expose
	private String URL_facebook;

	@Column(nullable = true, length = 1000)
	@Expose
	private String URL_twitter;

	@Column(nullable = true, length = 1000)
	@Expose
	private String URL_site;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean ativo = true;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "local", orphanRemoval = true)
	private List<Avaliacao> avaliacoes;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Imagem> imagens;

	@Column(nullable = true, length = 100)
	@Expose
	private String razaoSocial;

	@Column(nullable = true, length = 100)
	@Expose
	private String nomeFantasia;

	@Column(nullable = true, length = 100)
	@Expose
	private String orgaoResp; // op

	@Column(nullable = false, length = 50)
	@Expose
	private String contato;

	@Expose
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Endereco endereco;

	@Column(nullable = true, length = 15)
	@Expose
	private String telefone1;

	@Column(nullable = true, length = 15)
	@Expose
	private String telefone2;

	@Column(nullable = true, length = 15)
	@Expose
	private String telefone3;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Imagem logo;

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

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getOrgaoResp() {
		return orgaoResp;
	}

	public void setOrgaoResp(String orgaoResp) {
		this.orgaoResp = orgaoResp;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Endereco getEndereco() {
		if (endereco == null) {
			endereco = new Endereco();
		}
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Imagem getLogo() {
		return logo;
	}

	public void setLogo(Imagem logo) {
		this.logo = logo;
	}

	public String getFixedVideoURL() {
		if (videoURL != null && videoURL.contains("watch?v=")) {
			return videoURL.replace("watch?v=", "v/");
		}
		return videoURL;
	}

	public List<Avaliacao> getAvaliacoes() {
		if (avaliacoes == null) {
			avaliacoes = new ArrayList<>();
		}
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
