package br.com.fiap.bo;

import br.com.fiap.dao.ProcedimentoDao;
import br.com.fiap.entities.Procedimento;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProcedimentoBO {

    private ProcedimentoDao dao;

    public ProcedimentoBO() throws SQLException, ClassNotFoundException {
        this.dao = new ProcedimentoDao();
    }

    public void cadastrarComValidacao(Procedimento p) throws SQLException, ErroNegocioException {
        if (p.getNome() == null || p.getNome().trim().isEmpty())
            throw new ErroNegocioException("O nome do procedimento e obrigatorio.");
        if (p.getData() == null || p.getData().isAfter(LocalDate.now()))
            throw new ErroNegocioException("A data do procedimento nao pode ser futura.");
        if (p.getRelatorio() == null || p.getRelatorio().trim().isEmpty())
            throw new ErroNegocioException("O relatorio do procedimento e obrigatorio.");
        dao.inserir(p);
    }

    public String classificarTipo(Procedimento p) {
        if (p.getNome() == null || p.getNome().trim().isEmpty())
            return "TIPO NAO IDENTIFICADO.";

        String nome = p.getNome().toLowerCase();

        if (nome.contains("extracao") || nome.contains("cirurgia") || nome.contains("implante"))
            return "CIRURGICO - Requer acompanhamento pos-operatorio rigoroso.";
        if (nome.contains("limpeza") || nome.contains("profilaxia") || nome.contains("selante"))
            return "PREVENTIVO - Procedimento de manutencao da saude bucal.";
        if (nome.contains("restauracao") || nome.contains("obturacao") || nome.contains("canal"))
            return "RESTAURADOR - Procedimento de recuperacao dental.";
        if (nome.contains("ortodontia") || nome.contains("aparelho") || nome.contains("contencao"))
            return "ORTOPEDICO - Procedimento de correcao e alinhamento.";
        return "GERAL - Procedimento odontologico nao categorizado.";
    }

    public boolean possuiRelatorio(Procedimento p) {
        return p.getRelatorio() != null && !p.getRelatorio().trim().isEmpty();
    }

    public List<Procedimento> listarTodos() throws SQLException { return dao.selecionar(); }
    public Procedimento buscarPorId(int id) throws SQLException { return dao.selecionarPorId(id); }
    public void atualizar(Procedimento p) throws SQLException { dao.atualizar(p); }
    public void deletar(int id) throws SQLException { dao.deletar(id); }
}