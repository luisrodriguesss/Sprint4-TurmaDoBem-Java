package br.com.fiap;

import br.com.fiap.service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int op;

        try {
            BeneficiarioService beneficiarioService = new BeneficiarioService();
            DoadorService doadorService = new DoadorService();
            DentistaService dentistaService = new DentistaService();
            DoacaoService doacaoService = new DoacaoService(doadorService.getBo());
            TriagemService triagemService = new TriagemService(beneficiarioService.getBo(), dentistaService.getBo());
            AtendimentoService atendimentoService = new AtendimentoService(beneficiarioService.getBo(), dentistaService.getBo());
            AgendamentoService agendamentoService = new AgendamentoService(dentistaService.getBo());
            ProcedimentoService procedimentoService = new ProcedimentoService();

            do {
                System.out.println("\n--- Menu Principal ---");
                System.out.println("1 - Beneficiarios");
                System.out.println("2 - Doadores");
                System.out.println("3 - Doacoes");
                System.out.println("4 - Dentistas");
                System.out.println("5 - Triagens");
                System.out.println("6 - Atendimentos");
                System.out.println("7 - Agendamentos");
                System.out.println("8 - Procedimentos");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");
                op = Integer.parseInt(sc.nextLine());

                switch (op) {
                    case 1 -> beneficiarioService.exibir(sc);
                    case 2 -> doadorService.exibir(sc);
                    case 3 -> doacaoService.exibir(sc);
                    case 4 -> dentistaService.exibir(sc);
                    case 5 -> triagemService.exibir(sc);
                    case 6 -> atendimentoService.exibir(sc);
                    case 7 -> agendamentoService.exibir(sc);
                    case 8 -> procedimentoService.exibir(sc);
                }

            } while (op != 0);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("Sistema encerrado.");
    }
}