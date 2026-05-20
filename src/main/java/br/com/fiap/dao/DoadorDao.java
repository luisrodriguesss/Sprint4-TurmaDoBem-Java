package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Doador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoadorDao {

    private Connection conn;
    public DoadorDao() throws SQLException, ClassNotFoundException {
        this.conn = new ConexaoFactory().conexao();
    }

    public void inserir(Doador d) throws SQLException {
        String sql = "INSERT INTO T_TDB_DOADOR (nm_doador, cpf_doador, tel_doador, email_doador) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getCpf());
            stmt.setString(3, d.getTelefone());
            stmt.setString(4, d.getEmail());
            stmt.execute();
        }
    }

    public List<Doador> selecionar() throws SQLException {
        List<Doador> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_TDB_DOADOR ORDER BY id_doador";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Doador d = new Doador();
                d.setId(rs.getInt("id_doador"));
                d.setNome(rs.getString("nm_doador"));
                d.setCpf(rs.getString("cpf_doador"));
                d.setTelefone(rs.getString("tel_doador"));
                d.setEmail(rs.getString("email_doador"));
                lista.add(d);
            }
        }
        return lista;
    }

    public Doador selecionarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_TDB_DOADOR WHERE id_doador = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Doador d = new Doador();
                    d.setId(rs.getInt("id_doador"));
                    d.setNome(rs.getString("nm_doador"));
                    d.setCpf(rs.getString("cpf_doador"));
                    d.setTelefone(rs.getString("tel_doador"));
                    d.setEmail(rs.getString("email_doador"));
                    return d;
                }
            }
        }
        return null;
    }

    public void atualizar(Doador d) throws SQLException {
        String sql = "UPDATE T_TDB_DOADOR SET nm_doador=?, cpf_doador=?, tel_doador=?, email_doador=? WHERE id_doador=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getCpf());
            stmt.setString(3, d.getTelefone());
            stmt.setString(4, d.getEmail());
            stmt.setInt(5, d.getId());
            stmt.execute();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM T_TDB_DOADOR WHERE id_doador = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public void fecharConexao() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}
