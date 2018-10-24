package gerenciador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gerenciador.modelo.Autor;
import gerenciador.modelo.Livro;

public class AutorDao implements Dao<Autor> {
	
	private static final String GET_BY_ID = "SELECT * FROM autor NATURAL JOIN livro WHERE id = ?";
	private static final String GET_ALL = "SELECT * FROM autor NATURAL JOIN livro";
	private static final String INSERT = "INSERT INTO autor (id, nome, cpf) "
			+ "VALUES (?, ?, ?)";
	private static final String UPDATE = "UPDATE conta SET id = ?, nome = ?, cpf = ? ";
	private static final String DELETE = "DELETE FROM autor WHERE id = ?";
	
	public AutorDao() {
		try {
			createTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createTable() throws SQLException {
	    final String sqlCreate = "CREATE TABLE IF NOT EXISTS autor"
	            + "  (autor_id           INTEGER,"
	            + "   nome      INTEGER,"
	            + "   cpf	   INTEGER,"
	            + "   FOREIGN KEY (autor_id) REFERENCES autor(id),"
	            + "   PRIMARY KEY (id))";
	    
	    Connection conn = DbConnection.getConnection();

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	
	private Autor getAutorFromRS(ResultSet rs) throws SQLException
    {
		Autor autor = new Autor();
			
		autor.setId( rs.getInt("autor_id") );
		autor.setNome( rs.getString("nome") );
		autor.setCpf( rs.getInt("cpf"));
	
		return autor;
    }
	
	@Override
	public Autor getByKey(int id) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Autor autor = null;
		
		try {
			stmt = conn.prepareStatement(GET_BY_ID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				autor = getAutorFromRS(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
		
		return autor;
	}

	@Override
	public List<Autor> getAll() {
		Connection conn = DbConnection.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Autor> autor = new ArrayList<>();
		
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(GET_ALL);
			
			while (rs.next()) {
				autor.add(getAutorFromRS(rs));
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
		
		return autor;
	}

	@Override
	public void insert(Autor autor) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, autor.getId());
			stmt.setString(2, autor.getNome());
			stmt.setDouble(3, autor.getCpf());
			
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				autor.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
			e.printStackTrace();
		} finally {
			close(conn, stmt, null);
		}
	}

	@Override
	public void update(Autor autor) {
		Connection conn = DbConnection.getConnection();
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(UPDATE);
			stmt.setInt(1, autor.getId());
			stmt.setString(2, autor.getNome());
			stmt.setDouble(3, autor.getCpf());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close (conn, stmt, null);
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
