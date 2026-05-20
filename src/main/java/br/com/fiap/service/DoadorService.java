package br.com.fiap.service;

import br.com.fiap.bo.DoadorBO;
import br.com.fiap.entities.Doador;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DoadorService {

    private DoadorBO bo;

    public DoadorService() throws SQLException, ClassNotFoundException {
        this.bo = new DoadorBO();
    }

    public void exibir(Scanner sc) throws SQLException, ErroNegocioException {
        System.out.println("\n1 - Cadastrar doador");
        System.out.println("2 - Listar doadores");
        System.out.print("Escolha: ");
        int sub = Integer.parseInt(sc.nextLine());

        if (sub == 1) {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.print("Telefone: ");
            String tel = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();

            Doador d = new Doador(0, nome, cpf, tel, email);
            bo.cadastrarComValidacao(d);
            System.out.println("Doador cadastrado!");

        } else if (sub == 2) {
            List<Doador> doadores = bo.listarTodos();
            if (doadores.isEmpty()) {
                System.out.println("Nenhum doador cadastrado.");
            } else {
                for (Doador d : doadores) {
                    System.out.println(d);
                }
            }
        }
    }

    public DoadorBO getBo() { return bo; }
}