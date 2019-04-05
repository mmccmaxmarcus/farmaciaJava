package br.com.gerenciamento.Bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.gerenciamento.DAO.ClienteDAO;
import br.com.gerenciamento.DAO.PessoaDAO;
import br.com.gerenciamento.entidade.Cliente;
import br.com.gerenciamento.entidade.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements GenericBean, Serializable {
	private Cliente cliente;
	private List<Cliente> clientes;
	private List<Pessoa> pessoas;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	private ClienteDAO getClienteDAO() {
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO;
	}

	private PessoaDAO getPessoaDAO() {
		PessoaDAO pessoaDAO = new PessoaDAO();
		return pessoaDAO;
	}

	@Override
	@PostConstruct
	public void novo() {
		try {
			this.cliente = new Cliente();
			this.pessoas = getPessoaDAO().listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao criar novo cliente");
			e.printStackTrace();
		}

	}

	@Override
	public void salvar() {
		try {
			getClienteDAO().merge(cliente);
			this.cliente = new Cliente();
			this.pessoas = getPessoaDAO().listar();
			this.clientes = getClienteDAO().listar();
			Messages.addGlobalInfo("Cliente salvo com sucesso");
		} catch (Exception e) {
			Messages.addGlobalError("Erro ao salvar cliente");
			e.printStackTrace();
		}

	}

	@Override
	@PostConstruct
	public void listar() {
		try {
			this.clientes = getClienteDAO().listarOrdenado();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao listar cliente");
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(ActionEvent evento) {
		try {
			this.cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			getClienteDAO().excluir(cliente);
			this.clientes = getClienteDAO().listar();
			Messages.addGlobalInfo("Cliente excluido com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao excluir cliente");
			e.printStackTrace();
		}

	}

	@Override
	public void editar(ActionEvent evento) {
	    try {
			this.cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			this.pessoas = getPessoaDAO().listar();
			getClienteDAO().editar(cliente);
			this.clientes = getClienteDAO().listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao editar cliente");
			e.printStackTrace();
		}

	}

}
