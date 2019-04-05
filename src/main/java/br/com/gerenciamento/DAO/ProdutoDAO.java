package br.com.gerenciamento.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.gerenciamento.entidade.Produto;
import br.com.gerenciamento.util.HibernateUtil;

public class ProdutoDAO extends GenericDAO<Produto> {
	protected Session sessao;

	@SuppressWarnings("unchecked")
	public List<Produto> listaDeProdutos(Long ID) {
		sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			return sessao.createCriteria(Produto.class, "f").createAlias("produto", "p")
					.add(Restrictions.eq("p.ID", ID)).list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sessao.close();
		}
		return null;
	}
}
