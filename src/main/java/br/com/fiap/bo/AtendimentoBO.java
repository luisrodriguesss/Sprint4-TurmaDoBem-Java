package br.com.fiap.bo;

import br.com.fiap.dao.AtendimentoDao;
import br.com.fiap.entities.Atendimento;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AtendimentoBO {

    private AtendimentoDao dao;

    public AtendimentoBO() throws SQLException, ClassNotFoundException {
        this.dao = new AtendimentoDao();
    }

    public void cadastrarComValidacao(Atendimento a) throws SQLException, ErroNegocioException {
        if (a.getData() == null || a.getData().isAfter(LocalDate.now()))
            throw new ErroNegocioException("A data do atendimento nao pode ser futura.");
        if (a.getDescricao() == null || a.getDescricao().trim().isEmpty())
            throw new ErroNegocioException("A descricao do atendimento e obrigatoria.");
        if (a.getIdBeneficiario() <= 0)
            throw new ErroNegocioException("ID do beneficiario invalido.");
        if (a.getIdDentista() <= 0)
            throw new ErroNegocioException("ID do dentista invalido.");
        dao.inserir(a);
    }

    public String classificarAntiguidade(Atendimento a) {
        if (a.getData() == null) return "DATA NAO INFORMADA.";
        long dias = ChronoUnit.DAYS.between(a.getData(), LocalDate.now());
        if (dias <= 7)
            return "ATENDIMENTO RECENTE - Realizado ha " + dias + " dia(s). Monitoramento recomendado.";
        if (dias <= 30)
            return "ATENDIMENTO EM ACOMPANHAMENTO - Realizado ha " + dias + " dias. Verificar retorno.";
        return "ATENDIMENTO ANTIGO - Realizado ha " + dias + " dias. Avaliar necessidade de novo agendamento.";
    }

    public boolean possuiReceita(Atendimento a) {
        return a.getReceita() != null && !a.getReceita().trim().isEmpty();
    }

    public List<Atendimento> listarTodos() throws SQLException { return dao.selecionar(); }
    public Atendimento buscarPorId(int id) throws SQLException { return dao.selecionarPorId(id); }
    public void atualizar(Atendimento a) throws SQLException { dao.atualizar(a); }
    public void deletar(int id) throws SQLException { dao.deletar(id); }
}