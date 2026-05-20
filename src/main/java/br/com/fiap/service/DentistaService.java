package br.com.fiap.service;

import br.com.fiap.bo.DentistaBO;
import br.com.fiap.entities.Dentista;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DentistaService {

    private DentistaBO bo;

    public DentistaService() throws SQLException, ClassNotFoundException {
        this.bo = new DentistaBO();
    }

    public void exibir(Scanner sc) throws SQLException, ErroNegocioException {
        System.out.println("\n1 - Cadastrar dentista");
        System.out.println("2 - Listar dentistas");
        System.out.print("Escolha: ");
        int sub = Integer.parseInt(sc.nextLine());

        if (sub == 1) {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("CRO (ex: CRO-SP-12345): ");
            String cro = sc.nextLine();
            System.out.print("Especialidade: ");
            String especialidade = sc.nextLine();
            System.out.print("CEP do consultorio: ");
            String cep = sc.nextLine();

            Dentista d = new Dentista(0, nome, cro, especialidade, cep);
            bo.cadastrarComValidacao(d);
            System.out.println("Dentista cadastrado!");

        } else if (sub == 2) {
            List<Dentista> dentistas = bo.listarTodos();
            if (dentistas.isEmpty()) {
                System.out.println("Nenhum dentista cadastrado.");
            } else {
                for (Dentista d : dentistas) {
                    System.out.println(d);
                }
            }
        }
    }

    public DentistaBO getBo() { return bo; }
}