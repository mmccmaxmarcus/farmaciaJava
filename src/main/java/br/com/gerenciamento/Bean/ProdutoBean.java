package br.com.gerenciamento.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import org.hibernate.loader.custom.Return;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import br.com.gerenciamento.DAO.FabricanteDAO;
import br.com.gerenciamento.DAO.ImagemDAO;
import br.com.gerenciamento.DAO.ProdutoDAO;
import br.com.gerenciamento.entidade.Fabricante;
import br.com.gerenciamento.entidade.Imagem;
import br.com.gerenciamento.entidade.Produto;
import br.com.gerenciamento.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@ManagedBean
@SuppressWarnings("serial")
@SessionScoped
public class ProdutoBean implements Serializable {
	private Produto produto;
	private Produto produtoSelecionado = new Produto();
	private List<Produto> produtos;
	private List<Fabricante> fabricantes;
	private StreamedContent downloadFoto;

	private List<Imagem> imagens = new ArrayList<>();
	private Imagem imagem = new Imagem();
	private StreamedContent imagemStream = new DefaultStreamedContent();

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public StreamedContent getDownloadFoto() {
		return downloadFoto;
	}

	public void setDownloadFoto(StreamedContent downloadFoto) {
		this.downloadFoto = downloadFoto;
	}

	private ProdutoDAO getProdutoDAO() {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		return produtoDAO;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {

		this.imagem = imagem;
	}

	private ImagemDAO getImagemDAO() {
		ImagemDAO imagemDAO = new ImagemDAO();
		return imagemDAO;
	}
	
	public StreamedContent getImagemStream() {
		return imagemStream;
	}
	
	public void setImagemStream(StreamedContent imagemStream) {
		this.imagemStream = imagemStream;
	}

	public void novo() {
		try {
			this.produto = new Produto();
			this.imagem = new Imagem();
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao gerar um novo fabricanteS");
			e.printStackTrace();
		}
	}

	public void salvarImagem() {
		try {
			getImagemDAO().salvar(imagem);
			this.imagem = new Imagem();
			Messages.addGlobalInfo("Imagem salva com sucesso");
		} catch (RuntimeException e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro ao salvar a imagem");
		} finally {
			this.imagem = new Imagem();

		}
	}

	public void fileUpload(FileUploadEvent evento) throws IOException {
		try {
			 if(evento.getFile().getContents() == null){
			    this.imagem = new Imagem();
			 }	 
			this.imagemStream = new DefaultStreamedContent(evento.getFile().getInputstream());
	
			imagem.setProduto(produtoSelecionado);
			imagem.setImagem(evento.getFile().getContents());
			 
			//System.out.println(imagem.getDescricao());
			//System.out.println(imagem.getImagem().toString());
			//System.out.println(imagem.getProduto().getCodigo());

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao fazer upload");
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void listaImagensProduto() {
		try {

			ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			this.imagens = getImagemDAO().listarImagemProdutos(produtoSelecionado.getCodigo());
			
			File pasta = new File(sContext.getRealPath("/temp"));
			if (!pasta.exists())
				pasta.mkdirs();
			for (Imagem i : imagens) {
				String nomeArquivo = i.getCodigo() + ".png";
				String arquivo = sContext.getRealPath("/temp/" + nomeArquivo);
				this.criaArquivo(i.getImagem(), arquivo);

			}

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro a listar a imagem");
			e.printStackTrace();
		}
	}

	private void criaArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			// if (produto.getCaminho() == null) {
			// Messages.addGlobalError("O campo foto é obrigatório");
			// return;
			// }
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.merge(produto);
			// Produto produtoRetorno = produtoDAO.merge(produto);
			// Path origem = Paths.get(produto.getCaminho());
			// Path destino = Paths.get("/home/upload/" +
			// produtoRetorno.getCodigo() + ".png");
			// Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			this.produto = new Produto();
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			this.fabricantes = fabricanteDAO.listar();
			this.produtos = produtoDAO.listar();
			Messages.addGlobalInfo("Produto salvo com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Não foi possivel salvar o produto");
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			this.produtos = produtoDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar");
			e.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			this.produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			getProdutoDAO().excluir(produto);

			Path caminho = Paths.get("/home/upload/" + produto.getCodigo() + ".png");
			Files.deleteIfExists(caminho);

			this.produtos = getProdutoDAO().listar();
			Messages.addGlobalInfo("Produto excluido com sucesso");
		} catch (RuntimeException | IOException e) {
			Messages.addGlobalError("Não foi possivel excluir o produto");
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			this.produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			this.produto.setCaminho("C:/Users/deads/Desktop/uploads/" + produto.getCodigo() + ".png");
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			this.fabricantes = fabricanteDAO.listar();
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.editar(produto);
			this.produtos = produtoDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Não foi possivel editar o produto");
			e.printStackTrace();
		}
	}

	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formlistagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();
			String proDescricao = (String) filtros.get("descricao");
			String fabDescricao = (String) filtros.get("fabricante.descricao");

			String caminho = Faces.getRealPath("/reports/produtos.jasper");
			String caminhoBanner = Faces.getRealPath("/resources/imagens/banner.jpg");

			Map<String, Object> parametros = new HashMap<>();

			parametros.put("CAMINHO_BANNER", caminhoBanner);

			if (proDescricao == null) {
				parametros.put("PRODUTO_DESCRICAO", "%%");
			} else {
				parametros.put("PRODUTO_DESCRICAO", "%" + proDescricao + "%");
			}
			if (fabDescricao == null) {
				parametros.put("FABRICANTE_DESCRICAO", "%%");
			} else {
				parametros.put("FABRICANTE_DESCRICAO", "%" + fabDescricao + "%");
			}

			Connection conexao = HibernateUtil.getConexoes();
			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException error) {
			Messages.addGlobalError("Erro ao tentar fazer relatório");
			error.printStackTrace();
		}
	}

	public void imagemDownload(ActionEvent evento) {

		try {
			this.produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			InputStream stream = new FileInputStream("C:/Users/deads/Desktop/uploads/" + produto.getCodigo() + ".png");
			this.downloadFoto = new DefaultStreamedContent(stream, "image/png", produto.getCodigo() + ".png");
		} catch (FileNotFoundException e) {
			Messages.addGlobalError("Erro ao efetuar download do produto");
			e.printStackTrace();
		}
	}

}
