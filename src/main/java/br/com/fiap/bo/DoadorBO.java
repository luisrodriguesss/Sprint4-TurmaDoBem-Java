package br.com.fiap.bo;

import br.com.fiap.dao.DoadorDao;
import br.com.fiap.entities.Doador;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.util.List;

public class DoadorBO {

    private DoadorDao dao;

    public DoadorBO() throws SQLException, ClassNotFoundException {
        this.dao = new DoadorDao();
    }

    public void cadastrarComValidacao(Doador d) throws SQLException, ErroNegocioException {
        if (d.getNome() == null || d.getNome().trim().isEmpty())
            throw new ErroNegocioException("Nome do doador e obrigatorio.");
        if (d.getCpf() == null || d.getCpf().trim().isEmpty())
            throw new ErroNegocioException("CPF do doador e obrigatorio.");
        if (d.getEmail() == null || d.getEmail().trim().isEmpty())
            throw new ErroNegocioException("Email do doador e obrigatorio.");
        if (!validarEmail(d.getEmail()))
            throw new ErroNegocioException("Email invalido: " + d.getEmail());

        List<Doador> todos = dao.selecionar();
        for (Doador existente : todos) {
            if (existente.getCpf().equals(d.getCpf()))
                throw new ErroNegocioException("Ja existe um doador com esse CPF: " + d.getCpf());
        }

        dao.inserir(d);
    }

    public boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return email.contains("@") && email.contains(".") && email.indexOf("@") < email.lastIndexOf(".");
    }

    public String classificarPerfil(Doador d) {
        boolean temTelefone = d.getTelefone() != null && !d.getTelefone().trim().isEmpty();
        boolean temEmail = d.getEmail() != null && !d.getEmail().trim().isEmpty();

        if (temTelefone && temEmail)
            return "PERFIL COMPLETO - Doador pode ser contatado por telefone e email.";
        if (temEmail)
            return "PERFIL PARCIAL - Doador disponivel apenas por email.";
        if (temTelefone)
            return "PERFIL PARCIAL - Doador disponivel apenas por telefone.";
        return "PERFIL INCOMPLETO - Nenhum contato adicional informado.";
    }

    public List<Doador> listarTodos() throws SQLException { return dao.selecionar(); }
    public Doador buscarPorId(int id) throws SQLException { return dao.selecionarPorId(id); }
    public void atualizar(Doador d) throws SQLException { dao.atualizar(d); }
    public void deletar(int id) throws SQLException { dao.deletar(id); }
}