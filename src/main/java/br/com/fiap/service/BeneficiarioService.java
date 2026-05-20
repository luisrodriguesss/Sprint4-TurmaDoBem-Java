package br.com.fiap.service;

import br.com.fiap.bo.BeneficiarioBO;
import br.com.fiap.entities.Beneficiario;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class BeneficiarioService {

    private BeneficiarioBO bo;

    public BeneficiarioService() throws SQLException, ClassNotFoundException {
        this.bo = new BeneficiarioBO();
    }

    public void exibir(Scanner sc) throws SQLException, ErroNegocioException {
        System.out.println("\n1 - Cadastrar beneficiario");
        System.out.println("2 - Listar beneficiarios");
        System.out.print("Escolha: ");
        int sub = Integer.parseInt(sc.nextLine());

        if (sub == 1) {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.print("CEP: ");
            String cep = sc.nextLine();
            System.out.print("Problema: ");
            String problema = sc.nextLine();

            Beneficiario b = new Beneficiario(0, nome, cpf, LocalDate.now(), cep, problema);
            bo.cadastrarComValidacao(b);
            System.out.println("Beneficiario cadastrado!");

        } else if (sub == 2) {
            for (Beneficiario b : bo.listarTodos()) {
                System.out.println(b);
            }
        }
    }

    public BeneficiarioBO getBo() { return bo; }
}