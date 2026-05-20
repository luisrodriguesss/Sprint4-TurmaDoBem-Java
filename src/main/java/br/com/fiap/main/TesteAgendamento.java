package br.com.fiap.main;

import br.com.fiap.bo.AgendamentoBO;
import br.com.fiap.entities.Agendamento;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TesteAgendamento {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("  TESTE DE AGENDAMENTO - TURMA DO BEM");
        System.out.println("========================================\n");

        try {

            AgendamentoBO bo = new AgendamentoBO();

            int idDentista = 6;
            int idBeneficiario = 1;

            System.out.println(">> METODO LOGICO: Classificar prioridade...");

            Agendamento mock = new Agendamento(
                    0,
                    LocalDate.now().plusDays(1),
                    LocalDateTime.now().plusDays(1),
                    "ALTA",
                    idDentista,
                    idBeneficiario
            );

            System.out.println("  Urgencia ALTA: " + bo.classificarPrioridade(mock));

            mock.setUrgencia("MEDIA");
            System.out.println("  Urgencia MEDIA: " + bo.classificarPrioridade(mock));

            mock.setUrgencia("BAIXA");
            System.out.println("  Urgencia BAIXA: " + bo.classificarPrioridade(mock));

            System.out.println("\n>> CRUD: Inserindo agendamento...");

            Agendamento a = new Agendamento(
                    0,
                    LocalDate.now().plusDays(3),
                    LocalDateTime.now().plusDays(3),
                    "ALTA",
                    idDentista,
                    idBeneficiario
            );

            bo.cadastrarComValidacao(a);

            System.out.println("  Agendamento inserido com sucesso!");

            System.out.println("\n>> CRUD: Listando todos os agendamentos...");

            List<Agendamento> lista = bo.listarTodos();

            for (Agendamento ag : lista) {
                System.out.println("  " + ag);
            }

            if (!lista.isEmpty()) {

                int idTeste = lista.get(0).getId();

                System.out.println("\n>> CRUD: Buscando por ID = " + idTeste + "...");

                Agendamento encontrado = bo.buscarPorId(idTeste);

                System.out.println("  Encontrado: " + encontrado);

                System.out.println("\n>> CRUD: Atualizando urgencia...");

                encontrado.setUrgencia("MEDIA");

                bo.atualizar(encontrado);

                System.out.println("  Atualizado: " + bo.buscarPorId(idTeste));

                System.out.println("\n>> CRUD: Excluindo agendamento ID " + idTeste + "...");

                bo.deletar(idTeste);

                System.out.println("  Excluido com sucesso!");
            }

            System.out.println("\n>> TESTE de Excecao: Urgencia invalida...");

            try {

                Agendamento invalido = new Agendamento(
                        0,
                        LocalDate.now().plusDays(1),
                        LocalDateTime.now().plusDays(1),
                        "EXTREMA",
                        idDentista,
                        idBeneficiario
                );

                bo.cadastrarComValidacao(invalido);

            } catch (ErroNegocioException e) {

                System.out.println("  Excecao capturada: " + e.getMessage());
            }

        } catch (SQLException | ClassNotFoundException | ErroNegocioException e) {

            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("\n========================================");
        System.out.println("   TESTE CONCLUIDO");
        System.out.println("========================================");
    }
}
