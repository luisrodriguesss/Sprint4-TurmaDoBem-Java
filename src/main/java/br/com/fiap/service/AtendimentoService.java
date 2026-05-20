package br.com.fiap.service;

import br.com.fiap.bo.AtendimentoBO;
import br.com.fiap.bo.BeneficiarioBO;
import br.com.fiap.bo.DentistaBO;
import br.com.fiap.entities.Atendimento;
import br.com.fiap.entities.Beneficiario;
import br.com.fiap.entities.Dentista;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AtendimentoService {

    private AtendimentoBO bo;
    private BeneficiarioBO beneficiarioBO;
    private DentistaBO dentistaBO;

    public AtendimentoService(BeneficiarioBO beneficiarioBO, DentistaBO dentistaBO) throws SQLException, ClassNotFoundException {
        this.bo = new AtendimentoBO();
        this.beneficiarioBO = beneficiarioBO;
        this.dentistaBO = dentistaBO;
    }

    public void exibir(Scanner sc) throws SQLException, ErroNegocioException {
        System.out.println("\n1 - Cadastrar atendimento");
        System.out.println("2 - Listar atendimentos");
        System.out.print("Escolha: ");
        int sub = Integer.parseInt(sc.nextLine());

        if (sub == 1) {
            List<Beneficiario> beneficiarios = beneficiarioBO.listarTodos();
            if (beneficiarios.isEmpty()) {
                System.out.println("Cadastre um beneficiario primeiro!");
                return;
            }
            System.out.println("Beneficiarios disponiveis:");
            for (Beneficiario b : beneficiarios) {
                System.out.println("  ID " + b.getId() + " - " + b.getNome());
            }
            System.out.print("ID do beneficiario: ");
            int idBenef = Integer.parseInt(sc.nextLine());

            List<Dentista> dentistas = dentistaBO.listarTodos();
            if (dentistas.isEmpty()) {
                System.out.println("Cadastre um dentista primeiro!");
                return;
            }
            System.out.println("Dentistas disponiveis:");
            for (Dentista d : dentistas) {
                System.out.println("  ID " + d.getId() + " - " + d.getNome());
            }
            System.out.print("ID do dentista: ");
            int idDentista = Integer.parseInt(sc.nextLine());

            System.out.print("Descricao do atendimento: ");
            String descricao = sc.nextLine();
            System.out.print("Receita medica (ou Enter para pular): ");
            String receita = sc.nextLine();

            Atendimento a = new Atendimento(0, LocalDate.now(), receita, descricao, idBenef, idDentista);
            bo.cadastrarComValidacao(a);
            System.out.println("Atendimento cadastrado!");

        } else if (sub == 2) {
            List<Atendimento> atendimentos = bo.listarTodos();
            if (atendimentos.isEmpty()) {
                System.out.println("Nenhum atendimento cadastrado.");
            } else {
                for (Atendimento a : atendimentos) {
                    System.out.println(a);
                }
            }
        }
    }
}