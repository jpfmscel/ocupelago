package br.managedBeans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.dao.UsuarioDAO;
import br.entidades.Usuario;

@ViewScoped
@ManagedBean(name = "manterUsuario")
public class ManterUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuarioAdd;
	private UsuarioDAO dao;

	public String adicionarUsuario() {

		if (!getUsuarioAdd().getSenha().equals(getUsuarioAdd().getConfirmacaoSenha())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A senha não é igual à confirmação de senha!", ""));
			return "addUsuario.xhtml";
		}

		Usuario userDB = getDao().buscarUsuario(getUsuarioAdd().getEmail());

		if (userDB != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existe um registro com esse login ou e-mail!", ""));
			return "addUsuario.xhtml";
		} else {
			getUsuarioAdd().setDataCriado(new Date());
			getDao().iniciarTransacao();
			getDao().inserir(getUsuarioAdd());
			getDao().comitarTransacao();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário registrado com sucesso!", ""));
			setUsuarioAdd(null);
		}

		return "login.xhtml";
	}

	public Usuario getUsuarioAdd() {
		if (usuarioAdd == null) {
			usuarioAdd = new Usuario();
		}
		return usuarioAdd;
	}

	public void setUsuarioAdd(Usuario usuarioAdd) {
		this.usuarioAdd = usuarioAdd;
	}

	public UsuarioDAO getDao() {
		if (dao == null) {
			dao = new UsuarioDAO();
		}
		return dao;
	}

	public void setDao(UsuarioDAO dao) {
		this.dao = dao;
	}

}
