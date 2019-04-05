package br.com.gerenciamento.Bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.gerenciamento.DAO.ClienteDAO;
import br.com.gerenciamento.DAO.FuncionarioDAO;
import br.com.gerenciamento.DAO.ProdutoDAO;
import br.com.gerenciamento.DAO.VendaDAO;
import br.com.gerenciamento.entidade.Cliente;
import br.com.gerenciamento.entidade.Funcionario;
import br.com.gerenciamento.entidade.ItemVenda;
import br.com.gerenciamento.entidade.Produto;
import br.com.gerenciamento.entidade.Venda;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {
	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	private Venda venda;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private List<Venda> vendas;
	private Calendar calendar = Calendar.getInstance();
	private String data;
	private boolean desenha = true;

	public boolean isDesenha() {
		return desenha;
	}

	public void setDesenha(boolean desenha) {
		this.desenha = desenha;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	private ProdutoDAO getProdutoDao() {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		return produtoDAO;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	private ClienteDAO getClienteDao() {
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO;
	}

	private FuncionarioDAO getFuncionarioDao() {
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		return funcionarioDAO;
	}

	private VendaDAO getVendaDao() {
		VendaDAO vendaDAO = new VendaDAO();
		return vendaDAO;
	}

	public void novo() {
		try {
			venda = new Venda();
			venda.setValorTotal(new BigDecimal("0.00"));
			this.produtos = getProdutoDao().listarOrdenado("descricao");

			this.itensVenda = new ArrayList<>();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Não foi possivel listar produtos");
			e.printStackTrace();
		}
	}

	public void listar() {
		try {
			this.vendas = getVendaDao().listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao listar vendas");
		}

		if (vendas.size() == 5) {
			vendas.remove(0);
			vendas.remove(1);
		}
	}

	public void adicionar(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		Integer achou = null;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
			}
		}

		if (achou == null) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setValorParcial(produto.getPreco());
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(new Short("1"));
			itensVenda.add(itemVenda);
		} else {
			ItemVenda itemVenda = itensVenda.get(achou);
			itemVenda.setQuantidade(new Short((short) (itemVenda.getQuantidade() + 1)));
			itemVenda.setValorParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}

		this.calcular();
	}

	public void atualizarPrecoParcial() {
		for (ItemVenda itemVenda : this.itensVenda) {
			itemVenda.setValorParcial(
					itemVenda.getProduto().getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}
		this.calcular();
	}

	public void remover(ActionEvent evento) {
		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");
		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())) {
				achou = posicao;
			}

		}

		if (achou > -1) {
			itensVenda.remove(achou);

		}
		this.calcular();
	}

	private void calcular() {
		venda.setValorTotal(new BigDecimal("0.00"));
		for (ItemVenda itemVenda : itensVenda) {
			venda.setValorTotal(venda.getValorTotal().add(itemVenda.getValorParcial()));
		}
	}

	public void finalizar() {
		try {
			this.venda.setHorario(new Date());
			this.funcionarios = getFuncionarioDao().listarOrdenado();
			this.clientes = getClienteDao().listarOrdenado();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao finalizar venda");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (venda.getValorTotal().signum() == 0) {
				Messages.addGlobalError("Informe um item para venda");
				return;
			}
			getVendaDao().salvar(venda, itensVenda);
			this.novo();
			Messages.addGlobalInfo("Venda salva com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao salvar venda");
			e.printStackTrace();
		}
	}

	public int produtos(int i){
		return produtos_vendidos[i];
	}
	public double arrecadado(int i){
		return arrecadado[i];
	}
	private int[] produtos_vendidos = new int[3];
	private double[] arrecadado = new double[3];
	public void produtosVendidosMes() {
		int mes[] = new int[3];
		if(data == null){
			data = "12/2017";
		}
		
		mes[1] = Integer.parseInt(data.substring(0, 2));
		mes[0] = mes[1]-1;
		mes[2] = mes[1]+1;
		int ano = Integer.parseInt(data.replace("/", "").substring(2, 6));
		
		if(mes[0] < 1)
			mes[0] = 12;
		
		if(mes[2] > 12)
			mes[2] = 1;
		
		
		List<Venda> vendas;
		for (int i = 0; i <= 2; i++) {
			produtos_vendidos[i] = 0;

			vendas = getVendaDao().listarPorData(mes[i], ano);

			for (Venda venda : vendas) {
//				calendar.setTime(venda.getHorario());
//				System.out.println("MES: " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
//						+ "	Ano: " + calendar.get(Calendar.YEAR));
				for(ItemVenda iv : venda.getItemVendas()){
					produtos_vendidos[i] += iv.getQuantidade();
				}
				arrecadado[i] = venda.getValorTotal().doubleValue();
			}
		}
	}
}