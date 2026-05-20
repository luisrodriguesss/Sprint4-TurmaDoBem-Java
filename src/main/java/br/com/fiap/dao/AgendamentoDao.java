package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Agendamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDao {

    private Connection conn;

    public AgendamentoDao() throws SQLException, ClassNotFoundException {
        this.conn = new ConexaoFactory().conexao();
    }

    public void inserir(Agendamento a) throws SQLException {
        String sql = "INSERT INTO T_TDB_AGENDAMENTO " +
                "(dt_agendamento, horario_agendamento, urgencia_atendimento, id_dentista, id_beneficiario) " +
                "VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(a.getData()));
            stmt.setTimestamp(2, Timestamp.valueOf(a.getHorario()));
            stmt.setString(3, a.getUrgencia());
            stmt.setInt(4, a.getIdDentista());
            stmt.setInt(5, a.getIdBeneficiario());
            stmt.execute();
        }
    }

    public List<Agendamento> selecionar() throws SQLException {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_TDB_AGENDAMENTO ORDER BY id_agendamento";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Agendamento a = new Agendamento();
                a.setId(rs.getInt("id_agendamento"));
                a.setData(rs.getDate("dt_agendamento").toLocalDate());
                a.setHorario(rs.getTimestamp("horario_agendamento").toLocalDateTime());
                a.setUrgencia(rs.getString("urgencia_atendimento"));
                a.setIdDentista(rs.getInt("id_dentista"));
                a.setIdBeneficiario(rs.getInt("id_beneficiario"));
                lista.add(a);
            }
        }
        return lista;
    }

    public Agendamento selecionarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_TDB_AGENDAMENTO WHERE id_agendamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Agendamento a = new Agendamento();
                    a.setId(rs.getInt("id_agendamento"));
                    a.setData(rs.getDate("dt_agendamento").toLocalDate());
                    a.setHorario(rs.getTimestamp("horario_agendamento").toLocalDateTime());
                    a.setUrgencia(rs.getString("urgencia_atendimento"));
                    a.setIdDentista(rs.getInt("id_dentista"));
                    a.setIdBeneficiario(rs.getInt("id_beneficiario"));
                    return a;
                }
            }
        }
        return null;
    }

    public void atualizar(Agendamento a) throws SQLException {
        String sql = "UPDATE T_TDB_AGENDAMENTO " +
                "SET dt_agendamento=?, horario_agendamento=?, urgencia_atendimento=?, id_dentista=?, id_beneficiario=? " +
                "WHERE id_agendamento=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(a.getData()));
            stmt.setTimestamp(2, Timestamp.valueOf(a.getHorario()));
            stmt.setString(3, a.getUrgencia());
            stmt.setInt(4, a.getIdDentista());
            stmt.setInt(5, a.getIdBeneficiario());
            stmt.setInt(6, a.getId());
            stmt.execute();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM T_TDB_AGENDAMENTO WHERE id_agendamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public void fecharConexao() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}
