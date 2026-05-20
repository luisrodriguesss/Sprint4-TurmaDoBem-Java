package br.com.fiap.bo;

import br.com.fiap.dao.DentistaDao;
import br.com.fiap.entities.Dentista;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.util.List;

public class DentistaBO {

    private DentistaDao dao;

    public DentistaBO() throws SQLException, ClassNotFoundException {
        this.dao = new DentistaDao();
    }

    public boolean validarCro(String cro) {
        if (cro == null || cro.trim().isEmpty()) return false;
        String croLimpo = cro.replaceAll("[^0-9]", "");
        return croLimpo.length() >= 4 && croLimpo.length() <= 6;
    }

    public void cadastrarComValidacao(Dentista d) throws SQLException, ErroNegocioException {
        if (d.getNome() == null || d.getNome().trim().isEmpty()) {
            throw new ErroNegocioException("Nome do dentista e obrigatorio.");
        }
        if (!validarCro(d.getCro())) {
            throw new ErroNegocioException("CRO invalido: " + d.getCro());
        }
        if (d.getEspecialidade() == null || d.getEspecialidade().trim().isEmpty()) {
            throw new ErroNegocioException("Especialidade e obrigatoria.");
        }

        List<Dentista> todos = dao.selecionar();
        for (Dentista existente : todos) {
            if (existente.getCro().equals(d.getCro())) {
                throw new ErroNegocioException("Ja existe um dentista com esse CRO: " + d.getCro());
            }
        }

        dao.inserir(d);
    }

    public List<Dentista> buscarPorEspecialidade(String especialidade) throws SQLException {
        List<Dentista> todos = dao.selecionar();
        List<Dentista> resultado = new java.util.ArrayList<>();
        for (Dentista d : todos) {
            if (d.getEspecialidade().equalsIgnoreCase(especialidade)) {
                resultado.add(d);
            }
        }
        return resultado;
    }

    public List<Dentista> listarTodos() throws SQLException { return dao.selecionar(); }
    public Dentista buscarPorId(int id) throws SQLException { return dao.selecionarPorId(id); }
    public void atualizar(Dentista d) throws SQLException { dao.atualizar(d); }
    public void deletar(int id) throws SQLException { dao.deletar(id); }
}