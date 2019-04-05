package br.com.gerenciamento.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.gerenciamento.entidade.Imagem;
import br.com.gerenciamento.util.HibernateUtil;

@SuppressWarnings("serial")
public class ImagemDAO extends GenericDAO<Imagem> implements Serializable {
   @SuppressWarnings("unchecked")
	public List<Imagem> listarImagemProdutos(Long codigo) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			return sessao.createCriteria(Imagem.class, "i").createAlias("produto", "p")
					.add(Restrictions.eq("p.codigo", codigo)).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			sessao.close();
		}
		return null;
	}

}
