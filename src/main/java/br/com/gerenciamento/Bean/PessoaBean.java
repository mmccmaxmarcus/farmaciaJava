package br.com.gerenciamento.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.gerenciamento.DAO.CidadeDAO;
import br.com.gerenciamento.DAO.EstadoDAO;
import br.com.gerenciamento.DAO.PessoaDAO;
import br.com.gerenciamento.entidade.Cidade;
import br.com.gerenciamento.entidade.Estado;
import br.com.gerenciamento.entidade.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements GenericBean, Serializable {
	private Pessoa pessoa;
	private Estado estado;
	private List<Pessoa> pessoas;
	private List<Cidade> cidades;
	private List<Estado> estados;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	private PessoaDAO getPessoaDAO() {
		PessoaDAO pessoaDAO = new PessoaDAO();
		return pessoaDAO;
	}

	private CidadeDAO getCidadeDAO() {
		CidadeDAO cidadeDAO = new CidadeDAO();
		return cidadeDAO;
	}

	private EstadoDAO getEstadoDAO() {
		EstadoDAO estadoDAO = new EstadoDAO();
		return estadoDAO;
	}

	@Override
	public void novo() {
		try {
			this.pessoa = new Pessoa();
			this.cidades = new ArrayList<Cidade>();
			this.estados = getEstadoDAO().listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao gerar uma nova pessoa");
			e.printStackTrace();
		}

	}

	@Override
	public void salvar() {
		try {
			getPessoaDAO().merge(pessoa);
			this.pessoas = getPessoaDAO().listar();
			this.pessoa = new Pessoa();
			this.estado = new Estado();
			this.estados = getEstadoDAO().listar();
			this.cidades = new ArrayList<>();
			Messages.addFlashGlobalInfo("Pessoa salva com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Não foi possível cadastrar pessoa");
			e.printStackTrace();
		}

	}

	@Override
	@PostConstruct
	public void listar() {
		try {
			this.pessoas = getPessoaDAO().listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Não foi possivel listar");
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(ActionEvent evento) {
		try {
			this.pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
			getPessoaDAO().excluir(pessoa);
			this.pessoas = getPessoaDAO().listar();
			Messages.addFlashGlobalInfo("Pessoa excluida com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao excluir pessoa");
			e.printStackTrace();
		}

	}

	@Override
	public void editar(ActionEvent evento) {
		try {
			this.pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
			this.estado = pessoa.getCidade().getEstado();
			this.estados = getEstadoDAO().listar();
            this.cidades = getCidadeDAO().buscarPorEstado(estado.getCodigo());
			getPessoaDAO().editar(pessoa);

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao editar pessoa");
			e.printStackTrace();
		}

	}

	public void popular() {
		try {
          if(estado != null){
        	  this.cidades = getCidadeDAO().buscarPorEstado(estado.getCodigo());
          }
		} catch (RuntimeException e) {
            Messages.addGlobalError("Não foi possivel filtrar estado");
			e.printStackTrace();
		}
	}

}
