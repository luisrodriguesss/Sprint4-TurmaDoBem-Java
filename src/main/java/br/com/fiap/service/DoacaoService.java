package br.com.fiap.service;

import br.com.fiap.bo.DoacaoBO;
import br.com.fiap.bo.DoadorBO;
import br.com.fiap.entities.Doacao;
import br.com.fiap.entities.Doador;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class DoacaoService {

    private DoacaoBO bo;
    private DoadorBO doadorBO;

    public DoacaoService(DoadorBO doadorBO) throws SQLException, ClassNotFoundException {
        this.bo = new DoacaoBO();
        this.doadorBO = doadorBO;
    }

    public void exibir(Scanner sc) throws SQLException, ErroNegocioException {
        System.out.println("\n1 - Registrar doacao");
        System.out.println("2 - Listar doacoes");
        System.out.println("3 - Total arrecadado");
        System.out.println("4 - Total por tipo");
        System.out.print("Escolha: ");
        int sub = Integer.parseInt(sc.nextLine());

        if (sub == 1) {
            List<Doador> doadores = doadorBO.listarTodos();
            if (doadores.isEmpty()) {
                System.out.println("Cadastre um doador primeiro!");
                return;
            }
            System.out.println("Doadores disponiveis:");
            for (Doador d : doadores) {
                System.out.println("  ID " + d.getId() + " - " + d.getNome());
            }
            System.out.print("ID do doador: ");
            int idDoador;
            try {
                idDoador = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ID invalido!");
                return;
            }

            System.out.print("Nome do doador: ");
            String nome = sc.nextLine();
            System.out.print("Tipo (Dinheiro/Material): ");
            String tipo = sc.nextLine();
            System.out.print("Valor (ex: 100.00): ");
            double valor;
            try {
                valor = Double.parseDouble(sc.nextLine().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido! Use o formato: 100.00");
                return;
            }

            Doacao doacao = new Doacao(0, nome, tipo, valor, LocalDate.now(), idDoador);
            bo.registrarComValidacao(doacao);
            System.out.println("Doacao registrada!");

        } else if (sub == 2) {
            for (Doacao d : bo.listarTodas()) {
                System.out.println(d);
            }
        } else if (sub == 3) {
            System.out.printf("Total arrecadado: R$ %.2f%n", bo.calcularTotalDoacoes());
        } else if (sub == 4) {
            System.out.print("Tipo (Dinheiro/Material): ");
            String tipo = sc.nextLine();
            System.out.printf("Total em %s: R$ %.2f%n", tipo, bo.calcularTotalPorTipo(tipo));
        }
    }
}