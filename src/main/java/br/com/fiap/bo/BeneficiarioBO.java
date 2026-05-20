package br.com.fiap.bo;

import br.com.fiap.dao.BeneficiarioDao;
import br.com.fiap.entities.Beneficiario;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class BeneficiarioBO {

    private BeneficiarioDao dao;

    public BeneficiarioBO() throws SQLException, ClassNotFoundException {
        this.dao = new BeneficiarioDao();
    }

    public String verificarElegibilidade(Beneficiario b) {
        int idade = Period.between(b.getNascimento(), LocalDate.now()).getYears();
        if (idade < 18)
            return "PRIORITARIO - Menor de idade (" + idade + " anos).";
        if (idade >= 60)
            return "PRIORITARIO - Idoso (" + idade + " anos). Estatuto do Idoso.";
        return "PADRAO - Adulto (" + idade + " anos). Fila comum.";
    }

    public boolean validarCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) return false;
        if (cpf.chars().distinct().count() == 1) return false;

        int soma = 0;
        for (int i = 0; i < 9; i++) soma += (cpf.charAt(i) - '0') * (10 - i);
        int primeiro = 11 - (soma % 11);
        if (primeiro >= 10) primeiro = 0;

        soma = 0;
        for (int i = 0; i < 10; i++) soma += (cpf.charAt(i) - '0') * (11 - i);
        int segundo = 11 - (soma % 11);
        if (segundo >= 10) segundo = 0;

        return (cpf.charAt(9) - '0') == primeiro && (cpf.charAt(10) - '0') == segundo;
    }

    public void cadastrarComValidacao(Beneficiario b) throws SQLException, ErroNegocioException {
        if (b.getNome() == null || b.getNome().trim().isEmpty())
            throw new ErroNegocioException("Nome do beneficiario e obrigatorio.");
        if (!validarCpf(b.getCpf()))
            throw new ErroNegocioException("CPF invalido: " + b.getCpf());
        if (b.getNascimento() == null || b.getNascimento().isAfter(LocalDate.now()))
            throw new ErroNegocioException("Data de nascimento invalida.");

        List<Beneficiario> todos = dao.selecionar();
        for (Beneficiario existente : todos) {
            if (existente.getCpf().replaceAll("[^0-9]", "").equals(b.getCpf().replaceAll("[^0-9]", "")))
                throw new ErroNegocioException("Ja existe um beneficiario com esse CPF: " + b.getCpf());
        }

        dao.inserir(b);
    }

    public List<Beneficiario> listarCasosUrgentes() throws SQLException {
        List<Beneficiario> todos = dao.selecionar();
        List<Beneficiario> urgentes = new ArrayList<>();
        String[] palavras = {"dor intensa", "abscesso", "urgente", "sangramento", "infeccao", "fratura"};

        for (Beneficiario b : todos) {
            String prob = b.getProblema().toLowerCase();
            for (String p : palavras) {
                if (prob.contains(p)) {
                    urgentes.add(b);
                    break;
                }
            }
        }
        return urgentes;
    }

    public List<Beneficiario> listarTodos() throws SQLException { return dao.selecionar(); }
    public Beneficiario buscarPorId(int id) throws SQLException { return dao.selecionarPorId(id); }
    public void atualizar(Beneficiario b) throws SQLException { dao.atualizar(b); }
    public void deletar(int id) throws SQLException { dao.deletar(id); }
}