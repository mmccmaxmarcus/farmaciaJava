package br.com.gerenciamento.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@SuppressWarnings("serial")
@Entity
public class Imagem extends GenericDomain {


	
	@Column(columnDefinition="LONGBLOB", nullable=false)
	private byte [] imagem;
	
	@Column(length = 30)
	private String descricao;
	
	@JoinColumn(nullable=true)
	@ManyToOne(cascade = {CascadeType.ALL})
	private Produto produto;

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
	
}
