package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Doacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoacaoDao {

    private Connection conn;
    public DoacaoDao() throws SQLException, ClassNotFoundException {
        this.conn = new ConexaoFactory().conexao();
    }

    public void inserir(Doacao d) throws SQLException {
        String sql = "INSERT INTO T_TDB_DOACAO (nm_doador, tipo_doacao, valor_doacao, dt_doacao, id_doador) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getNomeDoador());
            stmt.setString(2, d.getTipo());
            stmt.setDouble(3, d.getValor());
            stmt.setDate(4, Date.valueOf(d.getData()));
            stmt.setInt(5, d.getIdDoador());
            stmt.execute();
        }
    }

    public List<Doacao> selecionar() throws SQLException {
        List<Doacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_TDB_DOACAO ORDER BY id_doacao";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Doacao d = new Doacao();
                d.setId(rs.getInt("id_doacao"));
                d.setNomeDoador(rs.getString("nm_doador"));
                d.setTipo(rs.getString("tipo_doacao"));
                d.setValor(rs.getDouble("valor_doacao"));
                d.setData(rs.getDate("dt_doacao").toLocalDate());
                d.setIdDoador(rs.getInt("id_doador"));
                lista.add(d);
            }
        }
        return lista;
    }

    public Doacao selecionarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_TDB_DOACAO WHERE id_doacao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Doacao d = new Doacao();
                    d.setId(rs.getInt("id_doacao"));
                    d.setNomeDoador(rs.getString("nm_doador"));
                    d.setTipo(rs.getString("tipo_doacao"));
                    d.setValor(rs.getDouble("valor_doacao"));
                    d.setData(rs.getDate("dt_doacao").toLocalDate());
                    d.setIdDoador(rs.getInt("id_doador"));
                    return d;
                }
            }
        }
        return null;
    }

    public void atualizar(Doacao d) throws SQLException {
        String sql = "UPDATE T_TDB_DOACAO SET nm_doador=?, tipo_doacao=?, valor_doacao=?, dt_doacao=?, id_doador=? WHERE id_doacao=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getNomeDoador());
            stmt.setString(2, d.getTipo());
            stmt.setDouble(3, d.getValor());
            stmt.setDate(4, Date.valueOf(d.getData()));
            stmt.setInt(5, d.getIdDoador());
            stmt.setInt(6, d.getId());
            stmt.execute();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM T_TDB_DOACAO WHERE id_doacao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public void fecharConexao() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}
