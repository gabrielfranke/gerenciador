package gerenciador.modelo;


public class Autor implements Imprimivel {
	private int id;
	private String nome;
	private double cpf;

	
	public Autor() { }
	
	public Autor(int id, String nome, double cpf) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}
	


	@Override
	public String imprimeEmLista() {
		return String.format("%d\t%d\t%d\t%.2f\t%d\t%s", getId(), getNome(), getCpf());
	}

	@Override
	public String[] getColunas() {
		String[] colunas = {"id", "Nome", "CPF"};
		return colunas;
	}
	
	
	
}
