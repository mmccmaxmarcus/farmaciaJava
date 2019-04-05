package br.com.gerenciamento.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.gerenciamento.entidade.ItemVenda;
import br.com.gerenciamento.entidade.Produto;
import br.com.gerenciamento.entidade.Venda;
import br.com.gerenciamento.util.HibernateUtil;

public class VendaDAO extends GenericDAO<Venda> {
	public void salvar(Venda venda, List<ItemVenda> itensVendas) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.save(venda);
			for (int posicao = 0; posicao < itensVendas.size(); posicao++) {
				ItemVenda itemVenda = itensVendas.get(posicao);
				itemVenda.setVenda(venda);
				sessao.save(itemVenda);

				Produto produto = itemVenda.getProduto();
				Integer quantidade = produto.getQuantidade() - itemVenda.getQuantidade();
				if (quantidade >= 0) {
					produto.setQuantidade(new Short((quantidade) + ""));
					sessao.update(produto);
				} else {
					throw new RuntimeException("Sem produto em estoque");
				}
			}
			transacao.commit();
		} catch (RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;

		} finally {
			sessao.close();
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<Venda> listarPorData(int mes, int ano){
		
		String sql;

		sql = "CAST({alias}.horario as varchar(32)) like '"+ano+"-"+mes+"%'";

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Venda.class);
			
			List<Venda> resultado = consulta.add(Restrictions.sqlRestriction(sql)).list();

			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}
}
