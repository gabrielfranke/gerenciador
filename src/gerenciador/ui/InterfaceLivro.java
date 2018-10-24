package gerenciador.ui;

import java.util.List;

import gerenciador.dao.LivroDao;
import gerenciador.modelo.Livro;

public class InterfaceLivro extends InterfaceModeloTexto {
	
	private LivroDao dao;
	
	public InterfaceLivro() {
		super();
		dao = new LivroDao();
	}
	
	private Livro obtemDadosLivro(Livro livro) {
		
		System.out.print("Insira o id do livro: ");
		int id = entrada.nextInt();
		entrada.nextLine();
		
		System.out.println("Insira o Titulo do livro: ");
		String titulo = entrada.nextLine();
		entrada.nextLine();
		
		System.out.println("Insira o ano da publicação do livro: ");
		int anoPublicacao = entrada.nextInt();
		entrada.nextInt();
		
		System.out.println("Insira a editora do livro: ");
		String editora = entrada.nextLine();
		
		System.out.println("Insira o autor do livro: ");
		String autor = entrada.nextLine();
		entrada.nextLine();
		
		Livro novoLivro = new Livro(id, titulo, anoPublicacao, editora, autor);
		
		return novoLivro;
	}
	
	@Override
	public void adicionar() {
		System.out.println("Adicionar livro");
		System.out.println();
		
		Livro novoLivro = obtemDadosLivro(null);	
		dao.insert(novoLivro);
		
	}

	@Override
	public void listarTodos() {
		List<Livro> livro = dao.getAll();
		
		System.out.println("Lista dos Livros");
		System.out.println();
		
		System.out.println("id\tTitulo\tAnoPublicacao\tEditora\tAutor");
		
		for (Livro livro : livro) {
			imprimeItem(livro);
		}
		
	}

	@Override
	public void editar() {
		listarTodos();
		
		System.out.println("Editar livro");
		System.out.println();
		
		System.out.print("Entre o id do livro: ");
		int id = entrada.nextInt();
		entrada.nextLine();
		
		Livro livroAModifcar = dao.getByKey(id);
		
		Livro novoLivro = obtemDadosLivro(livroAModifcar);
		
		novoLivro.setId(id);
		
		dao.update(novoLivro);
		
	}

	@Override
	public void excluir() {
		listarTodos();
		
		System.out.println("Excluir livros");
		System.out.println();
		
		System.out.print("Entre o id do livro: ");
		int id = entrada.nextInt();
		entrada.nextLine();
		
		dao.delete(id);
		
	}

}
