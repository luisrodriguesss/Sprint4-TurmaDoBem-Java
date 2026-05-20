package br.com.fiap.service;

import br.com.fiap.bo.AtendimentoBO;
import br.com.fiap.bo.ProcedimentoBO;
import br.com.fiap.entities.Atendimento;
import br.com.fiap.entities.Procedimento;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProcedimentoService {

    private ProcedimentoBO bo;
    private AtendimentoBO atendimentoBO;

    public ProcedimentoService() throws SQLException, ClassNotFoundException {
        this.bo = new ProcedimentoBO();
        this.atendimentoBO = new AtendimentoBO();
    }

    public void exibir(Scanner sc) throws SQLException, ErroNegocioException {
        System.out.println("\n1 - Cadastrar procedimento");
        System.out.println("2 - Listar procedimentos");
        System.out.print("Escolha: ");
        int sub = Integer.parseInt(sc.nextLine());

        if (sub == 1) {
            List<Atendimento> atendimentos = atendimentoBO.listarTodos();
            if (atendimentos.isEmpty()) {
                System.out.println("Nenhum atendimento cadastrado. Cadastre um atendimento primeiro!");
                return;
            }
            System.out.println("Atendimentos disponíveis:");
            for (Atendimento a : atendimentos) {
                System.out.println("  ID " + a.getId() + " - " + a.getDescricao() + " | Data: " + a.getData());
            }
            System.out.print("ID do atendimento: ");
            int idAtendimento = Integer.parseInt(sc.nextLine());

            System.out.print("Nome do procedimento: ");
            String nome = sc.nextLine();
            System.out.print("Relatorio: ");
            String relatorio = sc.nextLine();

            Procedimento p = new Procedimento(0, nome, LocalDate.now(), relatorio, idAtendimento);
            bo.cadastrarComValidacao(p);
            System.out.println("Procedimento cadastrado!");
            System.out.println("Tipo: " + bo.classificarTipo(p));

        } else if (sub == 2) {
            List<Procedimento> procedimentos = bo.listarTodos();
            if (procedimentos.isEmpty()) {
                System.out.println("Nenhum procedimento cadastrado.");
            } else {
                for (Procedimento p : procedimentos) {
                    System.out.println(p);
                }
            }
        }
    }
}