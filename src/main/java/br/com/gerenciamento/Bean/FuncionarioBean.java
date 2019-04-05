package br.com.gerenciamento.Bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.gerenciamento.DAO.FuncionarioDAO;
import br.com.gerenciamento.DAO.PessoaDAO;
import br.com.gerenciamento.entidade.Funcionario;
import br.com.gerenciamento.entidade.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements GenericBean, Serializable {
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private List<Pessoa> pessoas;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	private FuncionarioDAO getFuncionarioDAO() {
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		return funcionarioDAO;
	}

	private PessoaDAO getPessoaDAO() {
		PessoaDAO pessoaDAO = new PessoaDAO();
		return pessoaDAO;
	}

	@Override
	public void novo() {
		try {
			this.funcionario = new Funcionario();
			this.pessoas = getPessoaDAO().listar();
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Erro o criar novo funcionario");
		}

	}

	@Override
	public void salvar() {
		try {
			getFuncionarioDAO().merge(funcionario);
			this.funcionario = new Funcionario();
			this.funcionarios = getFuncionarioDAO().listar();
			this.pessoas = getPessoaDAO().listar();
			Messages.addGlobalInfo("Funcionário salvo com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Funcinário não pode ser salvo");
			e.printStackTrace();
		}
	}

	@Override
	@PostConstruct
	public void listar() {
		try {
			this.funcionarios = getFuncionarioDAO().listar();
		} catch (RuntimeException e) {
			Messages.addGlobalInfo("Erro ao listar Funcionário");
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(ActionEvent evento) {
		try {
			this.funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
			this.pessoas = getPessoaDAO().listar();
			getFuncionarioDAO().excluir(funcionario);
			this.funcionarios = getFuncionarioDAO().listar();
			Messages.addGlobalInfo("Funcionário excluido com sucesso");
		} catch (RuntimeException error) {
			Messages.addGlobalError("Erro ao excluir funcionário");
			error.printStackTrace();
		}
	}

	@Override
	public void editar(ActionEvent evento) {
		try {
			this.funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
			this.pessoas = getPessoaDAO().listar();
			getFuncionarioDAO().editar(funcionario);
			this.funcionarios = getFuncionarioDAO().listar();
		} catch (RuntimeException e) {
            Messages.addGlobalError("Erro ao editar funcionário");
            e.printStackTrace();
		}
	}

}
