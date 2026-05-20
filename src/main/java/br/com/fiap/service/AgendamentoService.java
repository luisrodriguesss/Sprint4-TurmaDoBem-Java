package br.com.fiap.service;

import br.com.fiap.bo.AgendamentoBO;
import br.com.fiap.bo.DentistaBO;
import br.com.fiap.entities.Agendamento;
import br.com.fiap.entities.Dentista;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AgendamentoService {

    private AgendamentoBO bo;
    private DentistaBO dentistaBO;

    public AgendamentoService(DentistaBO dentistaBO) throws SQLException, ClassNotFoundException {
        this.bo = new AgendamentoBO();
        this.dentistaBO = dentistaBO;
    }

    public void exibir(Scanner sc) throws SQLException, ErroNegocioException {
        System.out.println("\n1 - Cadastrar agendamento");
        System.out.println("2 - Listar agendamentos");
        System.out.print("Escolha: ");
        int sub = Integer.parseInt(sc.nextLine());

        if (sub == 1) {
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

            System.out.print("ID do beneficiario: ");
            int idBeneficiario = Integer.parseInt(sc.nextLine());

            System.out.print("Data do agendamento (AAAA-MM-DD): ");
            LocalDate data = LocalDate.parse(sc.nextLine());
            System.out.print("Urgencia (BAIXA/MEDIA/ALTA): ");
            String urgencia = sc.nextLine();

            Agendamento a = new Agendamento(0, data, LocalDateTime.now(), urgencia, idDentista, idBeneficiario);
            bo.cadastrarComValidacao(a);
            System.out.println("Agendamento cadastrado!");
            System.out.println("Prioridade: " + bo.classificarPrioridade(a));

        } else if (sub == 2) {
            List<Agendamento> agendamentos = bo.listarTodos();
            if (agendamentos.isEmpty()) {
                System.out.println("Nenhum agendamento cadastrado.");
            } else {
                for (Agendamento a : agendamentos) {
                    System.out.println(a);
                }
            }
        }
    }
}
