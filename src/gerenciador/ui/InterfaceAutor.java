package gerenciador.ui;

import java.util.List;

import gerenciador.dao.AutorDao;
import gerenciador.dao.LivroDao;
import gerenciador.modelo.Autor;
import gerenciador.modelo.Livro;

public class InterfaceAutor extends InterfaceModeloTexto {

	private AutorDao dao;
	private LivroDao livroDao;
	
	public InterfaceAutor() {
		super();
		dao = new AutorDao();
		livroDao = new LivroDao();
	}
	
	@Override
	public void adicionar() {
		System.out.println("Adicionar autor");
		System.out.println();
		
		Autor novoAutor = obtemDadosAutor(null);	
		dao.insert(novoAutor);
	}

	private Autor obtemDadosAutor(Autor autor) {
		
		System.out.print("Insira o ID do autor: ");
		int id_autor = entrada.nextInt();
		
		Livro livro = livroDao.getByKey(id_autor);
		
		/* System.out.print("Insira o id do autor: ");
		int numero = entrada.nextInt();*/
		
		System.out.print("Insira o nome do autor: ");
		String nome = entrada.nextLine();
		
		System.out.print("Insira o CPF: ");
		int cpf = entrada.nextInt();
	
		
		return new Autor(id_autor, nome, cpf);
	}

	@Override
	public void listarTodos() {
		List<Autor> autores = dao.getAll();
		
		System.out.println("Lista autores");
		System.out.println();
		
		System.out.println("id\tNome\tCpf");
		
		for (Autor autor : autores) {
			imprimeItem(autor);
		}
	}

	@Override
	public void editar() {
		listarTodos();
		
		System.out.println("Editar autor");
		System.out.println();
		
		System.out.print("Entre o id do autor: ");
		int id_autor = entrada.nextInt();
		entrada.nextLine();
		
		Autor AutorAModificar = dao.getByKey(id_autor);
		
		Autor novoAutor = obtemDadosAutor(AutorAModificar);
		
		novoAutor.setId(id_autor);
		
		dao.update(novoAutor);
	}

	@Override
	public void excluir() {
		listarTodos();
		
		System.out.println("Excluir autor");
		System.out.println();
		
		System.out.print("Entre o id do autor: ");
		int id_autor = entrada.nextInt();
		entrada.nextLine();
		
		dao.delete(id_autor);
	}

}
