package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Procedimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedimentoDao {

    private Connection conn;

    public ProcedimentoDao() throws SQLException, ClassNotFoundException {
        this.conn = new ConexaoFactory().conexao();
    }

    public void inserir(Procedimento p) throws SQLException {

        String sql = "INSERT INTO T_TDB_PROCEDIMENTO " +
                "(nm_procedimento, dt_procedimento, relatorio_atendimento, id_atendimento) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setDate(2, Date.valueOf(p.getData()));
            stmt.setString(3, p.getRelatorio());
            stmt.setInt(4, p.getIdAtendimento());

            stmt.executeUpdate();
        }
    }

    public List<Procedimento> selecionar() throws SQLException {

        List<Procedimento> lista = new ArrayList<>();

        String sql = "SELECT * FROM T_TDB_PROCEDIMENTO ORDER BY id_procedimento";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Procedimento p = new Procedimento();

                p.setId(rs.getInt("id_procedimento"));
                p.setNome(rs.getString("nm_procedimento"));
                p.setData(rs.getDate("dt_procedimento").toLocalDate());
                p.setRelatorio(rs.getString("relatorio_atendimento"));
                p.setIdAtendimento(rs.getInt("id_atendimento"));

                lista.add(p);
            }
        }

        return lista;
    }

    public Procedimento selecionarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM T_TDB_PROCEDIMENTO WHERE id_procedimento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Procedimento p = new Procedimento();

                    p.setId(rs.getInt("id_procedimento"));
                    p.setNome(rs.getString("nm_procedimento"));
                    p.setData(rs.getDate("dt_procedimento").toLocalDate());
                    p.setRelatorio(rs.getString("relatorio_atendimento"));
                    p.setIdAtendimento(rs.getInt("id_atendimento"));

                    return p;
                }
            }
        }
        return null;
    }
    public void atualizar(Procedimento p) throws SQLException {

        String sql = "UPDATE T_TDB_PROCEDIMENTO " +
                "SET nm_procedimento = ?, dt_procedimento = ?, relatorio_atendimento = ?, id_atendimento = ? " +
                "WHERE id_procedimento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setDate(2, Date.valueOf(p.getData()));
            stmt.setString(3, p.getRelatorio());
            stmt.setInt(4, p.getIdAtendimento());
            stmt.setInt(5, p.getId());

            stmt.executeUpdate();
        }
    }
    public void deletar(int id) throws SQLException {

        String sql = "DELETE FROM T_TDB_PROCEDIMENTO WHERE id_procedimento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
    public void fecharConexao() throws SQLException {

        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}