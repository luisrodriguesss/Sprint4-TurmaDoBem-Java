package br.com.fiap.bo;

import br.com.fiap.dao.AgendamentoDao;
import br.com.fiap.entities.Agendamento;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AgendamentoBO {

    private AgendamentoDao dao;

    public AgendamentoBO() throws SQLException, ClassNotFoundException {
        this.dao = new AgendamentoDao();
    }

    public void cadastrarComValidacao(Agendamento a) throws SQLException, ErroNegocioException {
        if (a.getData() == null || a.getData().isBefore(LocalDate.now())) {
            throw new ErroNegocioException("A data do agendamento nao pode ser no passado.");
        }
        if (a.getUrgencia() == null || a.getUrgencia().trim().isEmpty()) {
            throw new ErroNegocioException("A urgencia e obrigatoria.");
        }
        if (!a.getUrgencia().equalsIgnoreCase("BAIXA") &&
                !a.getUrgencia().equalsIgnoreCase("MEDIA") &&
                !a.getUrgencia().equalsIgnoreCase("ALTA")) {
            throw new ErroNegocioException("Urgencia invalida. Use BAIXA, MEDIA ou ALTA.");
        }
        if (a.getIdDentista() <= 0) {
            throw new ErroNegocioException("ID do dentista invalido.");
        }
        if (a.getIdBeneficiario() <= 0) {
            throw new ErroNegocioException("ID do beneficiario invalido.");
        }
        dao.inserir(a);
    }

    public String classificarPrioridade(Agendamento a) {
        if (a.getUrgencia().equalsIgnoreCase("ALTA")) {
            return "PRIORIDADE MAXIMA - Atendimento imediato necessario.";
        } else if (a.getUrgencia().equalsIgnoreCase("MEDIA")) {
            return "PRIORIDADE MEDIA - Atendimento em ate 48 horas.";
        } else {
            return "PRIORIDADE BAIXA - Agendamento normal.";
        }
    }

    public List<Agendamento> listarTodos() throws SQLException { return dao.selecionar(); }
    public Agendamento buscarPorId(int id) throws SQLException { return dao.selecionarPorId(id); }
    public void atualizar(Agendamento a) throws SQLException { dao.atualizar(a); }
    public void deletar(int id) throws SQLException { dao.deletar(id); }
}
