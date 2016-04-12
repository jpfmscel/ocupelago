package br.managedBeans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.dao.UsuarioDAO;
import br.entidades.Usuario;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuarioLogado;
	private UsuarioDAO dao;
	private boolean logado = false;

	public String login() {
		Usuario user = getDao().logarUsuario(getUsuarioLogado().getEmail(), getUsuarioLogado().getSenha());
		if (user != null) {
			setUsuarioLogado(user);
			setLogado(true);
			return "indexadm";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não registrado!", ""));
		}
		return "login";
	}

	public String logout() {
		setUsuarioLogado(null);
		setLogado(false);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
		return "login";
	}

	public String isLoggedForward() {
        if (!isLogado()) {
            return "login.xhtml?faces-redirect=true";
        }

        return null;
    }
	
	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) {
			usuarioLogado = new Usuario();
		}
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
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

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

}
