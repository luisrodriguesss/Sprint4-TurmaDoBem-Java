package br.com.fiap.bo;

import br.com.fiap.dao.TriagemDao;
import br.com.fiap.entities.Triagem;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TriagemBO {

    private TriagemDao dao;

    public TriagemBO() throws SQLException, ClassNotFoundException {
        this.dao = new TriagemDao();
    }

    public void cadastrarComValidacao(Triagem t) throws SQLException, ErroNegocioException {
        if (t.getData() == null || t.getData().isAfter(LocalDate.now())) {
            throw new ErroNegocioException("A data da triagem nao pode ser futura.");
        }
        if (t.getLocal() == null || t.getLocal().trim().isEmpty()) {
            throw new ErroNegocioException("O local da triagem e obrigatorio.");
        }
        if (t.getCriterios() == null || t.getCriterios().trim().isEmpty()) {
            throw new ErroNegocioException("Os criterios da triagem sao obrigatorios.");
        }
        if (t.getIdBeneficiario() <= 0) {
            throw new ErroNegocioException("ID do beneficiario invalido.");
        }
        if (t.getIdDentista() <= 0) {
            throw new ErroNegocioException("ID do dentista invalido.");
        }
        dao.inserir(t);
    }

    public String classificarRisco(Triagem t) {
        String criterios = t.getCriterios().toLowerCase();
        if (criterios.contains("dor intensa") || criterios.contains("abscesso") ||
                criterios.contains("sangramento") || criterios.contains("infeccao")) {
            return "RISCO ALTO - Encaminhar para atendimento imediato.";
        } else if (criterios.contains("carie") || criterios.contains("sensibilidade") ||
                criterios.contains("inflamacao")) {
            return "RISCO MEDIO - Atendimento em breve.";
        } else {
            return "RISCO BAIXO - Atendimento eletivo.";
        }
    }

    public List<Triagem> listarTodas() throws SQLException { return dao.selecionar(); }
    public Triagem buscarPorId(int id) throws SQLException { return dao.selecionarPorId(id); }
    public void atualizar(Triagem t) throws SQLException { dao.atualizar(t); }
    public void deletar(int id) throws SQLException { dao.deletar(id); }
}