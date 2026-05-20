package br.com.fiap.service;

import br.com.fiap.bo.BeneficiarioBO;
import br.com.fiap.bo.DentistaBO;
import br.com.fiap.bo.TriagemBO;
import br.com.fiap.entities.Beneficiario;
import br.com.fiap.entities.Dentista;
import br.com.fiap.entities.Triagem;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TriagemService {

    private TriagemBO bo;
    private BeneficiarioBO beneficiarioBO;
    private DentistaBO dentistaBO;

    public TriagemService(BeneficiarioBO beneficiarioBO, DentistaBO dentistaBO) throws SQLException, ClassNotFoundException {
        this.bo = new TriagemBO();
        this.beneficiarioBO = beneficiarioBO;
        this.dentistaBO = dentistaBO;
    }

    public void exibir(Scanner sc) throws SQLException, ErroNegocioException {
        System.out.println("\n1 - Cadastrar triagem");
        System.out.println("2 - Listar triagens");
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

            System.out.print("Local da triagem: ");
            String local = sc.nextLine();
            System.out.print("Criterios (ex: dor intensa, carie, limpeza): ");
            String criterios = sc.nextLine();

            Triagem t = new Triagem(0, LocalDate.now(), local, criterios, idBenef, idDentista);
            bo.cadastrarComValidacao(t);
            System.out.println("Triagem cadastrada!");
            System.out.println("Classificacao: " + bo.classificarRisco(t));

        } else if (sub == 2) {
            List<Triagem> triagens = bo.listarTodas();
            if (triagens.isEmpty()) {
                System.out.println("Nenhuma triagem cadastrada.");
            } else {
                for (Triagem t : triagens) {
                    System.out.println(t);
                }
            }
        }
    }
}