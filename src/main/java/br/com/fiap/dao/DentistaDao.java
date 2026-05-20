package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Dentista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DentistaDao {

    private Connection conn;
    public DentistaDao() throws SQLException, ClassNotFoundException {
        this.conn = new ConexaoFactory().conexao();
    }

    public void inserir(Dentista d) throws SQLException {
        String sql = "INSERT INTO T_TDB_DENTISTA (nm_dentista, cro_dentista, especialidade_dentista, cep_consultorio_dentista) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getCro());
            stmt.setString(3, d.getEspecialidade());
            stmt.setString(4, d.getCep());
            stmt.execute();
        }
    }

    public List<Dentista> selecionar() throws SQLException {
        List<Dentista> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_TDB_DENTISTA ORDER BY id_dentista";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Dentista d = new Dentista();
                d.setId(rs.getInt("id_dentista"));
                d.setNome(rs.getString("nm_dentista"));
                d.setCro(rs.getString("cro_dentista"));
                d.setEspecialidade(rs.getString("especialidade_dentista"));
                d.setCep(rs.getString("cep_consultorio_dentista"));
                lista.add(d);
            }
        }
        return lista;
    }

    public Dentista selecionarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_TDB_DENTISTA WHERE id_dentista = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Dentista d = new Dentista();
                    d.setId(rs.getInt("id_dentista"));
                    d.setNome(rs.getString("nm_dentista"));
                    d.setCro(rs.getString("cro_dentista"));
                    d.setEspecialidade(rs.getString("especialidade_dentista"));
                    d.setCep(rs.getString("cep_consultorio_dentista"));
                    return d;
                }
            }
        }
        return null;
    }

    public void atualizar(Dentista d) throws SQLException {
        String sql = "UPDATE T_TDB_DENTISTA SET nm_dentista=?, cro_dentista=?, especialidade_dentista=?, cep_consultorio_dentista=? WHERE id_dentista=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getCro());
            stmt.setString(3, d.getEspecialidade());
            stmt.setString(4, d.getCep());
            stmt.setInt(5, d.getId());
            stmt.execute();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM T_TDB_DENTISTA WHERE id_dentista = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public void fecharConexao() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}
