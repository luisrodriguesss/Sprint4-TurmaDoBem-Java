package br.com.fiap.bo;

import br.com.fiap.dao.DoacaoDao;
import br.com.fiap.entities.Doacao;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DoacaoBO {

    private DoacaoDao dao;

    public DoacaoBO() throws SQLException, ClassNotFoundException {
        dao = new DoacaoDao();
    }

    public void registrarComValidacao(Doacao d)
            throws SQLException, ErroNegocioException {

        if (d.getValor() <= 0) {
            throw new ErroNegocioException(
                    "O valor da doacao deve ser maior que zero."
            );
        }

        if (!d.getTipo().equalsIgnoreCase("Dinheiro") &&
                !d.getTipo().equalsIgnoreCase("Material")) {

            throw new ErroNegocioException(
                    "Tipo invalido."
            );
        }

        if (d.getData() == null ||
                d.getData().isAfter(LocalDate.now())) {

            throw new ErroNegocioException(
                    "Data invalida."
            );
        }

        dao.inserir(d);
    }

    public double calcularTotalDoacoes() throws SQLException {

        List<Doacao> lista = dao.selecionar();

        double total = 0;

        for (Doacao d : lista) {
            total += d.getValor();
        }

        return total;
    }

    public List<Doacao> listarTodas() throws SQLException {
        return dao.selecionar();
    }

    public Doacao buscarPorId(int id) throws SQLException {
        return dao.selecionarPorId(id);
    }

    public void atualizar(Doacao d) throws SQLException {
        dao.atualizar(d);
    }

    public void deletar(int id) throws SQLException {
        dao.deletar(id);
    }

    public double calcularTotalPorTipo(String tipo) throws SQLException {
        List<Doacao> lista = dao.selecionar();
        double total = 0;
        for (Doacao d : lista) {
            if (d.getTipo().equalsIgnoreCase(tipo)) {
                total += d.getValor();
            }
        }
        return total;
    }
}