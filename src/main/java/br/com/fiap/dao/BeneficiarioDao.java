package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Beneficiario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BeneficiarioDao {

    private Connection conn;
    public BeneficiarioDao() throws SQLException, ClassNotFoundException {
        this.conn = new ConexaoFactory().conexao();
    }

    public void inserir(Beneficiario b) throws SQLException {
        String sql = "INSERT INTO T_TDB_BENEFICIARIO (nm_beneficiario, cpf_beneficiario, " +
                "dta_nascimento_beneficiario, cep_beneficiario, desc_problema_beneficiario) " +
                "VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, b.getNome());
            stmt.setString(2, b.getCpf());
            stmt.setDate(3, Date.valueOf(b.getNascimento()));
            stmt.setString(4, b.getCep());
            stmt.setString(5, b.getProblema());
            stmt.execute();
        }
    }

    public List<Beneficiario> selecionar() throws SQLException {
        List<Beneficiario> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_TDB_BENEFICIARIO ORDER BY id_beneficiario";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Beneficiario b = new Beneficiario();
                b.setId(rs.getInt("id_beneficiario"));
                b.setNome(rs.getString("nm_beneficiario"));
                b.setCpf(rs.getString("cpf_beneficiario"));
                b.setNascimento(rs.getDate("dta_nascimento_beneficiario").toLocalDate());
                b.setCep(rs.getString("cep_beneficiario"));
                b.setProblema(rs.getString("desc_problema_beneficiario"));
                lista.add(b);
            }
        }
        return lista;
    }

    public Beneficiario selecionarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_TDB_BENEFICIARIO WHERE id_beneficiario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Beneficiario b = new Beneficiario();
                    b.setId(rs.getInt("id_beneficiario"));
                    b.setNome(rs.getString("nm_beneficiario"));
                    b.setCpf(rs.getString("cpf_beneficiario"));
                    b.setNascimento(rs.getDate("dta_nascimento_beneficiario").toLocalDate());
                    b.setCep(rs.getString("cep_beneficiario"));
                    b.setProblema(rs.getString("desc_problema_beneficiario"));
                    return b;
                }
            }
        }
        return null;
    }

    public void atualizar(Beneficiario b) throws SQLException {
        String sql = "UPDATE T_TDB_BENEFICIARIO SET nm_beneficiario=?, cpf_beneficiario=?, " +
                "dta_nascimento_beneficiario=?, cep_beneficiario=?, desc_problema_beneficiario=? " +
                "WHERE id_beneficiario=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, b.getNome());
            stmt.setString(2, b.getCpf());
            stmt.setDate(3, Date.valueOf(b.getNascimento()));
            stmt.setString(4, b.getCep());
            stmt.setString(5, b.getProblema());
            stmt.setInt(6, b.getId());
            stmt.execute();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM T_TDB_BENEFICIARIO WHERE id_beneficiario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public void fecharConexao() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}
