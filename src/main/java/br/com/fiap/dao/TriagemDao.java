package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Triagem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TriagemDao {

    private Connection conn;
    public TriagemDao() throws SQLException, ClassNotFoundException {
        this.conn = new ConexaoFactory().conexao();
    }

    public void inserir(Triagem t) throws SQLException {
        String sql = "INSERT INTO T_TDB_TRIAGEM (dt_triagem, local_triagem, criterios_triagem, id_beneficiario, id_dentista) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(t.getData()));
            stmt.setString(2, t.getLocal());
            stmt.setString(3, t.getCriterios());
            stmt.setInt(4, t.getIdBeneficiario());
            stmt.setInt(5, t.getIdDentista());
            stmt.execute();
        }
    }

    public List<Triagem> selecionar() throws SQLException {
        List<Triagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_TDB_TRIAGEM ORDER BY id_triagem";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Triagem t = new Triagem();
                t.setId(rs.getInt("id_triagem"));
                t.setData(rs.getDate("dt_triagem").toLocalDate());
                t.setLocal(rs.getString("local_triagem"));
                t.setCriterios(rs.getString("criterios_triagem"));
                t.setIdBeneficiario(rs.getInt("id_beneficiario"));
                t.setIdDentista(rs.getInt("id_dentista"));
                lista.add(t);
            }
        }
        return lista;
    }

    public Triagem selecionarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_TDB_TRIAGEM WHERE id_triagem = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Triagem t = new Triagem();
                    t.setId(rs.getInt("id_triagem"));
                    t.setData(rs.getDate("dt_triagem").toLocalDate());
                    t.setLocal(rs.getString("local_triagem"));
                    t.setCriterios(rs.getString("criterios_triagem"));
                    t.setIdBeneficiario(rs.getInt("id_beneficiario"));
                    t.setIdDentista(rs.getInt("id_dentista"));
                    return t;
                }
            }
        }
        return null;
    }

    public void atualizar(Triagem t) throws SQLException {
        String sql = "UPDATE T_TDB_TRIAGEM SET dt_triagem=?, local_triagem=?, criterios_triagem=?, id_beneficiario=?, id_dentista=? WHERE id_triagem=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(t.getData()));
            stmt.setString(2, t.getLocal());
            stmt.setString(3, t.getCriterios());
            stmt.setInt(4, t.getIdBeneficiario());
            stmt.setInt(5, t.getIdDentista());
            stmt.setInt(6, t.getId());
            stmt.execute();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM T_TDB_TRIAGEM WHERE id_triagem = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public void fecharConexao() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}
