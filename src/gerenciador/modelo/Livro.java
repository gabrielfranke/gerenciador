package gerenciador.modelo;


public class Livro implements Imprimivel {
	private int id;
	private String titulo;
	private int anoPublicacao;
	private String editora;
	private String autor;
	
	public Livro() { }
	
	public Livro(int id, String Titulo, int anoPublicacao, String editora, String autor, Livro livro) {
		this.id = id;
		this.titulo = titulo;
		this.anoPublicacao = anoPublicacao;
		this.editora = editora;
		this.autor = autor;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}


	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(int anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}
	
	public String getEditora() {
		return editora;
	}
	
	public void setEditora(String editora) {
		this.editora = editora;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}


	@Override
	public String imprimeEmLista() {
		return String.format("%d\t%d\t%d\t%.2f\t%d\t%s", getId(), getTitulo(), getAnoPublicacao(), getEditora(), getAutor());
	}

	@Override
	public String[] getColunas() {
		String[] colunas = {"id", "Titulo", "Ano Publicacao", "Editora", "Autor"};
		return colunas;
	}
	
	
}
