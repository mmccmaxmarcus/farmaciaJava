package br.com.gerenciamento.Bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.gerenciamento.DAO.UsuarioDAO;
import br.com.gerenciamento.entidade.Pessoa;
import br.com.gerenciamento.entidade.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AutenticacaoBean implements Serializable {
	private Usuario usuario;
	private Usuario logado;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getLogado() {
		return logado;
	}
	
	public void setLogado(Usuario logado) {
		this.logado = logado;
	}

	@PostConstruct
	public void novo() {
		this.usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}

	public void autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			logado = usuarioDAO.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());
			if(logado != null){
			Faces.redirect("./paginas/principal.xhtml");
			}else {
				Messages.addGlobalWarn("Digite CPF e/ou Senha corretos");
				return;
			}
		} catch (IOException e) {
			Messages.addFlashGlobalError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean ocultarPermissoes(List<String> permissoes){
		for (String permissao : permissoes) {
			if(logado.getTipo() == (permissao.charAt(0))){
				return true;
			}
		}
		return false;
	}
}
