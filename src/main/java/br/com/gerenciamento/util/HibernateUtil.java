package br.com.gerenciamento.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory = buildSessionFactory();

	public static SessionFactory getFabricaDeSessoes(){
		return sessionFactory;
	}
	public static Connection getConexoes() {
		Session sessao = sessionFactory.openSession();
		Connection conexao = sessao.doReturningWork(new ReturningWork<Connection>() {

			@Override
			public Connection execute(Connection conexao) throws SQLException {
				// TODO Auto-generated method stub
				return conexao;
			}
		});
		return conexao;

	}

	private static SessionFactory buildSessionFactory() {
		try {
			// Cria fábrica de sessão hibernate.cfg.xml
			Configuration configuracao = new Configuration().configure();
			ServiceRegistry registro = new StandardServiceRegistryBuilder().applySettings(configuracao.getProperties())
					.build();
			SessionFactory fabrica = configuracao.buildSessionFactory(registro);
			return fabrica;
		} catch (Throwable ex) {

			System.err.println("Fábrica de sessão não pode ser iniciada" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
