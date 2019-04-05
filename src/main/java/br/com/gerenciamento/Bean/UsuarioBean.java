package br.com.gerenciamento.Bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.gerenciamento.DAO.PessoaDAO;
import br.com.gerenciamento.DAO.UsuarioDAO;
import br.com.gerenciamento.entidade.Pessoa;
import br.com.gerenciamento.entidade.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements GenericBean, Serializable {
	private Usuario usuario;
	private List<Usuario> usuarios;
	private Pessoa pessoa;
	private List<Pessoa> pessoas;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	private UsuarioDAO getUsuarioDAO() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		return usuarioDAO;
	}

	private PessoaDAO getPessoaDAO() {
		PessoaDAO pessoaDAO = new PessoaDAO();
		return pessoaDAO;
	}

	@Override
	public void novo() {
		try {
			this.usuario = new Usuario();
			this.pessoas = getPessoaDAO().listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao adicionar novo usuário");
			e.printStackTrace();
		}

	}

	@Override
	public void salvar() {
		try {
			getUsuarioDAO().merge(usuario);
			this.usuario=  new Usuario();
			this.pessoas = getPessoaDAO().listar();
			this.usuarios = getUsuarioDAO().listar();
			Messages.addGlobalInfo("Usuário salvo com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao salvar usuário");
			e.printStackTrace();
		}

	}

	@Override
	@PostConstruct
	public void listar() {
		try {
			this.usuarios = getUsuarioDAO().listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar");
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(ActionEvent evento) {
		try {
		this.usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
		getUsuarioDAO().excluir(usuario);
		this.usuarios = getUsuarioDAO().listar();
		Messages.addGlobalInfo("Usuário excluido com sucesso");
		}catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao excluir usuário");
			e.printStackTrace();
		}
		

	}

	@Override
	public void editar(ActionEvent evento) {
		try {
		this.usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
        this.pessoas = getPessoaDAO().listar();
        getUsuarioDAO().editar(usuario);
        this.usuarios = getUsuarioDAO().listar();
		}catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao editar usuário");
		}
	}

}
