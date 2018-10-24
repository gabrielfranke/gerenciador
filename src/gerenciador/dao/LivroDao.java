package gerenciador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gerenciador.modelo.Livro;

public class LivroDao implements Dao<Livro> {
	
	private static final String GET_BY_ID = "SELECT * FROM cliente WHERE id = ?"; // comandos sql, toda consulta fará alguma coisa. Neste caso ele chamará uma variável
	private static final String GET_ALL = "SELECT * FROM cliente";
	private static final String INSERT = "INSERT INTO cliente (nome, rg, cpf, endereco, telefone, renda_mensal) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE cliente SET nome = ?, rg = ?, cpf = ?, "
			+ "endereco = ?, telefone = ?, renda_mensal = ? WHERE id = ?";
	private static final String DELETE = "DELETE FROM cliente WHERE id = ?";
	
	public LivroDao() {
		try {
			createTable();
		} catch (SQLException e) { // tratamento de error se der error no createTable()
			throw new RuntimeException("Erro ao criar tabela no banco.", e);
			//e.printStackTrace();
		}
	}
	
	private void createTable() throws SQLException {
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS cliente"
	            + "  (id           INTEGER,"
	            + "   nome            VARCHAR(50),"
	            + "   rg	          BIGINT,"
	            + "   cpf			  BIGINT,"
	            + "   endereco           VARCHAR(255),"
	            + "   telefone           BIGINT,"
	            + "   renda_mensal       DOUBLE,"
	            + "   PRIMARY KEY (id))";
	    
	    Connection conn = DbConnection.getConnection(); // fecha a conexão com o banco


	    Statement stmt = conn.createStatement(); // permite executar comandos do sql
	    stmt.execute(sqlCreate);
	    
	    close(conn, stmt, null); // fecha a conexão com o banco
	}
	
	
	private Livro getClienteFromRS(ResultSet rs) throws SQLException // alguma coisa que o banco retorna. O rs é o resultado
    {
		Livro cliente = new Livro();
			
		cliente.setId( rs.getInt("id") );
		cliente.setTitulo( rs.getString("titulo") );
		cliente.setAnoPublicacao( rs.getInt("anoPublicacao") );
		cliente.setEditora( rs.getString("editora") );
		cliente.setAutor( rs.getString("autor") );
		
		return cliente;
    }
	
	@Override
	public Livro getByKey(int id) { 
		Connection conn = DbConnection.getConnection();
		
		Livro livro = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(GET_BY_ID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				livro = getClienteFromRS(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao obter o livro pela chave.", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return livro;
	}

	@Override
	public List<Livro> getAll() {
		Connection conn = DbConnection.getConnection();
		
		List<Livro> livros = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(GET_ALL);
			
			while (rs.next()) {
				livros.add(getClienteFromRS(rs));
			}			
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao obter todos os livros.", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return livros;
	}

	@Override
	public void insert(Livro livro) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, livro.getId());
			stmt.setString(2, livro.getTitulo());
			stmt.setInt(3, livro.getAnoPublicacao());
			stmt.setString(4, livro.getEditora());
			stmt.setString(5, livro.getAutor());
			
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				livro.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao inserir os livros.", e);
		}finally {
			close(conn, stmt, rs);
		}

	}

	@Override
	public void delete(int id) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(DELETE);
			
			stmt.setInt(1, id);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao remover os livros.", e);
		} finally {
			close(conn, stmt, null);
		}
	}

	@Override
	public void update(Livro livro) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(UPDATE);
			stmt.setInt(1, livro.getId());
			stmt.setString(2, livro.getTitulo());
			stmt.setInt(3, livro.getAnoPublicacao());
			stmt.setString(4, livro.getEditora());
			stmt.setString(5, livro.getAutor());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar os livros.", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar recursos.", e);
		}
		
	}

}
