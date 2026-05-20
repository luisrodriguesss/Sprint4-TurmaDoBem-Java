package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Atendimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtendimentoDao {

    private Connection conn;
    public AtendimentoDao() throws SQLException, ClassNotFoundException {
        this.conn = new ConexaoFactory().conexao();
    }

    public void inserir(Atendimento a) throws SQLException {
        String sql = "INSERT INTO T_TDB_ATENDIMENTO (dt_atendimento, receita_medica, desc_atendimento, id_beneficiario, id_dentista) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(a.getData()));
            stmt.setString(2, a.getReceita());
            stmt.setString(3, a.getDescricao());
            stmt.setInt(4, a.getIdBeneficiario());
            stmt.setInt(5, a.getIdDentista());
            stmt.execute();
        }
    }

    public List<Atendimento> selecionar() throws SQLException {
        List<Atendimento> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_TDB_ATENDIMENTO ORDER BY id_atendimento";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Atendimento a = new Atendimento();
                a.setId(rs.getInt("id_atendimento"));
                a.setData(rs.getDate("dt_atendimento").toLocalDate());
                a.setReceita(rs.getString("receita_medica"));
                a.setDescricao(rs.getString("desc_atendimento"));
                a.setIdBeneficiario(rs.getInt("id_beneficiario"));
                a.setIdDentista(rs.getInt("id_dentista"));
                lista.add(a);
            }
        }
        return lista;
    }

    public Atendimento selecionarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_TDB_ATENDIMENTO WHERE id_atendimento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Atendimento a = new Atendimento();
                    a.setId(rs.getInt("id_atendimento"));
                    a.setData(rs.getDate("dt_atendimento").toLocalDate());
                    a.setReceita(rs.getString("receita_medica"));
                    a.setDescricao(rs.getString("desc_atendimento"));
                    a.setIdBeneficiario(rs.getInt("id_beneficiario"));
                    a.setIdDentista(rs.getInt("id_dentista"));
                    return a;
                }
            }
        }
        return null;
    }

    public void atualizar(Atendimento a) throws SQLException {
        String sql = "UPDATE T_TDB_ATENDIMENTO SET dt_atendimento=?, receita_medica=?, desc_atendimento=?, id_beneficiario=?, id_dentista=? WHERE id_atendimento=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(a.getData()));
            stmt.setString(2, a.getReceita());
            stmt.setString(3, a.getDescricao());
            stmt.setInt(4, a.getIdBeneficiario());
            stmt.setInt(5, a.getIdDentista());
            stmt.setInt(6, a.getId());
            stmt.execute();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM T_TDB_ATENDIMENTO WHERE id_atendimento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public void fecharConexao() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}
